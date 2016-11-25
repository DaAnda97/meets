package de.meets.assets;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {

	// member table
	public static final String MEMBER_TABLE = "Member";
	public static final String MEMBER_ID = "memberID";
	public static final String MEMBER_USERNAME = "username";
	public static final String MEMBER_FIRST_NAME = "firstName";
	public static final String MEMBER_LAST_NAME = "lastName";
	public static final String MEMBER_PASSWORD = "password";
	public static final String MEMBER_EMAIL = "email";
	public static final String MEMBER_LOCATION = "memberLocation";
	public static final String MEMBER_CREATED = "created";
	
	// define member fields/columns
	@Id @Column(name = MEMBER_ID)
	private int id;
	
	@Column(name = MEMBER_USERNAME)
	private String username;
	
	@Column(name = MEMBER_FIRST_NAME)
	private String firstName;
	
	@Column(name = MEMBER_LAST_NAME)
	private String lastName;
	
	@Column(name = MEMBER_PASSWORD)
	private String password;
	
	@Column(name = MEMBER_EMAIL)
	private String email;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinTable(name="meet", 
				joinColumns={@JoinColumn(name=Member.MEMBER_ID)}, 
				inverseJoinColumns={@JoinColumn(name=Meeting.MEETING_ID)})
	private List<Meeting> meetings;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MEMBER_LOCATION, nullable = false)
	private Location memberLocation;
	
	@Column(name = MEMBER_CREATED)
	private Timestamp created;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = Meeting.MEETING_OWNER)
	private List<Meeting> ownMeetings;
	
	// constructors
	public Member() {}
	
	public Member(int id) {
		this.id = id;
	}
	
	public Member(int id, String username, String firstName, String lastName,
			String password, String email, Location memberLocation,
			Timestamp created, List<Meeting> ownMeetings) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.memberLocation = memberLocation;
		this.created = created;
		this.ownMeetings = ownMeetings;
	}
	
	// getters and setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Location getMemberLocation() {
		return memberLocation;
	}
	
	public void setMemberLocation(Location memberLocation) {
		this.memberLocation = memberLocation;
	}
	
	public Timestamp getCreated() {
		return created;
	}
	
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	public List<Meeting> getOwnMeetings() {
		return ownMeetings;
	}
	
	public void setOwnMeetings(List<Meeting> ownMeetings) {
		this.ownMeetings = ownMeetings;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	
}
