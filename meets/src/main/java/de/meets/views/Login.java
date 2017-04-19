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
import de.meets.services.GeneralServices;
import de.meets.services.PasswordValidator;
import de.meets.vaadin_archetype_application.MeetsUI;

public class Login extends CustomComponent implements View {
	public static final String NAME = "login";
	public MeetsUI meetsUI;

	private TextField userTextField;
	private PasswordField passwordTextField;
	private Button loginButton;
	private Button registerButton;

	private MemberManager memberManager;

	
	public Login(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;
		memberManager = meetsUI.getMemberManager();
		
		setSizeFull();

		// Create the user input field
		userTextField = new TextField("E-Mail:");
		userTextField.setWidth("300px");
		userTextField.setRequired(true);
		userTextField.setInputPrompt("Deine hinterlegte E-Mail");
		userTextField.addValidator(new EmailValidator(
				"Der Benutzername muss eine E-Mailadresse sein"));
		userTextField.setInvalidAllowed(false);

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
		VerticalLayout fields = new VerticalLayout(userTextField,
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
		userTextField.focus();
	}

	public void registerButtonClicked() {
		meetsUI.getNavigator().navigateTo(Register.NAME);
	}

	public void loginButtonClicked() {

		// Teste, ob die Eingaben (E-Mail, gültiges Passwort) valide sind
		if (!userTextField.isValid() || !passwordTextField.isValid()) {
			return;
		}

		String username = userTextField.getValue();
		String shaPassword;
		try {
			shaPassword = GeneralServices.shaHash(passwordTextField.getValue()
					.trim());
		} catch (Exception e1) {
			passwordTextField.setComponentError(new UserError(
					"Internal error - Please try later again"));
			e1.printStackTrace();
			return; // Abbruch, da Passwort nicht gehashed wurde
		}
		
		
		Member loginMember = memberManager.checkLogin(username, shaPassword);
		if (!loginMember.equals(null)) {
			
			meetsUI.login(loginMember);

		} else {

			// Wrong password clear the password field and refocuses it
			this.passwordTextField.setValue(null);
			this.passwordTextField.setComponentError(new UserError(
					"E-Mail oder Passwort falsch."));
			this.userTextField.setValue(null);
			this.userTextField.setComponentError(new UserError(
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

