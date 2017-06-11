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
		date.setCaption("Datum");
		date.setIcon(FontAwesome.CALENDAR);

		Label category = new Label(meeting.getCategory().getTitle());
		category.setCaption("Kategorie");
		category.setIcon(FontAwesome.BOOKMARK);

		Label title = new Label(meeting.getTitle());
		title.setCaption("Titel");
		title.setIcon(FontAwesome.TICKET);

		Label description = new Label(meeting.getDescription().toString());
		description.setCaption("Beschreibung");
		description.setIcon(FontAwesome.BOOK);

		Label time = new Label(meeting.getTime().toString());
		time.setCaption("Uhrzeit");
		time.setIcon(FontAwesome.CLOCK_O);

		Label location = new Label(meeting.getLocation().getCity());
		location.setCaption("Veranstaltungsort");
		location.setIcon(FontAwesome.LOCATION_ARROW);

		Label members = new Label(meeting.getMembers().size() + " / " + meeting.getMaxMembers());
		members.setCaption("Teilnehmeranzahl");
		members.setIcon(FontAwesome.USER_PLUS);

		inputLayout.addComponents(date, category, title, description, time, location, members);

		// --------------------- ButtonLayout ----------------------
		HorizontalLayout button1Layout = new HorizontalLayout();
		button1Layout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		button1Layout.setSizeFull();
		if (meeting.getCreator().getEmail().equals(registratedMember.getEmail())) {
			Button button1 = new Button();
			button1.setCaption("Bearbeiten");
			button1.addClickListener(e -> {
				getUI().getNavigator().navigateTo(ViewName.CREATE.toString() + "/" + meeting.getMeetingID());
				close();
			});
			button1Layout.addComponent(button1);
		} 


		HorizontalLayout cancelLayout = new HorizontalLayout();
		cancelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		cancelLayout.setSizeFull();
		Button cancel = new Button("Schließen");
		cancel.addClickListener(e -> {
			close();
		});
		cancelLayout.addComponent(cancel);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		buttonLayout.setWidth(80, Unit.PERCENTAGE);
		buttonLayout.addComponents(button1Layout, cancelLayout);

		// -------------------------- MainLayout -------------------------------

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		mainLayout.setSizeUndefined();
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.addComponents(inputLayout, buttonLayout);
		this.setContent(mainLayout);

		center();
		this.setWidth(25, Unit.PERCENTAGE);
		this.setHeight(70, Unit.PERCENTAGE);
	}

}
