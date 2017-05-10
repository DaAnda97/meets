package de.meets.assets;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Member")
public class Member {
	
	// member table
	public static final String MEMBER_TABLE = "Member";
	public static final String MEMBER_ID = "memberID";
	public static final String MEMBER_USERNAME = "username";
	public static final String MEMBER_FIRST_NAME = "firstName";
	public static final String MEMBER_LAST_NAME = "lastName";
	public static final String MEMBER_PASSWORD = "password";
	public static final String MEMBER_EMAIL = "email";
	public static final String MEMBER_LOCATION = "position";
	public static final String MEMBER_CREATED = "created";
	
	// define member fields/columns
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = MEMBER_ID)
	private int memberID;
	
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
	
	@Column(name = MEMBER_CREATED)
	private Timestamp created;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MEMBER_LOCATION, nullable = false)
	private Location position;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = Meeting.MEETING_OWNER, orphanRemoval = true)
	private Set<Meeting> ownedMeetings = new HashSet<Meeting>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "Meet", 
		joinColumns = { @JoinColumn(name = Member.MEMBER_ID, nullable = false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = Meeting.MEETING_ID, nullable = false, updatable = false) })
	private Set<Meeting> joinedMeetings = new HashSet<Meeting>();
	
	// constructors
	public Member() {}
	
	public Member(int id) {
		this.memberID = id;
	}

	public Member(String username, String firstName, String lastName,
			String password, String email, Location position) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.position = position;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(4);
		s.append("ID: ");
		s.append(this.memberID);
		s.append(", Username: ");
		s.append(this.username);
		return s.toString(); 
	}
	
	// getters and setters
	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		if (firstName == null){
			return "";
		} else {
			return firstName;
		}
	}

	public void setFirstName(String firstName) {
		if (!firstName.equals("")){
			this.firstName = firstName;
		}
	}

	public String getLastName() {
		if (lastName == null){
			return "";
		} else {
			return lastName;
		}
	}

	public void setLastName(String lastName) {
		if (!lastName.equals("")){
			this.lastName = lastName;
		}
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

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Location getPosition() {
		return position;
	}

	public void setPosition(Location position) {
		this.position = position;
	}

	public Set<Meeting> getOwnedMeetings() {
		return ownedMeetings;
	}

	public void setOwnedMeetings(Set<Meeting> ownedMeetings) {
		this.ownedMeetings = ownedMeetings;
	}

	public Set<Meeting> getJoinedMeetings() {
		return joinedMeetings;
	}

	public void setJoinedMeetings(Set<Meeting> joinedMeetings) {
		this.joinedMeetings = joinedMeetings;
	}

	
}
