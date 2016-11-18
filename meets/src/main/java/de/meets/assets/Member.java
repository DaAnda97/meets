package de.meets.assets;

public class Member {

	// member fields
	private int memberID;
	private String username;
	private String prename;
	private String surname;
	private String password;
	private String mail;
	
	// constructor
	public Member(int memberID, String username, String prename, 
			String surname, String password, String mail) {
		this.memberID = memberID;
		this.username = username;
		this.prename = prename;
		this.surname = surname;
		this.password = password;
		this.mail = mail;
	}

	// getters
	public int getMemberID() {
		return memberID;
	}

	public String getUsername() {
		return username;
	}

	public String getPrename() {
		return prename;
	}

	public String getSurname() {
		return surname;
	}
	
	public String getName() {
		return this.prename +" " +this.surname;
	}

	public String getPassword() {
		return password;
	}

	public String getMail() {
		return mail;
	}

	// setters
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
}
