package de.meets.assets;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {

	@Id @Column(name = "memberID")
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="meet", 
				joinColumns={@JoinColumn(name="memberID")}, 
				inverseJoinColumns={@JoinColumn(name="meetingID")})
	private List<Meeting> meetings;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberLocation", nullable = false)
	private Location memberLocation;
	
	@Column(name = "created")
	private Timestamp created;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingOwner")
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
	
}
