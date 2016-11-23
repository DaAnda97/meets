package de.meets.assets;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "meeting")
public class Meeting {

	@Id @Column(name = "meetingID")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetingCategory", nullable = false)
	private Category meetingCategory;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "meetingDate")
	private Date meetingDate;
	
	@Column(name = "meetingTime")
	private Time meetingTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetingLocation", nullable = true)
	private Location meetingLocation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meetingOwner", nullable = false)
	private Member meetingOwner;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "meetings")
	private List<Member> members;
	
	@Column(name = "maxMembers")
	private int maxMembers;
	
	@Column(name = "createdTime")
	private Timestamp createdTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdLocation", nullable = false)
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
