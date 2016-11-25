package de.meets.hibernate;

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
		CategoryManager cate = new CategoryManager();
	//	cate.add(new Category("Indoor"));
	//	cate.add(new Category("Gesellschaft"));
		
		LocationManager loca = new LocationManager();
	//	loca.add(new Location("Berlin", 12.4, 13.888));
	//	loca.add(new Location("Paris", 123.55, 123.55));
		
		MemberManager memb = new MemberManager();
	//	memb.add(new Member("Gola", "Peter", "Acker", "superSave", "peter.acker@web.de", 
	//			new Location(1)));
	//	memb.add(new Member("UpperState", "Bernd", "Heck", "1234321", "bernd01@web.de", 
	//			new Location(2)));
		
		MeetingManager meet = new MeetingManager();
	//	meet.add(new Meeting("Essen", "Wer hat bock auf Oxfort?", new Category(1), new Date(), 
	//			new Time(System.currentTimeMillis()), new Location(1), new Member(1), 5, new Location(1)));
	//	meet.add(new Meeting("Weihnachtsmarkt", "In der Weihnachtsbäckerei gibt es manche Leckerei - Weihnachtsmark?!", 
	//			new Category(2), new Date(), new Time(System.currentTimeMillis()), new Location(1), new Member(2), 4, new Location(1)));
	
		
		
		meet.delete(new Meeting(1));
		meet.delete(new Meeting(1));
		
		memb.delete(new Member(1));
		memb.delete(new Member(2));
		
		cate.delete(new Category(1));
		cate.delete(new Category(2));

		loca.delete(new Location(1));
		loca.delete(new Location(2));
		
		HibernateInit.tearDown();
	}

}
