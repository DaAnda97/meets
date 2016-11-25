package de.meets.assets;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {

	// location table
	public static final String LOCATION_TABLE = "Location";
	public static final String LOCATION_ID = "locationID";
	public static final String LOCATION_POSTAL_CODE = "postalCode";
	public static final String LOCATION_NAME = "name";
	
	// define location fields/columns
	@Id @Column(name = LOCATION_ID)
	private int id;
	
	@Column(name = LOCATION_POSTAL_CODE)
	private int postalCode;
	
	@Column(name = LOCATION_NAME)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = Member.MEMBER_LOCATION)
	private Set<Member> memberLocations = new HashSet<Member>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = Meeting.MEETING_LOCATION)
	private Set<Meeting> meetingLocations =  new HashSet<Meeting>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = Meeting.MEETING_CREATED_LOCATION)
	private Set<Meeting> meetingLocationsCreated =  new HashSet<Meeting>();
	
	// constructors
	public Location() {}
	
	public Location(int id) {
		this.id = id;
	}
	
	public Location(int id, int postalCode, String name) {
		this.id = id;
		this.postalCode = postalCode;
		this.name = name;
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Member> getMemberLocations() {
		return memberLocations;
	}

	public void setMemberLocations(Set<Member> memberLocations) {
		this.memberLocations = memberLocations;
	}

	public Set<Meeting> getMeetingLocations() {
		return meetingLocations;
	}

	public void setMeetingLocations(Set<Meeting> meetingLocations) {
		this.meetingLocations = meetingLocations;
	}

	public Set<Meeting> getMeetingLocationsCreated() {
		return meetingLocationsCreated;
	}

	public void setMeetingLocationsCreated(Set<Meeting> meetingLocationsCreated) {
		this.meetingLocationsCreated = meetingLocationsCreated;
	}

}
