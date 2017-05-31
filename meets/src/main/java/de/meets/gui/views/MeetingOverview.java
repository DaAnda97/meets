package de.meets.gui.views;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Category;
import de.meets.assets.Location;
import de.meets.assets.Meeting;
import de.meets.assets.Member;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.gui.extendedComponents.MeetingComponent;
import de.meets.vaadin_archetype_application.MeetsUI;

// Alle entsprechenden Meets anzeigen 
public class MeetingOverview extends MeetsView {
	
	private static final long serialVersionUID = 7973265153857834807L;
	private static final int MEETINGS_ON_PAGE = 5;
	private List<Meeting> meetings;
	
	VerticalLayout mainLayout;

	public MeetingOverview(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);
		
		mainLayout = new VerticalLayout();
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		
//		for(int i = 0; i < 10; i++){
//			mainLayout.addComponent(new Label(""));
//			mainLayout.addComponent(new Label("HIER ERSCHEINEN SPÄTER DIE PASSENDEN MEETS"));
//		}
		
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		setCompositionRoot(mainLayout);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {		
//		meetings = getMeetings();
		
		meetings = new ArrayList<Meeting>();
		/*
		for (int i = 0; i < 3; i++){
			Meeting testMeeting = createTestMeeting();
			meetings.add(testMeeting);
		}
		*/
		meetings = getMeetings();
		for (Meeting meeting : meetings){
			mainLayout.addComponent(new MeetingComponent(meeting));
		}
	}
	
	private List<Meeting> getMeetings(){
		List<Meeting> meetings = new ArrayList<Meeting>();
		Iterator<Meeting> it = getMeetingManager().get(0, 10);
		while (it.hasNext()) {
			Meeting meeting = (Meeting) it.next();
			meetings.add(meeting);
		}
		return meetings;
	}
	
	private Meeting createTestMeeting(){
		Category category = new Category(1);
		Date date = new Date(2017, 6, 14);
		Time time = new Time(System.currentTimeMillis());
		Location location = new Location(1);
		Member member = super.getRegistratedMember();
		Meeting testMeeting = new Meeting("Test Meeting", "Descriotion of test meeting", category, date, time, location, member, 4, location);
		Meeting testMeeting2 = new Meeting("Weihnachtsmarkt", "In der Weihnachtsbäckerei", new Category(2), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(2), 4, new Location(1));
		
		return testMeeting2;
	}

}
