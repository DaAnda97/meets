package de.meets.vaadin_archetype_application;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Login extends VerticalLayout implements View{
	
	@Override
	public void enter(ViewChangeEvent event) {
        
        ClassResource logo = new ClassResource("/images/logo.png");
        TextField name = new TextField("Benutzername");
        PasswordField password = new PasswordField("Passwort");

        Button loginButton = new Button("Login");
        loginButton.addClickListener( e -> {
        	if (name.getValue().equals("Andi") && password.getValue().equals("1234")){
        		this.addComponent(new Label("Hi, Andi. Du bist eingeloggt"));
        	}
        	else{
        		this.addComponent(new Label("Gescheitert!"));
        	}
        });
        
        Button switchButton = new Button("Noch nicht registriert?");
        switchButton.addClickListener(listener -> getUI().getNavigator().navigateTo("Register"));
        
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponents(new Image(null,logo), name, password, loginButton, switchButton);
        this.setSizeFull();
        this.setSpacing(true);
	}

}
