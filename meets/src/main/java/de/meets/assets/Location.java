package de.meets.assets;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Location")
public class Location {

	// location table
	public static final String LOCATION_TABLE = "Location";
	public static final String LOCATION_ID = "locationID";
	public static final String LOCATION_CITY = "city";
	public static final String LOCATION_LONGITUDE = "longitude";
	public static final String LOCATION_LATITUDE = "latitude";
	
	// define location fields/columns
	@Id @Column(name = LOCATION_ID)
	private int locationID;
	
	@Column(name = LOCATION_CITY)
	private String city;
	
	@Column(name = LOCATION_LONGITUDE)
	private double longitude;
	
	@Column(name = LOCATION_LATITUDE)
	private double latitude;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = Member.MEMBER_LOCATION)
	private Set<Member> memberLocations = new HashSet<Member>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = Meeting.MEETING_LOCATION)
	private Set<Meeting> meetingLocations =  new HashSet<Meeting>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = Meeting.METADATA_CREATED_LOCATION)
	private Set<Meeting> meetingLocationsCreated =  new HashSet<Meeting>();
	
	// constructors
	public Location() {}
	
	public Location(int id) {
		this.locationID = id;
	}
		
	public Location(String city, double longitude, double latitude) {
		this.city = city;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	// getters and setters
	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
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
