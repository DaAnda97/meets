package de.meets.vaadin_archetype_application;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
public class NavigatorUI extends UI {
    Navigator navigator;
    protected static final String MAINVIEW = "main";

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView("Login", new Login());
        navigator.addView(MAINVIEW, new Register());
        
        navigator.navigateTo("Login");
    }
}
//@Theme("mytheme")
//public class MyUI extends UI {
//
//    @Override
//    protected void init(VaadinRequest vaadinRequest) {
//    	final VerticalLayout mainLayout = new VerticalLayout();
//    	mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//    	new Navigator(this, mainLayout);
//    	ClassBasedViewProvider cbvp = new ClassBasedViewProvider("Test", Register.class);
//    	getUI().getNavigator().addProvider(cbvp);
//    	
//    	ClassBasedViewProvider cbvp2 = new ClassBasedViewProvider("", TestTest.class);
//    	getUI().getNavigator().addProvider(cbvp2);
//        
//    	
//
//        
//        mainLayout.setSizeFull();
//        
//        
//        this.setContent(mainLayout);
//    }
//
//    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
//    public static class MyUIServlet extends VaadinServlet {
//    }
//}
