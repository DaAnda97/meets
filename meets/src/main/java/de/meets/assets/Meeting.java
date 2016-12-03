package de.meets.assets;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Meeting")
public class Meeting {

	// meeting table
	public static final String MEETING_TABLE = "meeting";
	public static final String MEETING_ID = "meetingID";
	public static final String MEETING_NAME = "title";
	public static final String MEETING_DESCRIPTION = "description";
	public static final String MEETING_CATEGORY = "category";
	public static final String MEETING_DATE = "date";
	public static final String MEETING_TIME = "time";
	public static final String MEETING_LOCATION = "location";
	public static final String MEETING_OWNER = "creator";
	public static final String MEETING_MAX_MEMBERS = "scope";
	public static final String METADATA_CREATED_TIME = "createdTime";
	public static final String METADATA_CREATED_LOCATION = "createdLocation";
	
	// define meeting fields/columns
	@Id @Column(name = MEETING_ID)
	private int meetingID;
	
	@Column(name = MEETING_NAME)
	private String title;
	
	@Column(name = MEETING_DESCRIPTION)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MEETING_CATEGORY, nullable = false)
	private Category category;
	
	@Temporal(TemporalType.DATE)
	@Column(name = MEETING_DATE)
	private Date date;
	
	@Column(name = MEETING_TIME)
	private Time time;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MEETING_LOCATION, nullable = true)
	private Location location;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = MEETING_OWNER, nullable = false)
	private Member creator;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "joinedMeetings")
	private List<Member> members;
	
	@Column(name = MEETING_MAX_MEMBERS)
	private int scope;
	
	@Column(name = METADATA_CREATED_TIME)
	private Timestamp createdTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = METADATA_CREATED_LOCATION, nullable = false)
	private Location createdLocation;

	// constructors
	public Meeting() {}
	
	public Meeting(int id) {
		this.meetingID = id;
	}

	public Meeting(String title, String description, Category category,
			Date date, Time time, Location location, Member creator, int scope,
			Location createdLocation) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.date = date;
		this.time = time;
		this.location = location;
		this.creator = creator;
		this.scope = scope;
		this.createdLocation = createdLocation;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(4);
		s.append("ID: ");
		s.append(this.meetingID);
		s.append(", Title: ");
		s.append(this.title);
		return s.toString(); 
	}
	
	// getters and setters
	public int getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(int meetingID) {
		this.meetingID = meetingID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member creator) {
		this.creator = creator;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Location getCreatedLocation() {
		return createdLocation;
	}

	public void setCreatedLocation(Location createdLocation) {
		this.createdLocation = createdLocation;
	}
	

}
