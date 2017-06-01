package de.meets.gui.extendedComponents;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.meets.assets.Meeting;

// Informationen zu einem Meet
public class MeetingInformation extends Window {
	private Meeting meeting;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2420734523715598238L;
	
	public MeetingInformation(Meeting meeting) {
		super(meeting.getTitle());
		this.meeting = meeting;
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		
		Label date = new Label(meeting.getDate().toString());
		date.setIcon(FontAwesome.CALENDAR);
		
		Label category = new Label(meeting.getCategory().getTitle());
		category.setIcon(FontAwesome.BOOKMARK);
		
		Label title = new Label(meeting.getTitle());
		
		Label description = new Label(meeting.getDescription().toString());
		
		Label time = new Label(meeting.getTime().toString());
		time.setIcon(FontAwesome.CLOCK_O);
		
		Label location = new Label(meeting.getLocation().getCity());
		location.setIcon(FontAwesome.LOCATION_ARROW);
		
		Label members = new Label(meeting.getMembers().size() + "");
		members.setIcon(FontAwesome.USER_PLUS);
		
		layout.addComponents(date, category, title, description, time, location, members);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(category, Alignment.MIDDLE_RIGHT);
		layout.setMargin(true);
		this.setContent(layout);
		
		
		center();
		this.setWidth(25, Unit.PERCENTAGE);
		this.setHeight(70, Unit.PERCENTAGE);
	}
	
	
}
