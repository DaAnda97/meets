package de.meets.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.asset_manager.LocationManager;
import de.meets.asset_manager.MemberManager;
import de.meets.assets.Location;
import de.meets.assets.Member;
import de.meets.services.GeoData;
import de.meets.vaadin_archetype_application.MeetsUI;

public class ShowUser extends HorizontalLayout implements View {
	public static final String NAME = "showUser";
	private MeetsUI meetsUI;

	private MemberManager memberManager;
	private LocationManager locationManager;
	private Member member;

	private VerticalLayout informationPanel = new VerticalLayout();
	private Label message = new Label("Deine Angaben:");
	private TextField name = new TextField("Benutzername");
	private TextField email = new TextField("E-Mail");
	private TextField location = new TextField();
	private TextField firstName = new TextField("Vorname");
	private TextField lastName = new TextField("Nachname");

	private HorizontalLayout buttonPanel = new HorizontalLayout();
	private Button edit = new Button("Bearbeiten");
	private Button saveChanges = new Button("Speichern");
	private Button removeChanges = new Button("Abbrechen");

	private VerticalLayout passwordPanel = new VerticalLayout();
	private Label changePassword = new Label("Passwort ändern:");
	private PasswordField passwordOld = new PasswordField("Altes Passwort");
	private PasswordField passwordNew = new PasswordField("Neues Passwort");
	private PasswordField passwordNewConfirm = new PasswordField(
			"Bestätige neues Passwort");
	private Button confirmNewPassoword = new Button("Bestätigen");

	private Button deliteUser = new Button("Benutzer löschen!");

	public ShowUser(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;

		memberManager = meetsUI.getMemberManager();
		locationManager = meetsUI.getLocationManager();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		member = meetsUI.getRegistratedMember();

		// -------------------- INFORMATION - PANEL --------------------------
		name.setValue(member.getUsername());
		email.setValue(member.getEmail());
		firstName.setValue(member.getFirstName());
		lastName.setValue(member.getLastName());

		location.setCaption("Adresse");
		location.setImmediate(true);
		location.setInputPrompt(member.getPosition().getCity());

		changeToViewMode();

		// Button ClickListener
		edit.addClickListener(e -> {
			changeToEditMode();
		});
		saveChanges.addClickListener(e -> {
			saveChanges();
		});
		removeChanges.addClickListener(e -> {
			changeToViewMode();
		});
		buttonPanel.addComponent(edit);

		informationPanel.addComponents(message, name, email, location,
				firstName, lastName, buttonPanel);

		// -------------------- PASSWORD - PANEL ---------------------------
		confirmNewPassoword.addClickListener(e -> {
			confirmNewPassoword();
		});
		passwordPanel.addComponents(changePassword, passwordOld, passwordNew,
				passwordNewConfirm, confirmNewPassoword);

		// ------------------------ MAIN - PANEL ---------------------------

		deliteUser.addClickListener(e -> {
			meetsUI.deleteUser();
		});

		this.addComponents(informationPanel, passwordPanel, deliteUser);
		this.setMargin(true);
		this.setSpacing(true);
	}

	private void saveChanges() {
		member.setUsername(name.getValue());
		member.setEmail(email.getValue());
		member.setFirstName(firstName.getValue());
		member.setLastName(lastName.getValue());

		if (!location.getValue().trim().equals("")) {
			Location position;
			try {
				GeoData geoData = new GeoData();
				position = geoData
						.getCoordinatesFromAdress(location.getValue());
			} catch (Exception e) {
				location.setComponentError(new UserError(
						"Deine Adresse konnte nicht gefinden werden."));
				return;
			}

			if (locationManager.get(position.getCity()) == null) {
				locationManager.add(position);
				System.out.println("Instert into DB: " + position);
			}

			member.setPosition(position);

		}
		memberManager.update(member);
		message.setValue("Änderungen gespeichert!");

		changeToViewMode();
	}

	private void changeToViewMode() {
		name.setEnabled(false);
		email.setEnabled(false);
		location.setEnabled(false);
		firstName.setEnabled(false);
		lastName.setEnabled(false);
		buttonPanel.addComponent(edit);
		buttonPanel.removeComponent(saveChanges);
		buttonPanel.removeComponent(removeChanges);
	}

	private void changeToEditMode() {
		name.setEnabled(true);
		email.setEnabled(true);
		location.setEnabled(true);
		firstName.setEnabled(true);
		lastName.setEnabled(true);
		buttonPanel.removeComponent(edit);
		buttonPanel.addComponents(saveChanges, removeChanges);
	}

	private void confirmNewPassoword() {
		String oldPassword = passwordOld.getValue();
		String newPassword = passwordOld.getValue();
		String newPasswordConfirm = passwordNewConfirm.getValue();

		try {
			oldPassword = meetsUI.shaHash(passwordOld.getValue().trim());
			newPassword = meetsUI.shaHash(passwordNew.getValue().trim());
			newPasswordConfirm = meetsUI.shaHash(passwordNewConfirm.getValue()
					.trim());
		} catch (Exception e1) {
			passwordOld.setComponentError(new UserError(
					"Internal error - Please try later again"));
			e1.printStackTrace();
			return; // Cancel, because the password is not hashed
		}

		if (member.getPassword().equals(oldPassword)) {
			if (newPassword.equals(newPasswordConfirm)) {
				changePassword.setValue("Passwort geändert!");
				member.setPassword(newPassword);
				memberManager.update(member);
			} else {
				passwordNew.setComponentError(new UserError(
						"Neue Passwörter stimmen nicht überein"));
				passwordNewConfirm.setComponentError(new UserError(
						"Neue Passwörter stimmen nicht überein"));
			}
		} else {
			passwordOld.setComponentError(new UserError("Falsches Passwort"));
		}

		passwordOld.setValue("");
		passwordNew.setValue("");
		passwordNewConfirm.setValue("");
	}
}