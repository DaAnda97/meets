package de.meets.hibernate;

import java.sql.Time;
import java.util.Date;

import de.meets.asset_manager.CategoryManager;
import de.meets.asset_manager.LocationManager;
import de.meets.asset_manager.MeetingManager;
import de.meets.asset_manager.MemberManager;
import de.meets.assets.Category;
import de.meets.assets.Location;
import de.meets.assets.Meeting;
import de.meets.assets.Member;

public class ManagerTemplate {

	public static void main(String[] args) {
		
		DatabaseConnector db = new DatabaseConnector();
		
		MemberManager memb = new MemberManager(db);
		MeetingManager meet = new MeetingManager(db);
		LocationManager loca = new LocationManager(db);
		CategoryManager cate = new CategoryManager(db);
		
		// Add categories
		cate.add(new Category("Indoor"));
		cate.add(new Category("Gesellschaft"));
		
		// Add locations
		loca.add(new Location("Berlin", 12.4, 13.888));
		loca.add(new Location("Paris", 123.55, 123.55));
		
		// Add members
		memb.add(new Member("Gla", "Peter", "Acker", "superSave", "pnr.acker@web.de", 
				new Location(1)));
		memb.add(new Member("Umntate", "Bernd", "Heck", "1234321", "bnd01@web.de", 
				new Location(2)));
		memb.add(new Member("andi", "Andi", "Test", "1234", "andi@gmx.de", 
				new Location(2)));
		
		// Add meetings
		meet.add(new Meeting("Essen", "Wer hat bock auf Oxfort?", new Category(1), new Date(), 
				new Time(System.currentTimeMillis()), new Location(1), new Member(1), 5, new Location(1)));
		meet.add(new Meeting("Weihnachtsmarkt", "In der Weihnachtsbäckerei gibt es manche Leckerei - Weihnachtsmark?!", 
				new Category(2), new Date(), new Time(System.currentTimeMillis()), new Location(1), new Member(2), 4, new Location(1)));
		
		/* Delete meeting
		 * Note: Before you have to checkout are participants
		 * 
		 * 	1# 	meeting.getMembers().clear();
		 * 	2# 	meetingManager.update(meeting);
		 * 	3# 	meeting = meetingManager.get(ID);
		 * 
		 * 	4# 	meetingManager.delete(meeting);
		 */
		meet.delete(  meet.get(1) );
		
		// Update member
		Member m = memb.get(1);
		m.setFirstName("Herold");
		memb.update(m);
		
		m = memb.get(1);
		
		// Joining meetings
		m.getJoinedMeetings().add( meet.get(2) );
		memb.update(m);
		
		// For a clear website shutdown 
		// --> shut down database connection
		db.tearDown();
	}

}
