package de.meets.gui.extendedComponents;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import de.meets.assets.Meeting;

public class MeetingComponent extends CustomComponent{

	private static final long serialVersionUID = 1L;
	private Label date;
	private Label description;
	private Label cathegory;
	private Label location;
	private Label members;

	public MeetingComponent(Meeting meeting) {
		date = new Label(meeting.MEETING_DATE);
		description = new Label(meeting.MEETING_DESCRIPTION);
		cathegory = new Label(meeting.MEETING_CATEGORY);
		location = new Label(meeting.MEETING_LOCATION);
		members = new Label(meeting.getMembers().size() + "");
		
		Panel mainPanel = new Panel();
		GridLayout layout = new GridLayout(3, 3);
		
		layout.addComponent(date);
		layout.addComponent(new Label(""));
		layout.addComponent(cathegory);
		
		layout.addComponent(new Label(""));
		layout.addComponent(description);
		layout.addComponent(new Label(""));
		
		layout.addComponent(new Label(""));
		layout.addComponent(location);
		layout.addComponent(members);
		
		mainPanel.setContent(layout);
		setCompositionRoot(mainPanel);
	}
	
}
