package de.meets.assets;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Category")
public class Category {
	
	// category table
	public static final String CATEGORY_TABLE = "category";
	public static final String CATEGORY_ID = "categoryID";
	public static final String CATEGORY_TITLE = "title";
	
	// define category fields/columns	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = CATEGORY_ID)
	private int categoryID;
	
	@Column(name = CATEGORY_TITLE)
	private String title;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = Meeting.MEETING_CATEGORY, orphanRemoval = true)
	private Set<Meeting> categorized = new HashSet<Meeting>();
	
	// constructors
	public Category() {}
	
	public Category(int id) {
		this.categoryID = id;
	}
	public Category(String name) {
		this.title = name;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(4);
		s.append("ID: ");
		s.append(this.categoryID);
		s.append(", Title: ");
		s.append(this.title);
		return s.toString(); 
	}

	// getters and setters
	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Meeting> getCategorized() {
		return this.categorized;
	}

	public void setCategorized(Set<Meeting> categorized) {
		this.categorized = categorized;
	}
	
}
