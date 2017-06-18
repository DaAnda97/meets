package de.meets.gui.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Meeting;
import de.meets.assets.Member;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.gui.extendedComponents.MeetingComponent;
import de.meets.gui.extendedComponents.SafeButton;
import de.meets.vaadin_archetype_application.MeetsUI;

// Alle entsprechenden Meets anzeigen 
public class MeetingOverview extends MeetsView {

	private static final long serialVersionUID = 7973265153857834807L;
	private static final int MEETINGS_ON_PAGE = 5;

	public MeetingOverview(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);
	}

	@Override
	public void enter(ViewChangeEvent enterEvent) {
		// ------------------------MENU------------------------
		HorizontalLayout createLayout = new HorizontalLayout();
		createLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		createLayout.setSizeFull();
		Button bCreate = new Button("Erstellen");
		bCreate.addClickListener(e -> {
			getUI().getNavigator().navigateTo(ViewName.CREATE.toString());
		});
		createLayout.addComponent(bCreate);

		HorizontalLayout sortLayout = new HorizontalLayout();
		sortLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		sortLayout.setSizeFull();
		NativeSelect nativeSelcet = new NativeSelect("Sortieren:");
		nativeSelcet.addItems("Alle","Meine Umgebung", "Meine Meetings");
		// Handle selection event
		nativeSelcet.addValueChangeListener(new ValueChangeListener() {

	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public void valueChange(ValueChangeEvent event) {
				try {
					if (event.getProperty().getValue().equals("Alle")){
						
					} else if (event.getProperty().getValue().equals("Meine Umgebung")){
						
					} else if (event.getProperty().getValue().equals("Meine Meetings")){
						
					}
				} catch (NullPointerException e) {
					
				}
				 
	        }

	    });
		sortLayout.addComponent(nativeSelcet);

		HorizontalLayout menuLayout = new HorizontalLayout();
		menuLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		menuLayout.setWidth(80, Unit.PERCENTAGE);
		menuLayout.addComponents(createLayout, sortLayout);

		// ------------------------MAIN------------------------
		VerticalLayout meetingsLayout = new VerticalLayout();
		meetingsLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		meetingsLayout.setSizeFull();
		meetingsLayout.setMargin(true);
		meetingsLayout.setSpacing(true);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.addComponents(menuLayout, meetingsLayout);
		setSizeFull();
		setCompositionRoot(mainLayout);

		List<Meeting> meetings = getMeetings();

		for (Meeting meeting : meetings) {
			HorizontalLayout joinLeaveLayout = new HorizontalLayout();
			joinLeaveLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
			joinLeaveLayout.setSizeFull();
			boolean isCreator = (meeting.getCreator().equals(getRegistratedMember()));
			if (meeting.getMembers().stream().anyMatch(a -> a.equals(getRegistratedMember()))) {
				SafeButton bLeave = new SafeButton("Austreten",
						(isCreator ? "Bist du dir sicher, dass du aus deinem Meeting " + meeting.getTitle()
								+ " austreten möchtest? Das Meeting wird dabei gelöscht."
								: "Bist du sicher, dass du aus dem Meeting " + meeting.getTitle()
										+ " austreten möchtest?"),
						new ClickListener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void buttonClick(ClickEvent event) {
								if (isCreator) {
									getMeetingManager().delete(meeting);
									Notification.show("Meeting gelöscht!",
											"Dein Meeting " + meeting.getTitle() + " wurde gelöscht!",
											Type.TRAY_NOTIFICATION);
								} else {
									HashSet<Member> members = (HashSet<Member>) meeting.getMembers();
									members.remove(getRegistratedMember());
									meeting.setMembers(members);
									getMeetingManager().update(meeting);
									Notification.show("Du nimmst nicht mehr an diesem Meeting teil.",
											Type.TRAY_NOTIFICATION);
								}
								enter(enterEvent);
							}
						});
				joinLeaveLayout.addComponent(bLeave);
			} else {
				Button bJoin = new Button();
				bJoin.setCaption("Beitreten");
				if (meeting.getMembers().size() < meeting.getMaxMembers()) {
					bJoin.setEnabled(true);
				} else {
					bJoin.setEnabled(false);
				}
				bJoin.addClickListener(e -> {
					try {
						meeting.addMember(getRegistratedMember());
					} catch (Exception e1) {
						Notification.show("Meeting voll.", Type.TRAY_NOTIFICATION);
						e1.printStackTrace();
					}
					getMeetingManager().update(meeting);
					Notification.show("Du nimmst an diesem Meeting teil.", Type.TRAY_NOTIFICATION);
				});
				joinLeaveLayout.addComponent(bJoin);

				enter(enterEvent);
			}

			VerticalLayout oneMeetingLayout = new VerticalLayout();
			oneMeetingLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
			oneMeetingLayout.setSizeFull();
			oneMeetingLayout.addComponents(new MeetingComponent(meeting, getRegistratedMember()), joinLeaveLayout);

			meetingsLayout.addComponents(oneMeetingLayout);
		}
	}

	private List<Meeting> getMeetings() {
		List<Meeting> meetings = new ArrayList<Meeting>();
		Iterator<Meeting> it = getMeetingManager().get(0, MEETINGS_ON_PAGE);
		while (it.hasNext()) {
			Meeting meeting = (Meeting) it.next();
			meetings.add(meeting);
		}
		return meetings;
	}

	// private Meeting createTestMeeting(){
	// Category category = new Category(1);
	// Date date = new Date(2017, 6, 14);
	// Time time = new Time(System.currentTimeMillis());
	// Location location = new Location(1);
	// Member member = super.getRegistratedMember();
	// Meeting testMeeting = new Meeting("Test Meeting", "Descriotion of test
	// meeting", category, date, time, location, member, 4, location);
	// Meeting testMeeting2 = new Meeting("Weihnachtsmarkt", "In der
	// Weihnachtsbäckerei", new Category(2), new Date(),
	// new Time(System.currentTimeMillis()), new Location(1), new Member(2), 4,
	// new Location(1));
	//
	// return testMeeting2;
	// }

}
