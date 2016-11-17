package de.meets.assets;

import java.util.HashMap;

import de.meets.djbc.SQLDatabaseAgent;

public abstract class Category {

	private static HashMap<Integer, String> categories;

	static {
		load();	
	}
	
	public static String getCategory( int id ) {
		if ( categories != null ) 
			return categories.get(id);
		else
			return null;
	}

	public static Integer[] getIDs() {
		return categories.keySet().toArray(new Integer[0]);
	}
	
	private static void load() {
		categories = new HashMap<Integer, String>();	
		SQLDatabaseAgent agent = SQLDatabaseAgent.getInstance();
		categories = agent.getCategories();	
	}
	
	public static boolean reload() {
		load();	
		if ( categories != null ) 
			return true;
		else 
			return false;
	}
	
}
