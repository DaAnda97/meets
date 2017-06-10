package de.meets.gui.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Meeting;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.gui.extendedComponents.MeetingComponent;
import de.meets.vaadin_archetype_application.MeetsUI;

// Alle entsprechenden Meets anzeigen 
public class MeetingOverview extends MeetsView {

	private static final long serialVersionUID = 7973265153857834807L;
	private static final int MEETINGS_ON_PAGE = 5;

	VerticalLayout mainLayout = new VerticalLayout();
	VerticalLayout meetingsLayout = new VerticalLayout();

	public MeetingOverview(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);

		//------------------------MENU------------------------
		HorizontalLayout createLayout = new HorizontalLayout();
		createLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		createLayout.setSizeFull();
		Button bCreate = new Button("Create");
		bCreate.addClickListener(e ->  {
			getUI().getNavigator().navigateTo(ViewName.CREATE.toString());
		});
		createLayout.addComponent(bCreate);
		
		HorizontalLayout sortLayout = new HorizontalLayout();
		sortLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		sortLayout.setSizeFull();
		Button bSort = new Button("Create");
		bSort.addClickListener(e ->  {
			//TODO
		});
		sortLayout.addComponent(bSort);
		
		HorizontalLayout menuLayout = new HorizontalLayout();
		menuLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		menuLayout.setSizeFull();
		menuLayout.addComponents(createLayout, sortLayout);
		
		//------------------------MAIN------------------------
		meetingsLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		meetingsLayout.setSizeFull();
		meetingsLayout.setMargin(true);
		meetingsLayout.setSpacing(true);
		
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.addComponents(menuLayout, meetingsLayout);
		setSizeFull();
		setCompositionRoot(mainLayout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		List<Meeting> meetings = getMeetings();

		for (Meeting meeting : meetings) {
			meetingsLayout.addComponent(new MeetingComponent(meeting));
		}
	}

	private List<Meeting> getMeetings() {
		List<Meeting> meetings = new ArrayList<Meeting>();
		Iterator<Meeting> it = getMeetingManager().get(0, 10);
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
