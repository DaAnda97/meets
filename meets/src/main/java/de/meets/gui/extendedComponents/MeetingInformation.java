package de.meets.gui.extendedComponents;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.meets.assets.Meeting;
import de.meets.assets.Member;
import de.meets.gui.ViewName;

// Informationen zu einem Meet
public class MeetingInformation extends Window {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2420734523715598238L;
	
	public MeetingInformation(Meeting meeting, Member registratedMember) {
		super(meeting.getTitle());
		
		VerticalLayout inputLayout = new VerticalLayout();
		inputLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		inputLayout.setSizeFull();
		inputLayout.setMargin(true);
		inputLayout.setSpacing(true);

		Label date = new Label(meeting.getDate().toString());
		date.setIcon(FontAwesome.CALENDAR);
		
		Label category = new Label(meeting.getCategory().getTitle());
		category.setIcon(FontAwesome.BOOKMARK);
		
		Label title = new Label(meeting.getTitle());
		title.setIcon(FontAwesome.TICKET);
		
		Label description = new Label(meeting.getDescription().toString());
		description.setIcon(FontAwesome.BOOK);
		
		Label time = new Label(meeting.getTime().toString());
		time.setIcon(FontAwesome.CLOCK_O);
		
		Label location = new Label(meeting.getLocation().getCity());
		location.setIcon(FontAwesome.LOCATION_ARROW);
		
		Label members = new Label(meeting.getMembers().size() + "");
		members.setIcon(FontAwesome.USER_PLUS);
		
		inputLayout.addComponents(date, category, title, description, time, location, members);

		// --------------------- ButtonLayout ----------------------
		HorizontalLayout button1Layout = new HorizontalLayout();
		button1Layout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		button1Layout.setSizeFull();
		Button button1 = new Button();
		if (meeting.getCreator().getEmail().equals(registratedMember.getEmail())){
			button1.setCaption("Bearbeiten");
			button1.addClickListener(e -> {
				getUI().getNavigator().navigateTo(ViewName.CREATE.toString());
				// TODO ID mitgeben
			});
		} else {
			button1.setCaption("Beitreten");
			button1.addClickListener(e -> {
				// TODO beitreten
			});
		}
		button1Layout.addComponent(button1);

		HorizontalLayout cancelLayout = new HorizontalLayout();
		cancelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		cancelLayout.setSizeFull();
		Button cancel = new Button("Abbrechen");
		cancel.addClickListener(e -> {
			close();
		});
		cancelLayout.addComponent(cancel);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		buttonLayout.setWidth(25, Unit.PERCENTAGE);
		buttonLayout.addComponents(button1Layout, cancelLayout);

		// -------------------------- MainLayout -------------------------------

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.addComponents(inputLayout, buttonLayout);
		this.setContent(mainLayout);
		

		
		center();
		this.setWidth(25, Unit.PERCENTAGE);
		this.setHeight(70, Unit.PERCENTAGE);
	}
	
	
}
