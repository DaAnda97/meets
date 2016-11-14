package de.meets.vaadin_archetype_application;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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
    	final HorizontalLayout mainLayout = new HorizontalLayout();
    	
    	//Login
        final VerticalLayout loginLayout = new VerticalLayout();
        final Label login = new Label("Please enter your user name and your password:");
        final TextField name = new TextField("user name");
        final PasswordField password = new PasswordField("password");

        Button loginButton = new Button("Login");
        loginButton.addClickListener( e -> {
        	if (name.getValue().equals("Andi") && password.getValue().equals("1234")){
        		loginLayout.addComponent(new Label("Hi Andi. You are logged in!"));
        	}
        	else{
        		loginLayout.addComponent(new Label("Faild. Retry!"));
        	}
        });
        
        loginLayout.addComponents(login, name, password, loginButton);
        loginLayout.setMargin(true);
        loginLayout.setSpacing(true);
        
        
        //Register
        final VerticalLayout registerLayout = new VerticalLayout();
        final Label register = new Label("Please enter your user name and your password:");
        final TextField registerName = new TextField("user name");
        final TextField registerMail = new TextField("e-Mail");
        final PasswordField registerPassword = new PasswordField("password");
        final PasswordField controlRegisterPassword = new PasswordField("password again");

        Button registerButton = new Button("Register");
        registerButton.addClickListener( e -> {
        	registerLayout.addComponent(new Label("Registered!"));
        });
        
        registerLayout.addComponents(register, registerName, registerMail, registerPassword, controlRegisterPassword, registerButton);
        registerLayout.setMargin(true);
        registerLayout.setSpacing(true);
        
        mainLayout.addComponent(loginLayout);
        mainLayout.addComponent(registerLayout);
        this.setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
