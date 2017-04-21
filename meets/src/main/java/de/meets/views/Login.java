package de.meets.views;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import de.meets.asset_manager.MemberManager;
import de.meets.assets.Member;
import de.meets.services.PasswordValidator;
import de.meets.vaadin_archetype_application.MeetsUI;

public class Login extends CustomComponent implements View {
	public static final String NAME = "login";
	public MeetsUI meetsUI;

	private TextField emailTextField;
	private PasswordField passwordTextField;
	private Button loginButton;
	private Button registerButton;

	private MemberManager memberManager;

	public Login(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;
		memberManager = meetsUI.getMemberManager();

		setSizeFull();

		// Create the user input field
		emailTextField = new TextField("E-Mail:");
		emailTextField.setWidth("300px");
		emailTextField.setRequired(true);
		emailTextField.setInputPrompt("Deine hinterlegte E-Mail");
		emailTextField.addValidator(new EmailValidator(
				"Der Benutzername muss eine E-Mailadresse sein"));
		emailTextField.setInvalidAllowed(false);

		// Create the password input field
		passwordTextField = new PasswordField("Passwort:");
		passwordTextField.setWidth("300px");
		passwordTextField.addValidator(new PasswordValidator());
		passwordTextField.setRequired(true);
		passwordTextField.setValue("");
		passwordTextField.setNullRepresentation("");

		// Create login button
		loginButton = new Button("Login");
		loginButton.addClickListener(e -> {
			loginButtonClicked();
		});

		// Create register button
		registerButton = new Button("Noch nicht registriert?");
		registerButton.addClickListener(e -> {
			registerButtonClicked();
		});

		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(emailTextField,
				passwordTextField, loginButton, registerButton);
		fields.setCaption("Bitte melde dich an:");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

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
		meetsUI.getNavigator().navigateTo(Register.NAME);
	}

	public void loginButtonClicked() {

		// Teste, ob die Eingaben (E-Mail, gültiges Passwort) valide sind
		if (!emailTextField.isValid() || !passwordTextField.isValid()) {
			return;
		}

		String validEmail = emailTextField.getValue();
		String shaPassword;
		try {
			shaPassword = meetsUI.shaHash(passwordTextField.getValue().trim());
		} catch (Exception e1) {
			passwordTextField.setComponentError(new UserError(
					"Internal error - Please try later again"));
			e1.printStackTrace();
			return; // Abbruch, da Passwort nicht gehashed wurde
		}

		Member loginMember = memberManager.checkLogin(validEmail, shaPassword);
		if (loginMember != null) {
			meetsUI.login(loginMember);
		} else {
			// Wrong password clear the password field and refocuses it
			this.passwordTextField.setValue("");
			this.passwordTextField.setComponentError(new UserError(
					"E-Mail oder Passwort falsch."));
			this.emailTextField.setValue("");
			this.emailTextField.setComponentError(new UserError(
					"E-Mail oder Passwort falsch."));
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

