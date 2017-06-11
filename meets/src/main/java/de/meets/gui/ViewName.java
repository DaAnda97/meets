package de.meets.gui;

public enum ViewName {

	LOGIN,
	REGISTER,
	IMPRESSUM,
	PROFILE,
	OVERVIEW,
	MEET,
	CREATE
	
	;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
	
	
}
