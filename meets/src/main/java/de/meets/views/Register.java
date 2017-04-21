package de.meets.views;

import org.vaadin.addons.locationtextfield.GeocodedLocation;
import org.vaadin.addons.locationtextfield.LocationTextField;
import org.vaadin.addons.locationtextfield.OpenStreetMapGeocoder;

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
import de.meets.services.PasswordValidator;
import de.meets.vaadin_archetype_application.MeetsUI;

public class Register extends CustomComponent implements View {
	public static final String NAME = "register";
	MeetsUI meetsUI;
	
	private Label register;
	private TextField username;
	private TextField email;
	private LocationTextField<GeocodedLocation> location;
	private TextField firstName;
	private TextField lastName;
	private PasswordField password;
	private PasswordField controlPassword;
	private Button registerButton;
	private Button switchButton;
	
	private MemberManager memberManager;
	private LocationManager locationManager;

	
	public Register(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;
		memberManager = meetsUI.getMemberManager();
		locationManager = meetsUI.getLocationManager();
		
		final OpenStreetMapGeocoder geocoder = OpenStreetMapGeocoder
				.getInstance();
		geocoder.setLimit(25);
		location = new LocationTextField<GeocodedLocation>(geocoder,
				GeocodedLocation.class);
		location.setCaption("Adresse");
		location.setImmediate(true);
		location.setInputPrompt("");
		location.setRequired(true);

		register = new Label("Registrieren");

		username = new TextField("Benutzername");
		username.setRequired(true);
		// username.addTextChangeListener(new TextChangeListener() {
		// @Override
		// public void textChange(TextChangeEvent event) {
		//
		// }
		// });

		email = new TextField("E-Mail");
		email.setRequired(true);
		email.setRequired(true);
		email.setInputPrompt("Deine hinterlegte E-Mail");
		email.addValidator(new EmailValidator(
				"Der Benutzername muss eine E-Mailadresse sein"));
		email.setInvalidAllowed(false);

		password = new PasswordField("Passwort");
		password.setRequired(true);
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		controlPassword = new PasswordField("Passwort wiederholen");
		controlPassword.setRequired(true);

		firstName = new TextField("Vorname");
		lastName = new TextField("Nachname");

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
				if (location.getLocation() == null) {
					location.setComponentError(new UserError(
							"Wähle eine Adresse aus der Liste! (Drücke Enter)"));
				} else {

					// Generate Location
					Location position = new Location(location.getText(),
							location.getLocation().getLon(), location
									.getLocation().getLat());

					if (locationManager.get(position.getCity()) == null) {
						locationManager.add(position);
						System.out.println("Instert into DB: " + position);
					}

					position = locationManager.get(position.getCity());

					// Generate Member
					Member member = new Member(username.getValue().trim(), null, null,
							shaPassword, validEmail, position);
					member.setFirstName(firstName.getValue().trim());
					member.setLastName(lastName.getValue().trim());
					memberManager.add(member);
					
					System.out.println("------------" +member.toString());
					meetsUI.login(member);
				}
			}
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		email.focus();
	}

}
