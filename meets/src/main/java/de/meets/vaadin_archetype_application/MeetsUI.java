package de.meets.vaadin_archetype_application;

import java.util.Iterator;

import javax.servlet.annotation.WebServlet;

















import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Member;
import de.meets.views.Footer;
import de.meets.views.Header;
import de.meets.views.Impressum;
import de.meets.views.Login;
import de.meets.views.Register;
import de.meets.views.ShowUser;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
public class MeetsUI extends UI implements View{
	Navigator navigator;
	VerticalLayout mainLayout = new VerticalLayout();
	
	static Header header = new Header();
	Panel mainView = new Panel();
	Footer footer = new Footer();
	
    protected static Member registratedMember = null;

    @Override
    public void enter(ViewChangeEvent event) {
    	
    }
    
	@Override
    protected void init(VaadinRequest request) {
		// Create a navigator to control the views
		navigator = new Navigator(this, mainView);
		
		// Create and register the views
		for (Views view : Views.values()){
			navigator.addView(view.getView(), view.getItsClass());
		}
		
		navigator.navigateTo(Views.LOGIN.getView());
		
		
        getPage().setTitle("Meets");
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    	mainLayout.addComponents(header, mainView, footer);
//    	mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        this.setContent(mainLayout);
        
    }

	public static Member getRegistratedMember() {
		return registratedMember;
	}
	
	public static void setRegistratedMember(Member registratedMember) {
		MeetsUI.registratedMember = registratedMember;
		header.addShowUser();
	}
    
	public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MeetsUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {}


}