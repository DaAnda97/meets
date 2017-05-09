package de.meets.gui.views;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.asset_manager.LocationManager;
import de.meets.asset_manager.MemberManager;
import de.meets.assets.Location;
import de.meets.assets.Member;
import de.meets.gui.extendedComponents.ExtendedTextField;
import de.meets.services.GeoData;
import de.meets.services.PasswordValidator;
import de.meets.vaadin_archetype_application.MeetsUI;

public class Register extends CustomComponent implements View {
	public static final String NAME = "register";
	MeetsUI meetsUI;

	private Label register;
	private ExtendedTextField username;
	private ExtendedTextField email;
	private ExtendedTextField location;
	private ExtendedTextField firstName;
	private ExtendedTextField lastName;
	private ExtendedTextField password;
	private ExtendedTextField controlPassword;
	private Button registerButton;
	private Button switchButton;

	private MemberManager memberManager;
	private LocationManager locationManager;

	public Register(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;
		memberManager = meetsUI.getMemberManager();
		locationManager = meetsUI.getLocationManager();

		register = new Label("Registrieren");

		username = new ExtendedTextField("Benutzername:", new TextField(), "");
		username.setRequired(true);
		// username.addTextChangeListener(new TextChangeListener() {
		// @Override
		// public void textChange(TextChangeEvent event) {
		//
		// }
		// });

		email = new ExtendedTextField("E-Mail:", new TextField(), "Diese E-Mail ist schon registriert");
		email.setRequired(true);
		email.setInputPrompt("Deine hinterlegte E-Mail");
		email.addValidator(new EmailValidator(""), "Keine gültige E-Mailadresse");
		email.setInvalidAllowed(false);
		
		location = new ExtendedTextField("Adresse:", new TextField(), "Keine gültige Adresse");
		location.setRequired(true);

		password = new ExtendedTextField("Passwort:", new PasswordField(), "Die Passwörter stimmen nicht überein");
		password.setRequired(true);
		password.addValidator(new PasswordValidator(), "Das Passwort entspricht nicht den Vorgaben");
		
		controlPassword = new ExtendedTextField("Passwort wiederholen", new TextField(), "Die Passwörter stimmen nicht überein");
		controlPassword.setRequired(true);

		firstName = new ExtendedTextField("Vorname:", new TextField(), "");
		lastName = new ExtendedTextField("Nachname:", new TextField(), "");

		registerButton = new Button("Registrieren");
		registerButton.addClickListener(e -> {
			registerButtonClicked();
		});

		switchButton = new Button("Zum Login");
		;
		switchButton.addClickListener(listener -> getUI().getNavigator()
				.navigateTo(Login.NAME));

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		verticalLayout.addComponents(register, username, email, location,
				firstName, lastName, password, controlPassword, registerButton,
				switchButton);
		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);
		setCompositionRoot(verticalLayout);
	}

	private void registerButtonClicked() {
		// Teste, ob die Eingaben (E-Mail, gültiges Passwort) valide sind
		// Fehlermeldungen kommen automatisch durch den Validator
		if (!email.isValid() || !password.isValid()) {
			return;
		}

		String validEmail = email.getValue().trim();
		String shaPassword;
		try {
			shaPassword = meetsUI.shaHash(password.getValue().trim());
		} catch (Exception e1) {
			password.setComponentError(new UserError(
					"Internal error - Please try later again"));
			e1.printStackTrace();
			return; // Abbruch, da Passwort nicht gehashed wurde
		}

		if (!password.getValue().equals(controlPassword.getValue())) {
			password.setComponentError(new UserError(
					"Passwörter stimmen nicht überein!"));
			controlPassword.setComponentError(new UserError(
					"Passwörter stimmen nicht überein!"));
		} else {
			if (memberManager.checkEMail(email.getValue())) {
				email.setComponentError(new UserError(
						"E-Mail ist schon verwendet"));
			} else {
				Location position;
				try {
					GeoData geoData = new GeoData();
					position = geoData.getCoordinatesFromAdress(location
							.getValue());
				} catch (Exception e) {
					location.setComponentError(new UserError(
							"Deine Adresse konnte nicht gefinden werden."));
					return;
				}

				if (locationManager.get(position.getCity()) == null) {
					locationManager.add(position);
					System.out.println("Instert into DB: " + position);
				}

				position = locationManager.get(position.getCity());

				// Generate Member
				Member member = new Member(username.getValue().trim(), null,
						null, shaPassword, validEmail, position);
				member.setFirstName(firstName.getValue().trim());
				member.setLastName(lastName.getValue().trim());
				memberManager.add(member);

				System.out.println("------------" + member.toString());
				meetsUI.login(member);

			}
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		email.focus();
	}

}