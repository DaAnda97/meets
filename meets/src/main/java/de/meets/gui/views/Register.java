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

import de.meets.assets.Location;
import de.meets.assets.Member;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.services.GeoData;
import de.meets.services.PasswordValidator;
import de.meets.services.SHAEncription;
import de.meets.vaadin_archetype_application.MeetsUI;

public class Register extends MeetsView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6587051905101649685L;

	private Label register;
	private TextField username;
	private TextField email;
	private TextField location;
	private TextField firstName;
	private TextField lastName;
	private PasswordField password;
	private PasswordField controlPassword;
	private Button registerButton;
	private Button switchButton;

	public Register(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);

		register = new Label("Registrieren");
		register.setWidthUndefined();

		// Validators: com.vaadin.data.validator.

		username = new TextField("Benutzername");
		username.setIcon(FontAwesome.USER);
		username.setRequired(true);
		username.setInvalidAllowed(false);

		// username.addTextChangeListener(new TextChangeListener() {
		// @Override
		// public void textChange(TextChangeEvent event) {
		//
		// }
		// });

		email = new TextField("E-Mail");
		email.setIcon(FontAwesome.ENVELOPE);
		email.setRequired(true);
		// email.setInputPrompt("E-Mail-Adresse");
		email.addValidator(new EmailValidator("Keine gültige E-Mailadresse"));

		location = new TextField("Adresse");
		location.setRequired(true);
		location.setIcon(FontAwesome.LOCATION_ARROW);
		// location.addValidator(new RegexpValidator("Location-REGEX", true,
		// "Keine gültige Adresse"));

		password = new PasswordField("Passwort");
		password.setRequired(true);
		password.setIcon(FontAwesome.LOCK);
		password.addValidator(new PasswordValidator("Das Passwort zu schwach!"));
		password.setInvalidAllowed(false);

		controlPassword = new PasswordField("Passwort wiederholen");
		controlPassword.setIcon(FontAwesome.LOCK);
		controlPassword.setRequired(true);
		controlPassword.setInvalidAllowed(false);

		firstName = new TextField("Vorname");
		firstName.setIcon(FontAwesome.TAG);
		lastName = new TextField("Nachname");
		lastName.setIcon(FontAwesome.TAG);

		registerButton = new Button("Registrieren");
		registerButton.addClickListener(e -> {
			registerButtonClicked();
		});

		switchButton = new Button("Zum Login");
		switchButton.setStyleName(Runo.BUTTON_LINK);
		switchButton.addClickListener(listener -> getUI().getNavigator().navigateTo(ViewName.LOGIN.toString()));

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		verticalLayout.addComponents(register, username, email, location, firstName, lastName, password,
				controlPassword, registerButton, switchButton);
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
			shaPassword = new SHAEncription().SHAHash(password.getValue().trim());
		} catch (Exception e1) {
			password.setComponentError(new UserError("Internal error - Please try later again"));
			e1.printStackTrace();
			return; // Abbruch, da Passwort nicht gehashed wurde
		}

		if (!password.getValue().equals(controlPassword.getValue())) {
			password.setComponentError(new UserError("Passwörter stimmen nicht überein!"));
			controlPassword.setComponentError(new UserError("Passwörter stimmen nicht überein!"));
		} else {
			if (getMemberManager().checkEMail(email.getValue())) {
				email.setComponentError(new UserError("E-Mail ist schon verwendet"));
			} else {
				Location position;
				try {
					GeoData geoData = new GeoData();
					position = geoData.getCoordinatesFromAdress(location.getValue());
				} catch (Exception e) {
					location.setComponentError(new UserError("Deine Adresse konnte nicht gefinden werden."));
					return;
				}

				if (getLocationManager().get(position.getCity()) == null) {
					getLocationManager().add(position);
					System.out.println("Instert into DB: " + position);
				}

				position = getLocationManager().get(position.getCity());

				// Generate Member
				Member member = new Member(username.getValue().trim(), null, null, shaPassword, validEmail, position);
				member.setFirstName(firstName.getValue().trim());
				member.setLastName(lastName.getValue().trim());
				getMemberManager().add(member);

				System.out.println("------------" + member.toString());
				this.login(member);

			}
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		username.focus();
	}

}