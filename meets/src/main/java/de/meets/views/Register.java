package de.meets.views;

import java.util.Locale;

import org.vaadin.addons.locationtextfield.GeocodedLocation;
import org.vaadin.addons.locationtextfield.LocationTextField;
import org.vaadin.addons.locationtextfield.OpenStreetMapGeocoder;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.asset_manager.MemberManager;
import de.meets.assets.Location;
import de.meets.assets.Member;
import de.meets.vaadin_archetype_application.MeetsUI;
import de.meets.vaadin_archetype_application.Views;

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
	    	boolean isValidEmail = MeetsUI.isValidEmailAddress(email.getValue());
	    	boolean isUnusedEmail = !memberManager.checkEMail(email.getValue());
	    	
	    	if (isSamePasswordInput)
	    	{
	    		if (isValidEmail)
	    		{
	    			if (isUnusedEmail){
	    				Location position = new Location(location.getText(), locationValue.getLon(), locationValue.getLat());
	    				// Noch E-Mail und Benutzer auf Eindeutigkeit prüfen
	    				// Location ID über Location Manager get(plz) abfragen
	    				Member member = new Member(username.getValue(), "", "", password.getValue(), email.getValue(), position);
	    				memberManager.add(member);
	    				MeetsUI.setRegistratedMember(member);
	    				getUI().getNavigator().navigateTo(Views.MEETING_OVERVIEW.getView());
	    			} else {
	    				email.setComponentError(new UserError("E-Mail ist schon verwendet"));
	    			}
	    		} else {
	    			email.setComponentError(new UserError("Ungültige E-Mail"));
	    		}
	    	} else {
	    		password.setComponentError(new UserError("Passwörter stimmen nicht überein!"));
	    		controlPassword.setComponentError(new UserError("Passwörter stimmen nicht überein!"));
	    	}
	    });
	    
	    switchButton = new Button("Zum Login");;
        switchButton.addClickListener(listener -> getUI().getNavigator().navigateTo(Views.LOGIN.getView()));
	    
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
	    this.addComponents(register, username, email, location, password, controlPassword, registerButton, switchButton);
	    this.setMargin(true);
	    this.setSpacing(true);
	}

}
