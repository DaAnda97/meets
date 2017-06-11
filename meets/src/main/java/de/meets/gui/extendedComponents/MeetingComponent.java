package de.meets.gui.extendedComponents;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Meeting;
import de.meets.assets.Member;

public class MeetingComponent extends CustomComponent{

	private static final long serialVersionUID = 1L;
	private Label date;
	private Label description;
	private Label cathegory;
	private Label location;
	private Label members;
	
	public MeetingComponent(Meeting meeting, Member registratedMember) {
		date = new Label(meeting.getDate().toString());
		description = new Label(meeting.getDescription());
		cathegory = new Label("Kategorie: " + meeting.getCategory().getTitle());
		location = new Label("Ort: " + meeting.getLocation().getCity());
		members = new Label("Teilnehmer: " + meeting.getMaxMembers());
		
		description.addStyleName("meetsdesc");
		
		Panel mainPanel = new Panel();
		GridLayout layout = new GridLayout(3, 3);
		layout.setWidth(50, Unit.PERCENTAGE);
		
		layout.addComponent(date);
		layout.setComponentAlignment(date, Alignment.MIDDLE_LEFT);
		layout.addComponent(new Label(""));
		layout.addComponent(cathegory);
		layout.setComponentAlignment(cathegory, Alignment.MIDDLE_LEFT);
		
		layout.addComponent(new Label(""));
		layout.addComponent(description);
		layout.setComponentAlignment(description, Alignment.MIDDLE_CENTER);
		layout.addComponent(new Label(""));
		
		layout.addComponent(new Label(""));
		layout.addComponent(location);
		layout.setComponentAlignment(location, Alignment.MIDDLE_RIGHT);
		layout.addComponent(members);
		layout.setComponentAlignment(members, Alignment.MIDDLE_RIGHT);
		
		layout.addLayoutClickListener(event -> {
			MeetingInformation info = new MeetingInformation(meeting, registratedMember);
			UI.getCurrent().addWindow(info);
		});
		
		
		VerticalLayout upperLayout = new VerticalLayout();
		upperLayout.setSizeFull();
		upperLayout.addComponent(layout);
		upperLayout.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
		mainPanel.setContent(upperLayout);
		setCompositionRoot(mainPanel);
	}
	
}
