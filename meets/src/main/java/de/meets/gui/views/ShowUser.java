package de.meets.gui.views;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Location;
import de.meets.assets.Member;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.gui.extendedComponents.SafeButton;
import de.meets.services.GeoData;
import de.meets.services.SHAEncription;
import de.meets.vaadin_archetype_application.MeetsUI;

public class ShowUser extends MeetsView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7447737181195264736L;
	
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

	private SafeButton deliteUser;
	
	private String locationChanged; // To check, weather the user has changed the location


	public ShowUser(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);
		
		// -------------------- INFORMATION - PANEL --------------------------
		location.setCaption("Adresse");
		location.setImmediate(true);

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
		
		deliteUser = new SafeButton("Löschen",
				"Bist du dir sicher, dass du deinen Account unwiederruflich löschen möchtest?",
				new ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						Member userToDelete = getRegistratedMember();
						getMemberManager().delete(userToDelete);
						logout();
						Notification.show("Meeting gelöscht!",
								"Dein Account wurde gelöscht!",
								Type.TRAY_NOTIFICATION);
					}
				});

		
		Panel componentPanel = new Panel();
		HorizontalLayout componentPanelLayout = new HorizontalLayout();
		componentPanelLayout.addComponents(informationPanel, passwordPanel, deliteUser);
		componentPanelLayout.setMargin(true);
		componentPanelLayout.setSpacing(true);
		componentPanel.setContent(componentPanelLayout);
		setCompositionRoot(componentPanel);
	}


	private void saveChanges() {
		Member newMember = getRegistratedMember();	
		newMember.setUsername(name.getValue());
		newMember.setEmail(email.getValue());
		newMember.setFirstName(firstName.getValue());
		newMember.setLastName(lastName.getValue());

		if (!location.getValue().trim().equals("") & locationChanged != location.getValue().trim()) {
			Location position;
			try {
				GeoData geoData = new GeoData();
				position = geoData
						.getCoordinatesFromAdress(location.getValue().trim());
			} catch (Exception e) {
				location.setComponentError(new UserError(
						"Deine Adresse konnte nicht gefinden werden."));
				return;
			}

			if (getLocationManager().get(position.getCity()) == null) {
				getLocationManager().add(position);
				System.out.println("Instert into DB: " + position);
			}

			newMember.setPosition(position);

		} else {
			newMember.setPosition(getRegistratedMember().getPosition());
		}
		updateRegistratedMember(newMember);
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
			oldPassword = new SHAEncription().SHAHash(passwordOld.getValue().trim());
			newPassword = new SHAEncription().SHAHash(passwordNew.getValue().trim());
			newPasswordConfirm = new SHAEncription().SHAHash(passwordNewConfirm.getValue()
					.trim());
		} catch (Exception e1) {
			passwordOld.setComponentError(new UserError(
					"Internal error - Please try later again"));
			e1.printStackTrace();
			return; // Cancel, because the password is not hashed
		}

		if (getRegistratedMember().getPassword().equals(oldPassword)) {
			if (newPassword.equals(newPasswordConfirm)) {
				changePassword.setValue("Passwort geändert!");
				Member newMember = getRegistratedMember();
				newMember.setPassword(newPassword);
				updateRegistratedMember(newMember);
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


	@Override
	public void enter(ViewChangeEvent event) {
		Member member = getRegistratedMember();

		name.setValue(member.getUsername());
		email.setValue(member.getEmail());
		firstName.setValue(member.getFirstName());
		lastName.setValue(member.getLastName());
		location.setInputPrompt(member.getPosition().getCity());
		
		locationChanged = member.getPosition().getCity();
	}
}