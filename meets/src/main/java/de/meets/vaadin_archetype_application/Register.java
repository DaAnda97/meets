package de.meets.vaadin_archetype_application;

import java.util.Locale;

import org.vaadin.addons.locationtextfield.GeocodedLocation;
import org.vaadin.addons.locationtextfield.LocationTextField;
import org.vaadin.addons.locationtextfield.OpenStreetMapGeocoder;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;









import de.meets.asset_manager.MemberManager;
import de.meets.assets.Location;
import de.meets.assets.Member;

public class Register extends VerticalLayout implements View{
	Label register;
	TextField username;
	TextField email;
	LocationTextField<GeocodedLocation> location;
	PasswordField password;
	PasswordField controlPassword;
	Button registerButton;
	Button switchButton;
	
	MemberManager memberManager = new MemberManager();
	
	@Override
	public void enter(ViewChangeEvent event) {
		final OpenStreetMapGeocoder geocoder = OpenStreetMapGeocoder.getInstance();
		geocoder.setLimit(25);
		location = new LocationTextField<GeocodedLocation>(geocoder, GeocodedLocation.class);
		location.setCaption("Adresse");
        location.setImmediate(true);
        location.setInputPrompt("");
        
        register = new Label("Registrieren");
        
        username  = new TextField("Benutzername");
        username.setRequired(true);
//        username.addTextChangeListener(new TextChangeListener() {
//			@Override
//			public void textChange(TextChangeEvent event) {
//				
//			}
//		});
        
        email = new TextField("E-Mail");
        
        password = new PasswordField("Passwort");
        
        controlPassword = new PasswordField("Passwort wiederholen");
		
        registerButton = new Button("Registrieren");
	    registerButton.addClickListener( e -> {
	    	GeocodedLocation locationValue = location.getLocation();
	    	Locale locale = location.getLocale();
	    	
	    	boolean isSamePasswordInput = password.getValue().equals(controlPassword.getValue());
	    	boolean isValidEmail = Login.isValidEmailAddress(email.getValue());
	    	boolean isUnusedEmail = memberManager.existsEMail(email.getValue());
	    	
	    	if (isSamePasswordInput){
	    		if (isValidEmail){
	    			// Noch E-Mail und Benutzer auf Eindeutigkeit prüfen
	    			// Location ID über Location Manager get(plz) abfragen
	    			Member member = new Member(0, username.getValue(), null, null, password.getValue(), 
	    									email.getValue(), new Location(1), null, null);
	    			memberManager.add(member);
	    			NavigatorUI.setRegistratedMember(member);
	    			getUI().getNavigator().navigateTo("ShowUser");
	    		} else {
	    			register.setValue("Bitte geben Sie eine gültige E-Mail Adresse ein!");
	    		}
	    	} else {
	    		register.setValue("Ihre Passwörter stimmen nicht überein");
	    	}
	    });
	    
	    switchButton = new Button("Zum Login");;
        switchButton.addClickListener(listener -> getUI().getNavigator().navigateTo("Login"));
	    
	    this.addComponents(register, username, email, location, password, controlPassword, registerButton, switchButton);
	    this.setMargin(true);
	    this.setSpacing(true);
	}

}
