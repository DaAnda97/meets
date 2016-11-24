package de.meets.vaadin_archetype_application;

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
	Label register = new Label("Registrieren");
	TextField username = new TextField("Benutzername");
	TextField email = new TextField("E-Mail");
	TextField location = new TextField("Ort");
	PasswordField password = new PasswordField("Passwort");
	PasswordField controlPassword = new PasswordField("Passwort wiederholen");
	Button registerButton = new Button("Registrieren");
	
	Button switchButton = new Button("Zum Login");
	
	MemberManager memberManager = new MemberManager();
	
	@Override
	public void enter(ViewChangeEvent event) {
	    registerButton.addClickListener( e -> {
	    	String usernameValue = username.getValue().trim();
	    	String emailValue = email.getValue().trim();
	    	String locationValue = location.getValue().trim();
	    	String passwordValue = password.getValue().trim();
	    	String controlPasswordValue = controlPassword.getValue().trim();
	    	
	    	if (passwordValue.equals(controlPasswordValue)){
	    		if (Login.isValidEmailAddress(emailValue)){
	    			// Noch E-Mail und Benutzer auf Eindeutigkeit prüfen
	    			// Location ID über Location Manager get(plz) abfragen
	    			Member member = new Member(0, usernameValue, null, null, passwordValue, 
	    									emailValue, new Location(1), null, null);
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
	    
        switchButton.addClickListener(listener -> getUI().getNavigator().navigateTo("Login"));
	    
	    this.addComponents(register, username, email, location, password, controlPassword, registerButton, switchButton);
	    this.setMargin(true);
	    this.setSpacing(true);
	}

}
