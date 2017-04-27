package de.meets.gui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import de.meets.asset_manager.CategoryManager;
import de.meets.asset_manager.LocationManager;
import de.meets.asset_manager.MeetingManager;
import de.meets.asset_manager.MemberManager;
import de.meets.assets.Member;
import de.meets.vaadin_archetype_application.MeetsUI;

public abstract class MeetsView implements View {
	
	private static final long serialVersionUID = 289686071533702233L;
	
	
	private final ViewName viewName;
	private MeetsUI meetsUI;
	
	public MeetsView( ViewName viewName, MeetsUI meetsUI ) {
		this.viewName = viewName;
		this.meetsUI = meetsUI;
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
		MemberManager memberManager = this.meetsUI.getMemberManager();
		memberManager.update(updatedMember);
		meetsUI.updateUser();
	}
		
	// Get asset manager
	public CategoryManager getCategoryManager() {
		return this.meetsUI.getCategoryManager();
	}
	
	public LocationManager getLocationManager() {
		return this.meetsUI.getLocationManager();
	}
	
	public MeetingManager getMeetingManager() {
		return this.meetsUI.getMeetingManager();
	}
	
	public MemberManager getMemberManager() {
		return this.meetsUI.getMemberManager();
	}
	
	// Get name of view
	public ViewName getViewName() {
		return viewName;
	}

	
}
