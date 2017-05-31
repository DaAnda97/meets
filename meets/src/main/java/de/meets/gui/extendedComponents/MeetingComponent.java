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
	private Label x;

	public MeetingComponent(Meeting meeting) {
		date = new Label(meeting.getDate().toLocaleString());
		description = new Label(meeting.getDescription());
		cathegory = new Label("Kategorie: " + meeting.getCategory().getTitle());
		location = new Label("Ort: " + meeting.getLocation().getCity());
		members = new Label("Teilnehmer: " + meeting.getMaxMembers());
		
		description.addStyleName("meetsdesc");
		
		Panel mainPanel = new Panel();
		GridLayout layout = new GridLayout(3, 3);
		layout.setSizeFull();
		
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
