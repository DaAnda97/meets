package de.meets.vaadin_archetype_application;

import javax.servlet.annotation.WebServlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.meets.asset_manager.CategoryManager;
import de.meets.asset_manager.LocationManager;
import de.meets.asset_manager.MeetingManager;
import de.meets.asset_manager.MemberManager;
import de.meets.assets.Member;
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
	
	private CategoryManager categoryManager;
	private LocationManager locationManager;
	private MeetingManager meetingManager;
	private MemberManager memberManager;
	private Member registeredMember;
	
	private Navigator navigator;
	
	private Header header;
	private Panel mainView;
	private Footer footer;

	@Override
    protected void init(VaadinRequest request) {
		// Database-Connection
		this.databaseConnector = new DatabaseConnector();
		// Asset-Manager
		this.categoryManager = new CategoryManager(databaseConnector);
		this.locationManager = new LocationManager(databaseConnector);
		this.meetingManager = new MeetingManager(databaseConnector);
		this.memberManager = new MemberManager(databaseConnector);
		
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
        navigator.addView(Login.NAME, new Login(this));
        navigator.addView(Register.NAME, new Register(this));
        navigator.addView(ShowUser.NAME, new ShowUser(this));
        navigator.addView(MeetingOverview.NAME, new MeetingOverview(this));
        navigator.addView(MeetingInformation.NAME, new MeetingInformation(this));
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
		navigator.navigateTo(MeetingOverview.NAME);
	}
	
	public void logout(){
		this.registeredMember = null;
		header.removeLogout();
		header.removeShowUser();
		navigator.navigateTo(Login.NAME);
	}

	public void deleteUser(){
		memberManager.delete(this.registeredMember);
		logout();
	}
	
	public String shaHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}
	
	public void showUser() {
		this.navigator.navigateTo(ShowUser.NAME);
	}
	
	public Navigator getNavigator() {
		return this.navigator;
	}

	// Get asset managers
	public CategoryManager getCategoryManager() {
		return this.categoryManager;
	}
	
	public LocationManager getLocationManager() {
		return this.locationManager;
	}
	
	public MeetingManager getMeetingManager() {
		return this.meetingManager;
	}
	
	public MemberManager getMemberManager() {
		return this.memberManager;
	}
	
	// Get registered member
	public Member getRegistratedMember() {
		return this.registeredMember;
	}
	
	/*
	public DatabaseConnector getDatabaseConnector() {
		return this.databaseConnector;
	}
	*/
	// Use to adapt registered member
	public void updateUser() {
		this.memberManager.get(this.registeredMember.getMemberID());
	}
	
}

