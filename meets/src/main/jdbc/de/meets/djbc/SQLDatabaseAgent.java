package de.meets.djbc;

public class SQLDatabaseAgent {

	
	
	private boolean JDBCDriverLoaded() {	
		System.out.println("Try to load driver...");
		// SQL driver available?
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		    return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the driver in the classpath!");
			return false;
		}
	}
	
}
