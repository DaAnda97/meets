package de.meets.views;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.server.UserError;

import de.meets.asset_manager.MemberManager;
import de.meets.assets.Member;
import de.meets.vaadin_archetype_application.MeetsUI;
import de.meets.vaadin_archetype_application.Views;


public class Login extends VerticalLayout implements View{
	Label login = new Label("Anmelden");
	TextField email = new TextField("E-Mail");
	PasswordField password = new PasswordField("Passwort");
	Button loginButton = new Button("Login");
	
	MemberManager memberManager = new MemberManager();
	
	@Override
	public void enter(ViewChangeEvent event) {
		
        loginButton.addClickListener( e -> {
        	String emailValue = email.getValue().trim();
        	
        	String shaPassword;
			try {
				shaPassword = MeetsUI.shaHash(password.getValue().trim());
			} catch (Exception e1) {
				password.setComponentError(new UserError("Internal error - Please try later again"));
				e1.printStackTrace();
				return; //Cancel, because the password is not hashed
			}
        	
        	if (MeetsUI.isValidEmailAddress(emailValue))
        	{
        		if(memberManager.checkEMail(emailValue))
        		{
        			Member member = memberManager.checkLogin(emailValue, shaPassword);
        			if (member != null)
        			{
        				MeetsUI.setRegistratedMember(member);
        				getUI().getNavigator().navigateTo(Views.MEETING_OVERVIEW.getView());
        			} else 
        			{
        				password.setComponentError(new UserError("Falsches Passwort!"));
        			}
        		} else 
        		{
        			email.setComponentError(new UserError("E-Mail ist uns nicht bekannt!"));
        		}
        	} else 
        	{
        		email.setComponentError(new UserError("Ungültige E-Mail"));
        	}
        	
        });
        
        Button switchButton = new Button("Noch nicht registriert?");
        switchButton.addClickListener(listener -> getUI().getNavigator().navigateTo(Views.REGISTER.getView()));
        
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponents(login, email, password, loginButton, switchButton);
//        this.setSizeFull();
        this.setMargin(true);
        this.setSpacing(true);
	}
	
}
