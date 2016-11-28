package de.meets.views;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.asset_manager.MemberManager;
import de.meets.assets.Member;
import de.meets.vaadin_archetype_application.MeetsUI;


public class Login extends VerticalLayout implements View{
//        ClassResource logo = new ClassResource("/images/logo.png");
	Label login = new Label("Anmelden");
	TextField email = new TextField("Benutzername");
	PasswordField password = new PasswordField("Passwort");
	Button loginButton = new Button("Login");
	
	MemberManager memberManager = new MemberManager();
	
	@Override
	public void enter(ViewChangeEvent event) {
		
        loginButton.addClickListener( e -> {
        	String emailValue = email.getValue().trim();
        	String passwordValue = password.getValue().trim();
        	
        	if (isValidEmailAddress(emailValue)){
        		login.setCaption("E-Mail valide!");
        		Member member = memberManager.checkLogin(emailValue, passwordValue);
        		if (member != null){
        			MeetsUI.setRegistratedMember(member);
            		getUI().getNavigator().navigateTo("ShowUser");
            	} else {
            		login.setCaption("Passwort oder E-Mail-Adresse falsch!");
            	}
        	} else {
        		login.setCaption("Bitte geben Sie eine valide E-Mail ein!");
        	}
        	
        });
        
        Button switchButton = new Button("Noch nicht registriert?");
        switchButton.addClickListener(listener -> getUI().getNavigator().navigateTo("Register"));
        
//        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//        this.addComponents(new Image(null,logo), name, password, loginButton, switchButton);
        this.addComponents(login, email, password, loginButton, switchButton);
//        this.setSizeFull();
        this.setMargin(true);
        this.setSpacing(true);
	}
	
	public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
 }

}
