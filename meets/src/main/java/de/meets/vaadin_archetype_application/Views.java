package de.meets.vaadin_archetype_application;

import de.meets.views.Impressum;
import de.meets.views.Login;
import de.meets.views.MeetingInformation;
import de.meets.views.MeetingOverview;
import de.meets.views.Register;
import de.meets.views.ShowUser;

public enum Views {
	
	IMPRESSUM ("Impressum", Impressum.class),
	MEETING_INFORMATION("MeetingInformation", MeetingInformation.class),
	MEETING_OVERVIEW("MeetingOverview", MeetingOverview.class),
	SHOW_USER("ShowUser", ShowUser.class),
	LOGIN("LOGIN", Login.class),
	REGISTER("Register", Register.class), ;
	
	
	String view;
	Class itsClass;
	
	Views (String view, Class itsClass){
		this.view = view;
		this.itsClass = itsClass;
	}

	public Class getItsClass() {
		return itsClass;
	}

	public void setItsClass(Class itsClass) {
		this.itsClass = itsClass;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

}
