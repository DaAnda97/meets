package de.meets.hibernate;

import java.sql.Time;

import java.util.Date;
import java.util.Iterator;

import de.meets.asset_manager.CategoryManager;
import de.meets.asset_manager.LocationManager;
import de.meets.asset_manager.MeetingManager;
import de.meets.asset_manager.MemberManager;
import de.meets.assets.Category;
import de.meets.assets.Location;
import de.meets.assets.Meeting;
import de.meets.assets.Member;

public class DatabaseTest {

	public static void databaseTest() {
		
		DatabaseConnector db = new DatabaseConnector();
		
		MemberManager memb = new MemberManager(db);
		MeetingManager meet = new MeetingManager(db);
		LocationManager loca = new LocationManager(db);
		CategoryManager cate = new CategoryManager(db);
		
		// Add categories
		System.out.println("\n--- BEGIN ADD Categories ---\n");
		
		cate.add(new Category("Indoor"));
		cate.add(new Category("Gesellschaft"));
		
		System.out.println("\n--- END Categories ---");	
		
		// Add locations
		System.out.println("\n--- BEGIN ADD Locations ---\n");
		
		loca.add(new Location("Berlin", 12.4, 13.888));
		loca.add(new Location("Paris", 123.55, 123.55));
		
		System.out.println("\n--- END Locations ---");
		
		// Add members
		System.out.println("\n--- BEGIN ADD Members ---\n");
		
		memb.add(new Member("Gla", "Peter", "Acker", "superSave", "pnr.acker@web.de", 
				new Location(1)));
		memb.add(new Member("Umntate", "Bernd", "Heck", "1234321", "bnd01@web.de", 
				new Location(2)));
		memb.add(new Member("andi", "Andi", "Test", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4", "andi@gmx.de", 
				new Location(2)));
		
		System.out.println("\n--- END Members ---");
		
		// Add meeting
		System.out.println("\n--- BEGIN ADD Meetings ---\n");
		
		Meeting meeting_1 = new Meeting("Essen", "Wer hat bock auf Oxfort?", new Category(1), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(1), 5, new Location(1));
		Meeting meeting_2 = new Meeting("Weihnachtsmarkt", "In der Weihnachtsbäckerei", new Category(2), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(2), 4, new Location(1));
		
		meet.add(meeting_1);
		meet.add(meeting_2);
		
		System.out.println("\n--- END Meetings ---");
		
		// Update member
		System.out.println("\n--- BEGIN UPDATE Member ---\n");
		
		Member member_1 = memb.get(1);
		System.out.println(member_1);
		member_1.setUsername("Herold");
		memb.update(member_1);
		System.out.println(memb.get(1));
		
		System.out.println("\n--- END Member ---");
		
		// Joining meetings
		System.out.println("\n--- BEGIN JOIN Meeting ---\n");
		
		member_1.getJoinedMeetings().add( meet.get(1) );
		member_1.getJoinedMeetings().add( meet.get(2) );
		memb.update(member_1);
				
		Member member_2 = memb.get(2);
		member_2.getJoinedMeetings().add( meet.get(1) );
		memb.update(member_2);
		
		System.out.println("\n--- END Meeting ---");
		
		// Delete meeting
		System.out.println("\n--- BEGIN DELETE Meeting ---\n");
		
		meeting_2 = meet.get(2);
		meet.delete(meeting_2);
		
		System.out.println("\n--- END Meeting ---");
		
		// Delete member
		System.out.println("\n--- BEGIN DELETE Member ---\n");
		
		member_1 = memb.get(1);
		member_2 = memb.get(2);
		memb.delete(member_1);
		memb.delete(member_2);
		
		System.out.println("\n--- END Member ---");
		
		// Get meetings
		System.out.println("\n--- GET Meetings ---\n");
		
		Meeting meeting_3 = new Meeting("Essen", "Wer hat bock auf Oxfort?", new Category(1), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(3), 5, new Location(1));
		Meeting meeting_4 = new Meeting("Weihnachtsmarkt", "In der Weihnachtsbäckerei", new Category(2), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(3), 4, new Location(1));
		Meeting meeting_5 = new Meeting("Weihnachtsmarkt", "In der Weihnachtsbäckerei", new Category(2), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(3), 4, new Location(1));
		
		meet.add(meeting_3);
		meet.add(meeting_4);
		meet.add(meeting_5);
		
		Iterator<Meeting> meetings = meet.get(0, 3);
		while (meetings.hasNext()) {
			Meeting meeting = (Meeting) meetings.next();
			System.out.println(meeting);
		}
		
		System.out.println("\n--- END Meetings ---");
		
		// Shut down database connection
		db.tearDown();
	}

	
}
