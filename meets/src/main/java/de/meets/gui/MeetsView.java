package de.meets.gui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

import de.meets.asset_manager.CategoryManager;
import de.meets.asset_manager.LocationManager;
import de.meets.asset_manager.MeetingManager;
import de.meets.asset_manager.MemberManager;
import de.meets.assets.Member;
import de.meets.hibernate.DatabaseConnector;
import de.meets.vaadin_archetype_application.MeetsUI;

public abstract class MeetsView extends CustomComponent implements View {
	
	private static final long serialVersionUID = 289686071533702233L;
	
	private CategoryManager categoryManager;
	private LocationManager locationManager;
	private MeetingManager meetingManager;
	private MemberManager memberManager;
	
	private final ViewName viewName;
	private MeetsUI meetsUI;
	
	/**
	 * 
	 * @param viewName
	 * @param meetsUI
	 */
	public MeetsView( ViewName viewName, MeetsUI meetsUI ) {
		this.viewName = viewName;
		this.meetsUI = meetsUI;
		
		DatabaseConnector conn = this.meetsUI.getDatabaseConnector();
		this.categoryManager = new CategoryManager(conn);
		this.locationManager = new LocationManager(conn);
		this.meetingManager = new MeetingManager(conn);
		this.memberManager = new MemberManager(conn);
		
	}
	
	
	abstract public void enter(ViewChangeEvent event);
	
	
	public void navigateTo(ViewName viewName) {
		meetsUI.getNavigator().navigateTo(viewName.toString());
	}
	
	// Get/update member
	public Member getRegistratedMember() {
		return this.meetsUI.getRegistratedMember();
	} 
	
	public void updateRegistratedMember(Member updatedMember) {
		this.memberManager.update(updatedMember);
		Member user = memberManager.get(updatedMember.getMemberID());
		meetsUI.updateUser(user);
	}
		
	// Login/-out
	public void login(Member user) {
		this.meetsUI.login(user);
	}
	
	public void logout() {
		this.meetsUI.logout();
	}
	
	// Get asset manager
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
	
	// Get name of view
	public ViewName getViewName() {
		return viewName;
	}

	
}
