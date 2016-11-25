package de.meets.assets;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "meeting")
public class Meeting {

	// meeting table
	public static final String MEETING_TABLE = "meeting";
	public static final String MEETING_ID = "meetingID";
	public static final String MEETING_NAME = "name";
	public static final String MEETING_DESCRIPTION = "description";
	public static final String MEETING_CATEGORY = "meetingCategory";
	public static final String MEETING_DATE = "meetingDate";
	public static final String MEETING_TIME = "meetingTime";
	public static final String MEETING_LOCATION = "meetingLocation";
	public static final String MEETING_OWNER = "meetingOwner";
	public static final String MEETING_MAX_MEMBERS = "maxMembers";
	public static final String MEETING_CREATED_TIME = "createdTime";
	public static final String MEETING_CREATED_LOCATION = "createdLocation";
	
	// define meeting fields/columns
	@Id @Column(name = MEETING_ID)
	private int id;
	
	@Column(name = MEETING_NAME)
	private String name;
	
	@Column(name = MEETING_DESCRIPTION)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MEETING_CATEGORY, nullable = false)
	private Category meetingCategory;
	
	@Temporal(TemporalType.DATE)
	@Column(name = MEETING_DATE)
	private Date meetingDate;
	
	@Column(name = MEETING_TIME)
	private Time meetingTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MEETING_LOCATION, nullable = true)
	private Location meetingLocation;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = MEETING_OWNER, nullable = false)
	private Member meetingOwner;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "meetings")
	private List<Member> members;
	
	@Column(name = MEETING_MAX_MEMBERS)
	private int maxMembers;
	
	@Column(name = MEETING_CREATED_TIME)
	private Timestamp createdTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MEETING_CREATED_LOCATION, nullable = false)
	private Location createdLocation;

	// constructors
	public Meeting() {}
	
	public Meeting(int id) {
		this.id = id;
	}
	public Meeting(int id, String name, String description, Category category,
			Date meetingDate, Time meetingTime, Location meetingLocation,
			Member meetingOwner, List<Member> members, int maxMembers, 
			Timestamp createdTime, Location createdLocation) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.meetingCategory = category;
		this.meetingDate = meetingDate;
		this.meetingTime = meetingTime;
		this.meetingLocation = meetingLocation;
		this.meetingOwner = meetingOwner;
		this.members = members;
		this.maxMembers = maxMembers;
		this.createdTime = createdTime;
		this.createdLocation = createdLocation;
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return meetingCategory;
	}

	public void setCategory(Category category) {
		this.meetingCategory = category;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public Time getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(Time meetingTime) {
		this.meetingTime = meetingTime;
	}

	public Location getMeetingLocation() {
		return meetingLocation;
	}

	public void setMeetingLocation(Location meetingLocation) {
		this.meetingLocation = meetingLocation;
	}

	public Member getMeetingOwner() {
		return meetingOwner;
	}

	public void setMeetingOwner(Member meetingOwner) {
		this.meetingOwner = meetingOwner;
	}

	public List<Member> getMembers() {
		return this.members;
	}
	
	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public int getMaxMembers() {
		return maxMembers;
	}

	public void setMaxMembers(int maxMembers) {
		this.maxMembers = maxMembers;
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
