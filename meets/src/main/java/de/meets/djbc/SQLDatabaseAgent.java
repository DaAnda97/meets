package de.meets.djbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDatabaseAgent {

	public static void main(String[] args) {
		
		System.out.println("database connection test...");
		
		SQLDatabase connection = new SQLDatabase();
		// Get all categories
		String sql = "SELECT * FROM meets.category;";
		try {
		    Statement stmt = connection.connect().createStatement();
		    stmt.execute(sql);
		    
		    if (stmt.getResultSet() != null) {
		    	ResultSet result = stmt.getResultSet();
		    	while ( result.next() ) {
					System.out.println( result.getString(2) );;
				}
		    }//if
		    
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    connection.disconnect();
		}
		
	}
	
    // declare connection object
	private SQLDatabase database;
	private Connection connection;
	
    // declare SQLDatabaseAgent object
	SQLDatabaseAgent agent;
	
	// constructor
	private SQLDatabaseAgent() {
		
	}
	
	
	
	
	
}


