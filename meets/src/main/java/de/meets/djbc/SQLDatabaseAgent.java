package de.meets.djbc;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class SQLDatabaseAgent {
	
    // declare connection object
	private Connection connection;
	
    // declare SQLDatabaseAgent object
	private static SQLDatabaseAgent AGENT;
	
	// constructor
	private SQLDatabaseAgent() {
		this.connection = SQLDatabase.connect();
	}
	
	// get SQL agent instance
	public static SQLDatabaseAgent getInstance() {
		if ( AGENT == null ) {
			AGENT = new SQLDatabaseAgent();
		}
		return AGENT;
	}
	
	// execute SQL statement and return result
	private ResultSet executeSQL( String sql ) {
		try {
		    Statement stmt = this.connection.createStatement();
		    stmt.execute(sql);
		    
		    // evaluate result
		    if (stmt.getResultSet() != null) {
		    	return stmt.getResultSet();
		    } else {
		    	return null;
		    }	    
		} catch (SQLException e) {
		    e.printStackTrace();
			return null;
		} finally {
			//
		}
	}
	
	// check mail and password
	public boolean checkLogin( String mail, String password ) {
		// SQL select result
		String[] mailParts = mail.split("@");
		
		ResultSet result = this.executeSQL(
				"SELECT DISTINCT idMember\n"
				+ "FROM member\n"
				+ "INNER JOIN emailcontact ON member.memberMAIL = emailcontact.idAddress\n"
				+ "INNER JOIN emaildomain ON emaildomain.idDomain = emailcontact.idDomain\n"
				+ "WHERE emaildomain.domainName = \"" +mailParts[1] +"\"\n"
				+ "AND emailcontact.contact = \"" +mailParts[0] +"\"\n"
				+ "AND member.memberPW = cast(\"" +password +"\" as binary(32));"
			);
		if ( result != null )
			return true;
		else 
			return false;
	}
	
	// get all categories
	public HashMap<Integer, String> getCategories() {	
		
		// SQL select result
		ResultSet result = this.executeSQL("SELECT * FROM category");
		
		// evaluate result
    	HashMap<Integer, String> categories = new HashMap<Integer, String>();
    	
    	try {
			while ( result.next() ) {
				categories.put( result.getInt(1), result.getString(2) );
			}
	    	return categories;
		} catch (SQLException e) {
			// failure
			e.printStackTrace();
			return null;
		}    	
	}
	
	
	
}


