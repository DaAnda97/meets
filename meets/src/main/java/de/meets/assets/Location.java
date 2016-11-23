package de.meets.assets;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {

	@Id @Column(name = "locationID")
	private int id;
	
	@Column(name = "postalCode")
	private int postalCode;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "memberLocation")
	private List<Member> memberLocations;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingLocation")
	private List<Meeting> meetingLocations;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "createdLocation")
	private List<Meeting> createdLocations;
	
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
	
	public List<Member> getMemberLocations() {
		return this.memberLocations;
	}
	
	public void setMemberLocations(List<Member> memberLocations) {
		this.memberLocations = memberLocations;
	}
	
	public List<Meeting> getMeetingLocations() {
		return this.meetingLocations;
	}
	
	public void setMeetingLocations(List<Meeting> meetingLocations) {
		this.meetingLocations = meetingLocations;
	}
	
	public List<Meeting> getMeetingCreateds() {
		return this.createdLocations;
	}
	
	public void setMeetingCreateds(List<Meeting> meetingCreated) {
		this.createdLocations = meetingCreated;
	}

}
