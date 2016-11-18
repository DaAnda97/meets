package de.meets.vaadin_archetype_application;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ShowUser extends VerticalLayout implements View{
	
	@Override
	public void enter(ViewChangeEvent event) {

	    Label message = new Label("Du bist jetzt angemeldet!");
//	    TextField registerName = new TextField("Benutzername");
//	    TextField registerMail = new TextField("E-Mail");
//	    PasswordField registerPassword = new PasswordField("Passwort");
//	    PasswordField controlRegisterPassword = new PasswordField("Passwort wiederholen");
//
//	    Button registerButton = new Button("Registrieren");
//	    registerButton.addClickListener( e -> {
//	    	this.addComponent(new Label("Registrieren!"));
//	    	
//	    });
//	    
//	    Button switchButton = new Button("Zum Login");
//        switchButton.addClickListener(listener -> getUI().getNavigator().navigateTo("Login"));
//	    
//	    this.addComponents(register, registerName, registerMail, registerPassword, controlRegisterPassword, registerButton, switchButton);
	    this.addComponent(message);
	    this.setMargin(true);
	    this.setSpacing(true);
	}
}
