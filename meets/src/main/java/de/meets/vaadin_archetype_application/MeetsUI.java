package de.meets.vaadin_archetype_application;

import javax.servlet.annotation.WebServlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.meets.asset_manager.MemberManager;
import de.meets.assets.Member;
import de.meets.views.Footer;
import de.meets.views.Header;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
public class MeetsUI extends UI implements View{
	static Navigator navigator;
	VerticalLayout mainLayout = new VerticalLayout();
	
	static Header header = new Header();
	Panel mainView = new Panel();
	static Footer footer = new Footer();
	
    private static Member registratedMember = null;
	private static MemberManager memberManager = new MemberManager();

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
		header.addLogout();
	}
    
	public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	public static void logout(){
		registratedMember = null;
		header.removeLogout();
		header.removeShowUser();
		navigator.navigateTo(Views.LOGIN.getView());
	}
	
	public static void deleteUser(){
		memberManager.delete(registratedMember);
		logout();
	}
	
	public static String shaHash (String password) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();
        
        //convert the byte to hex
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	
    	return hexString.toString();
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MeetsUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {}


}