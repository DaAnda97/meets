package de.meets.gui.extendedComponents;

import com.vaadin.ui.Window;

import de.meets.assets.Meeting;

// Informationen zu einem Meet
public class MeetingInformation extends Window {
	Meeting meeting;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2420734523715598238L;

	public MeetingInformation(Meeting meeting) {
		super(meeting.getTitle());
		this.meeting = meeting;
		
		center();
	}
	
	public MeetingInformation() {
		super("Neues Meeting erstellen");
		
		center();
	}
	
	

}
