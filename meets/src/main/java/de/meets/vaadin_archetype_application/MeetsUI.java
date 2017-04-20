package de.meets.vaadin_archetype_application;

import javax.servlet.annotation.WebServlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
import de.meets.hibernate.DatabaseConnector;
import de.meets.views.Footer;
import de.meets.views.Header;
import de.meets.views.Impressum;
import de.meets.views.Login;
import de.meets.views.MeetingInformation;
import de.meets.views.MeetingOverview;
import de.meets.views.Register;
import de.meets.views.ShowUser;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
public class MeetsUI extends UI{
	
	Navigator navigator;
	private MemberManager memberManager;
	private LocationManager locationManager;
	private Member registratedMember;
	
	private Header header = new Header(this);
	private Panel mainView = new Panel();
	private Footer footer = new Footer(this);

	@Override
    protected void init(VaadinRequest request) {
		DatabaseConnector databaseConnector = new DatabaseConnector();
		memberManager = new MemberManager(databaseConnector);
		locationManager = new LocationManager(databaseConnector);
		
		VerticalLayout mainLayout = new VerticalLayout(header, mainView, footer);

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
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MeetsUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {}

	public void login(Member loginMember){
		registratedMember = loginMember;
		header.addShowUser();
		header.addLogout();
		navigator.navigateTo(MeetingOverview.NAME);
	}
	
	public void logout(){
		registratedMember = null;
		header.removeLogout();
		header.removeShowUser();
		navigator.navigateTo(Login.NAME);
	}

	public void deleteUser(){
		memberManager.delete(registratedMember);
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
		getNavigator().navigateTo(ShowUser.NAME);
	}
	
	public Navigator getNavigator() {
		return navigator;
	}
	
	public MemberManager getMemberManager() {
		return memberManager;
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}
	
	public Member getRegistratedMember() {
//		System.out.println("Moin, i bims da Member: " + registratedMember.getUsername() + ", " + registratedMember.getEmail() + ", "
//				+ registratedMember.getFirstName() + ", " + registratedMember.getLastName());
		return registratedMember;
	}
	
}
	
//	Navigator navigator;
//	VerticalLayout mainLayout = new VerticalLayout();
//	
//	Header header = new Header();
//	Panel mainView = new Panel();
//	Footer footer = new Footer();
//	
//    private Member registratedMember = null;
//    private MemberManager memberManager = new MemberManager();
//    private LocationManager locationManager = new LocationManager();
//    private MeetingManager meetingManager = new MeetingManager();
//    private CategoryManager categoryManager = new CategoryManager();
//    
//	@Override
//    protected void init(VaadinRequest request) {
//		// Create a navigator to control the views
//		navigator = new Navigator(this, mainView);
//		
//		// Create and register the views
//		for (Views view : Views.values()){
//			navigator.addView(view.getView(), view.getItsClass());
//		}
//		navigator.navigateTo(Views.LOGIN.getView());
//		
//		
//        getPage().setTitle("Meets");
//        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//    	mainLayout.addComponents(header, mainView, footer);
//        mainLayout.setMargin(true);
//        mainLayout.setSpacing(true);
//        this.setContent(mainLayout);
//        
//    }
//
//    
//	public boolean isValidEmailAddress(String email) {
//        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
//        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
//        java.util.regex.Matcher m = p.matcher(email);
//        return m.matches();
//	}
//	

//	
//	public static String shaHash (String password) throws NoSuchAlgorithmException{
//		MessageDigest md = MessageDigest.getInstance("SHA-256");
//        md.update(password.getBytes());
//
//        byte byteData[] = md.digest();
//        
//        //convert the byte to hex
//        StringBuffer hexString = new StringBuffer();
//    	for (int i=0;i<byteData.length;i++) {
//    		String hex=Integer.toHexString(0xff & byteData[i]);
//   	     	if(hex.length()==1) hexString.append('0');
//   	     	hexString.append(hex);
//    	}
//    	
//    	return hexString.toString();
//	}
//
//	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//    @VaadinServletConfiguration(ui = MeetsUI.class, productionMode = false)
//    public static class MyUIServlet extends VaadinServlet {}
//

