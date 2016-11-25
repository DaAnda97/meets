package de.meets.assets;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
	
	// category table
	public static final String CATEGORY_TABLE = "category";
	public static final String CATEGORY_ID = "categoryID";
	public static final String CATEGORY_NAME = "name";
	
	// define category fields/columns	
	@Id @Column(name = CATEGORY_ID)
	private int id;
	
	@Column(name = CATEGORY_NAME)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingCategory")
	private List<Meeting> meetings;
	
	// constructors
	public Category() {}
	
	public Category(int id) {
		this.id = id;
	}
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
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
	
	public List<Meeting> getMeetings() {
		return this.meetings;
	}
	
	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	
}
