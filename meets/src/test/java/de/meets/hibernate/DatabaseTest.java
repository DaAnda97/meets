package de.meets.hibernate;

import static org.junit.Assert.*;

import java.sql.Time;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

import de.meets.asset_manager.CategoryManager;
import de.meets.asset_manager.LocationManager;
import de.meets.asset_manager.MeetingManager;
import de.meets.asset_manager.MemberManager;
import de.meets.assets.Category;
import de.meets.assets.Location;
import de.meets.assets.Meeting;
import de.meets.assets.Member;

public class DatabaseTest {
	
	private DatabaseConnector db;
	private MemberManager memberMng;
	private MeetingManager meetMng;
	private LocationManager locMng;
	private CategoryManager catMng;
	
	public void setUp() {
		
		this.db = new DatabaseConnector();
		
		memberMng = new MemberManager(db);
		meetMng = new MeetingManager(db);
		locMng = new LocationManager(db);
		catMng = new CategoryManager(db);
	}
	
	public void test() {
		setUp();
		
		testCategory(catMng);
		testLocation(locMng);
		testMember(memberMng);
		testMeeting(meetMng);
		
		testMeetingInteract(meetMng, memberMng);
		
		Iterator<Member> itmem = memberMng.getAll();
		while (itmem.hasNext()) {
			Member member = (Member) itmem.next();
			memberMng.delete(member);
		}
		
		Iterator<Meeting> itmet = meetMng.getAll();
		while (itmet.hasNext()) {
			Meeting meeting = (Meeting) itmet.next();
			meetMng.delete(meeting);
		}
		
		db.tearDown();		
	}
	
	private void testMeetingInteract(MeetingManager meetMng, MemberManager memberMng) {
		Member member_1 = memberMng.get(1);
		
		member_1.getJoinedMeetings().add( meetMng.get(1) );
		member_1.getJoinedMeetings().add( meetMng.get(2) );
		
		memberMng.update(member_1);
		
		member_1 = memberMng.get(1);
		Iterator<Meeting> joinedMeeting_1 = member_1.getJoinedMeetings().iterator();
		while (joinedMeeting_1.hasNext()) {
			Meeting meeting = (Meeting) joinedMeeting_1.next();
			assertEquals("Weihnachtsmarkt", meeting.getTitle());
		}
	}
	
	private void testMeeting(MeetingManager meetMng) {
		meetMng.add(new Meeting("Weihnachtsmarkt", "Wer hat bock auf Oxfort?", new Category(1), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(1), 5, new Location(1)));
		meetMng.add(new Meeting("Weihnachtsmarkt", "In der Weihnachtsbäckerei", new Category(2), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(2), 4, new Location(1)));
		meetMng.add(new Meeting("temp", "temp", new Category(2), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(2), 4, new Location(1)));
		
		Meeting meet_1 = meetMng.get(3);
		assertEquals("temp", meet_1.getTitle());
		
		meet_1.setTitle("tempNEW");
		meetMng.update(meet_1);
		meet_1 = meetMng.get(3);
		assertEquals("tempNEW", meet_1.getTitle());
		
		meetMng.delete(meet_1);
	}

	private void testMember(MemberManager memberMng) {
		memberMng.add(new Member("Herold15Azvvg555", "Peter", "Acker", "superSave", "pndd5fsr.acker@web.de", new Location(1)));
		memberMng.add(new Member("Herold15Azvvgjshkd", "Bernd", "Heck", "1234321", "bndd4f5d01@web.de", new Location(2)));
		memberMng.add(new Member("Herold15Azvvg", "Andi", "Test", "test", "test@test", new Location(2)));
		
		Member member_1 = memberMng.get(3);
		assertEquals("herold15azvvg", member_1.getUsername());
		
		member_1.setUsername("herold15azvvgnew");
		memberMng.update(member_1);
		member_1 = memberMng.get(3);
		assertEquals("herold15azvvgnew", member_1.getUsername());
		
		memberMng.delete(member_1);
	}

	private void testLocation(LocationManager locMng) {
		locMng.add(new Location("Berlin", 12.4, 13.888));
		locMng.add(new Location("Paris", 123.55, 123.55));
		locMng.add(new Location("Test", 153.5, 103.55));
		
		Location loc_1 = locMng.get(3);
		assertEquals("Test", loc_1.getCity());
		
		loc_1.setCity("TestNew");
		locMng.update(loc_1);
		loc_1 = locMng.get(3);
		assertEquals("TestNew", loc_1.getCity());
				
		locMng.delete(new Location(3));
	}
	
	private void testCategory(CategoryManager catMng) {
		catMng.add(new Category("Indoor"));
		catMng.add(new Category("Outdoor"));
		catMng.add(new Category("Nightlife"));
		
		Category cat_1 = catMng.get(3);
		assertEquals("Nightlife", cat_1.getTitle());
		
		cat_1.setTitle("Daylife");
		catMng.update(cat_1);
		cat_1 = catMng.get(3);
		assertEquals("Daylife", cat_1.getTitle());
				
		catMng.delete(new Category(3));
	}

}
