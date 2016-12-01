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



import de.meets.asset_manager.LocationManager;
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
	TextField firstName;
	TextField lastName;
	PasswordField password;
	PasswordField controlPassword;
	Button registerButton;
	Button switchButton;
	
	MemberManager memberManager = new MemberManager();
	LocationManager locationManager = new LocationManager();
	
	@Override
	public void enter(ViewChangeEvent event) {
		final OpenStreetMapGeocoder geocoder = OpenStreetMapGeocoder.getInstance();
		geocoder.setLimit(25);
		location = new LocationTextField<GeocodedLocation>(geocoder, GeocodedLocation.class);
		location.setCaption("Adresse");
        location.setImmediate(true);
        location.setInputPrompt("");
        location.setRequired(true);
        
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
        email.setRequired(true);
        password = new PasswordField("Passwort");
        password.setRequired(true);
        controlPassword = new PasswordField("Passwort wiederholen");
        controlPassword.setRequired(true);
        
        firstName = new TextField("Vorname");
        lastName = new TextField("Nachname");
        
        registerButton = new Button("Registrieren");
	    registerButton.addClickListener( e -> {
	    	if (password.getValue().equals(controlPassword.getValue()))
	    	{
	    		if (MeetsUI.isValidEmailAddress(email.getValue()))
	    		{
	    			if (!memberManager.checkEMail(email.getValue())){
	    				if (location.getLocation() != null){
	    					
	    					//Generate Location
	    					Location position = new Location(location.getText(), 
	    							location.getLocation().getLon(), location.getLocation().getLat());
	    					
	    					if ( locationManager.get(position.getCity()) == null ) {
	    						locationManager.add(position);
		    					System.out.println("Inster into DB: " +position);
	    					}
	    					
    						position = locationManager.get(position.getCity());
    						
	    					//Hash Password
	    					String shaPassword;
							try {
								shaPassword = MeetsUI.shaHash(password.getValue().trim());
							} catch (Exception e1) {
								password.setComponentError(new UserError("Internal error - Please try later again"));
								e1.printStackTrace();
								return; //Cancel, because the password is not hashed
							}
	    					
	    					//Generate Member
	    					Member member = new Member(username.getValue().trim(), null, null, 
	    							shaPassword, email.getValue().trim(), position);
	    					member.setFirstName(firstName.getValue().trim());
	    					member.setLastName(lastName.getValue().trim());
	    					
	    					//Add Member
	    					memberManager.add(member);
	    					MeetsUI.setRegistratedMember(member);
	    					getUI().getNavigator().navigateTo(Views.MEETING_OVERVIEW.getView());
	    					
	    				} else {
	    					location.setComponentError(new UserError("Wähle eine Adresse aus der Liste! (Drücke Enter)"));
	    				}
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
	    this.addComponents(register, username, email, location, firstName, lastName, password, controlPassword, registerButton, switchButton);
	    this.setMargin(true);
	    this.setSpacing(true);
	}
	

}
