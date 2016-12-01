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
	@Id @Column(name = CATEGORY_ID)
	private int categoryID;
	
	@Column(name = CATEGORY_TITLE)
	private String title;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = Meeting.MEETING_CATEGORY)
	private Set<Meeting> categorized = new HashSet<Meeting>();
	
	// constructors
	public Category() {}
	
	public Category(int id) {
		this.categoryID = id;
	}
	public Category(String name) {
		this.title = name;
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
