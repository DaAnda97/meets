package de.meets.gui.views;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

import de.meets.assets.Member;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.services.PasswordValidator;
import de.meets.services.SHAEncription;
import de.meets.vaadin_archetype_application.MeetsUI;

public class Login extends MeetsView {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4225187475844096527L;
	
	private TextField emailTextField;
	private PasswordField passwordTextField;
	private Button loginButton;
	private Button registerButton;

	public Login(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);
		
		setSizeFull();

		// Create the user input field
		emailTextField = new TextField("E-Mail");
		emailTextField.setRequired(true);
		emailTextField.addValidator(new EmailValidator("E-Mail oder Passwort falsch."));

		// Create the password input field
		passwordTextField = new PasswordField("Passwort");
		passwordTextField.addValidator(new PasswordValidator("E-Mail oder Passwort falsch."));
		passwordTextField.setRequired(true);
		passwordTextField.setValue("");

		// Create login button
		loginButton = new Button("Login", FontAwesome.SIGN_IN);
		loginButton.addClickListener(e -> {
			loginButtonClicked();
		});

		// Create register button
		registerButton = new Button("Noch nicht registriert?");
		registerButton.setStyleName(Runo.BUTTON_LINK);
		registerButton.addClickListener(e -> {
			registerButtonClicked();
		});

		Label lblLogin = new Label("Anmelden");
		lblLogin.setWidthUndefined();
		
		// Add both to a panel
		VerticalLayout fields = new VerticalLayout();
		fields.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		fields.setSpacing(true);
		fields.setMargin(true);
		fields.addComponents(lblLogin, emailTextField, passwordTextField, loginButton, registerButton);

		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		// viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		
		setCompositionRoot(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		emailTextField.focus();
	}

	public void registerButtonClicked() {
		navigateTo(ViewName.REGISTER);
	}

	public void loginButtonClicked() {

		// Teste, ob die Eingaben (E-Mail, gültiges Passwort) valide sind
		if (!emailTextField.isValid() || !passwordTextField.isValid()) {
			return;
		}

		String validEmail = emailTextField.getValue();
		String shaPassword;
		try {
			shaPassword = new SHAEncription().SHAHash(passwordTextField.getValue().trim());
		} catch (Exception e1) {
			passwordTextField.setComponentError(new UserError(
					"Internal error - Please try later again"));
			e1.printStackTrace();
			return; // Abbruch, da Passwort nicht gehashed wurde
		}

		Member loginMember = getMemberManager().checkLogin(validEmail, shaPassword);
		if (loginMember != null) {
			this.login(loginMember);
		} else {
			// Wrong password clear the password field and refocuses it
			this.passwordTextField.isValid();//.showErrorMessage();
			this.emailTextField.isValid();//.showErrorMessage();
		}
	}

}

// MemberManager memberManager = MeetsUI.getMemberManager();

// @Override
// public void enter(ViewChangeEvent event) {
//
// loginButton.addClickListener( e -> {
// String emailValue = email.getValue().trim();
//
// String shaPassword;
// try {
// shaPassword = MeetsUI.shaHash(password.getValue().trim());
// } catch (Exception e1) {
// password.setComponentError(new
// UserError("Internal error - Please try later again"));
// e1.printStackTrace();
// return; //Cancel, because the password is not hashed
// }
//
// if (MeetsUI.isValidEmailAddress(emailValue))
// {
// if(memberManager.checkEMail(emailValue))
// {
// Member member = memberManager.checkLogin(emailValue, shaPassword);
// if (member != null)
// {
// MeetsUI.setRegistratedMember(member);
// getUI().getNavigator().navigateTo(Views.MEETING_OVERVIEW.getView());
// } else
// {
// password.setComponentError(new UserError("Falsches Passwort!"));
// }
// } else
// {
// email.setComponentError(new UserError("E-Mail ist uns nicht bekannt!"));
// }
// } else
// {
// email.setComponentError(new UserError("Ungültige E-Mail"));
// }
//
// });
//
// Button switchButton = new Button("Noch nicht registriert?");
// switchButton.addClickListener(listener ->
// getUI().getNavigator().navigateTo(Views.REGISTER.getView()));
//
// this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
// this.addComponents(login, email, password, loginButton, switchButton);
// // this.setSizeFull();
// this.setMargin(true);
// this.setSpacing(true);

