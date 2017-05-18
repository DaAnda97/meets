package de.meets.vaadin_archetype_application;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Member;
import de.meets.gui.ViewName;
import de.meets.gui.views.Footer;
import de.meets.gui.views.Header;
import de.meets.gui.views.Impressum;
import de.meets.gui.views.Login;
import de.meets.gui.views.MeetingInformation;
import de.meets.gui.views.MeetingOverview;
import de.meets.gui.views.Register;
import de.meets.gui.views.ShowUser;
import de.meets.hibernate.DatabaseConnector;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
public class MeetsUI extends UI{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 69546722268473890L;

	private DatabaseConnector databaseConnector;
	
	private Member registeredMember;
	
	private Navigator navigator;
	
	private Header header;
	private Panel mainView;
	private Footer footer;

	@Override
    protected void init(VaadinRequest request) {
		// Database-Connection
		this.databaseConnector = new DatabaseConnector();
		
		// GUI
		header = new Header(this);
		mainView = new Panel();
		footer = new Footer(this);
		
		VerticalLayout mainLayout = new VerticalLayout(); // VerticalLayout(header, mainView, footer)
		mainLayout.setSpacing(true);
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		mainLayout.addComponent(header);
		mainLayout.addComponent(mainView);
		mainLayout.addComponent(footer);
		
		
		// Views
        navigator = new Navigator(this, mainView);
        navigator.addView(ViewName.LOGIN.toString(), new Login(ViewName.LOGIN, this));
        navigator.addView(ViewName.REGISTER.toString(), new Register(ViewName.REGISTER, this));
        navigator.addView(ViewName.PROFILE.toString(), new ShowUser(ViewName.PROFILE, this));
        navigator.addView(ViewName.MEETS.toString(), new MeetingOverview(ViewName.MEETS, this));
        navigator.addView(ViewName.MEET.toString(), new MeetingInformation(ViewName.MEET, this));
        navigator.addView(Impressum.NAME, new Impressum());
        
        navigator.navigateTo(Login.NAME);        
        
        this.setContent(mainLayout);
        getPage().setTitle("Meets"); 
        
    }	
	
	@SuppressWarnings("serial")
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MeetsUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {}

	public void login(Member loginMember){
		this.registeredMember = loginMember;
		header.addShowUser();
		header.addLogout();
		navigator.navigateTo(ViewName.MEETS.toString());
	}
	
	public void logout(){
		this.registeredMember = null;
		header.removeLogout();
		header.removeShowUser();
		navigator.navigateTo(Login.NAME);
	}
	
	public void showUser() {
		this.navigator.navigateTo(ViewName.PROFILE.toString());
	}
	
	public Navigator getNavigator() {
		return this.navigator;
	}
	
	// Get registered member
	public Member getRegistratedMember() {
		return this.registeredMember;
	}
	
	public DatabaseConnector getDatabaseConnector() {
		return this.databaseConnector;
	}
	
	// Use to adapt registered member
	public void updateUser(Member user) {
		this.registeredMember = user;
	}
	
}

