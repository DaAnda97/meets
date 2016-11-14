package de.meets.vaadin_archetype_application;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	final VerticalLayout mainLayout = new VerticalLayout();
    	mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    	
        final ClassResource logo = new ClassResource("/images/logo.png");
    	
    	//Login
        final VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        final TextField name = new TextField("Benutzername");
        final PasswordField password = new PasswordField("Passwort");

        Button loginButton = new Button("Login");
        loginButton.addClickListener( e -> {
        	if (name.getValue().equals("Andi") && password.getValue().equals("1234")){
        		loginLayout.addComponent(new Label("Hi, Andi. Du bist eingeloggt"));
        	}
        	else{
        		loginLayout.addComponent(new Label("Gescheitert!"));
        	}
        });
        
        loginLayout.addComponents(name, password, loginButton);
        loginLayout.setSizeFull();
        loginLayout.setSpacing(true);
        
        
        //Register
        final VerticalLayout registerLayout = new VerticalLayout();
        final Label register = new Label("Registrieren");
        final TextField registerName = new TextField("Benutzername");
        final TextField registerMail = new TextField("E-Mail");
        final PasswordField registerPassword = new PasswordField("Passwort");
        final PasswordField controlRegisterPassword = new PasswordField("Passwort wiederholen");

        Button registerButton = new Button("Registrieren");
        registerButton.addClickListener( e -> {
        	registerLayout.addComponent(new Label("Registrieren!"));
        });
        
        registerLayout.addComponents(register, registerName, registerMail, registerPassword, controlRegisterPassword, registerButton);
        registerLayout.setMargin(true);
        registerLayout.setSpacing(true);
        
        Button switchButton = new Button("Noch nicht registriert?");
        
        mainLayout.addComponents(new Image(null,logo), loginLayout, switchButton);
        mainLayout.setSizeFull();
        
        this.setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
