package de.meets.views;

import org.vaadin.addons.locationtextfield.GeocodedLocation;
import org.vaadin.addons.locationtextfield.LocationTextField;
import org.vaadin.addons.locationtextfield.OpenStreetMapGeocoder;

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
import de.meets.vaadin_archetype_application.MeetsUI;

public class ShowUser extends HorizontalLayout implements View{
	public static final String NAME = "showUser";
	private MeetsUI meetsUI;
	
	MemberManager memberManager;
	LocationManager locationManager;
	Member member;
	
	VerticalLayout informationPanel = new VerticalLayout();
	Label message = new Label("Deine Angaben:");
	TextField name = new TextField("Benutzername");
	TextField email = new TextField("E-Mail");
	LocationTextField<GeocodedLocation> location;
	TextField firstName = new TextField("Vorname");
	TextField lastName = new TextField("Nachname");
	
	HorizontalLayout buttonPanel = new HorizontalLayout();
    Button edit = new Button("Bearbeiten");
    Button saveChanges = new Button("Speichern");
    Button removeChanges = new Button("Abbrechen");
    
    VerticalLayout passwordPanel = new VerticalLayout();
    Label changePassword = new Label("Passwort ändern:");
    PasswordField passwordOld = new PasswordField("Altes Passwort");
    PasswordField passwordNew = new PasswordField("Neues Passwort");
    PasswordField passwordNewConfirm = new PasswordField("Bestätige neues Passwort");
    Button confirmNewPassoword = new Button("Bestätigen");
    
    Button deliteUser = new Button("Benutzer löschen!");
    Button logout = new Button("Abmelden");

    
    
	public ShowUser(MeetsUI meetsUI) {
		this.meetsUI = meetsUI;
		
		memberManager = meetsUI.getMemberManager();
		locationManager = meetsUI.getLocationManager();
		member = meetsUI.getRegistratedMember();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		//-------------------- INFORMATION - PANEL --------------------------
	    name.setValue(member.getUsername());
	    email.setValue(member.getEmail());
	    firstName.setValue(member.getFirstName());
	    lastName.setValue(member.getLastName());
	    
	    final OpenStreetMapGeocoder geocoder = OpenStreetMapGeocoder.getInstance();
		geocoder.setLimit(25);
		location = new LocationTextField<GeocodedLocation>(geocoder, GeocodedLocation.class);
		GeocodedLocation geoLoc = new GeocodedLocation();
		location.setLocation(geoLoc);
		location.setCaption("Adresse");
		location.setText("");
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
	    
	    informationPanel.addComponents(message, name, email, location, firstName, lastName, buttonPanel);
	    
	    
	    //-------------------- PASSWORD - PANEL ---------------------------
	    confirmNewPassoword.addClickListener(e -> {
	    	confirmNewPassoword();
	    });
	    passwordPanel.addComponents(changePassword, passwordOld, passwordNew, passwordNewConfirm, confirmNewPassoword);
	    
	    
	    //------------------------ MAIN - PANEL ---------------------------
	    
	    deliteUser.addClickListener(e -> {
//	    	MeetsUI.deleteUser();
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
		
		Location position = member.getPosition();
		if (!location.getText().trim().equals("")){
			if (location.getLocation() != null){
				position.setCity(location.getText());
				position.setLatitude(location.getLocation().getLat());
				position.setLongitude(location.getLocation().getLon());
				
				if ( locationManager.get(position.getCity()) == null ) {
					locationManager.add(position);
				} else {
					position = locationManager.get(position.getCity());
				}
				
				member.setPosition(position);
			} else {
				location.setComponentError(new UserError("Wähle eine Adresse aus der Liste! (Drücke Enter)"));
				return; //Stop saving, if there's a input, but it's not a GeoLoc
			}
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
			newPasswordConfirm = meetsUI.shaHash(passwordNewConfirm.getValue().trim());
		} catch (Exception e1) {
			passwordOld.setComponentError(new UserError("Internal error - Please try later again"));
			e1.printStackTrace();
			return; //Cancel, because the password is not hashed
		}
		
		if (member.getPassword().equals(oldPassword)){
			if (newPassword.equals(newPasswordConfirm)){
				changePassword.setValue("Passwort geändert!");
				member.setPassword(newPassword);
				memberManager.update(member);
			} else {
				passwordNew.setComponentError(new UserError("Neue Passwörter stimmen nicht überein"));
				passwordNewConfirm.setComponentError(new UserError("Neue Passwörter stimmen nicht überein"));
			}
		} else {
			passwordOld.setComponentError(new UserError("Falsches Passwort"));
		}
		
		passwordOld.setValue("");
		passwordNew.setValue("");
		passwordNewConfirm.setValue("");
	}
}
