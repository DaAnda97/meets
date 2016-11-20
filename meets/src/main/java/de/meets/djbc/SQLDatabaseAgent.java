package de.meets.djbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import de.meets.assets.Member;

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
	
	// execute SQL select statement and return result
	private ResultSet executeSelectStatement( String sql ) {
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
	
	// execute SQL insert, update, delete statement and return affected rows count
	private int executeUpdateStatement( String sql ) {
		try {
		    Statement stmt = this.connection.createStatement();
		    return stmt.executeUpdate(sql);
		} catch (SQLException e) {
		    System.out.println("+++ Failure: 'entry already exists' is ok! +++");
			e.printStackTrace();
        	return -1;
		} finally {
			//
		}
	}
	
	// check result set if a data set exists
	private boolean checkIfDataSetExists( String sql ) {
		ResultSet result = this.executeSelectStatement(sql);
		try {
			if ( result.next() ) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
        	System.out.println("----- SQLDatabaseAgent checkIfDataSetExists failure -----");
			e.printStackTrace();
        	System.out.println("----- SQLDatabaseAgent checkIfDataSetExists failure -----");
        	return false;
		}
	}
	
	// check mail and password
	public boolean checkLogin( String mail, String password ) {
		String[] mailParts = mail.split("@");
		
		// SQL statement
		String sql = "SELECT DISTINCT idMember FROM member"
				+ " INNER JOIN emaildomain ON member.mailDomain = emaildomain.idDomain"
				+ " WHERE emaildomain.domainName = \"" +mailParts[1] +"\""
				+ " AND member.memberPW = cast(\"" +password +"\" as binary(32));";
		
		return checkIfDataSetExists(sql);
	}
	
	// check mail
	public boolean checkEMail( String mail ) {
		String[] mailParts = mail.split("@");
		
		String sql = "SELECT DISTINCT idMember FROM member"
				+ " INNER JOIN emaildomain ON member.mailDomain = emaildomain.idDomain"
				+ " WHERE emaildomain.domainName = \"" +mailParts[1] +"\";";
		
		return checkIfDataSetExists(sql);
	}
	
	// check username
	public boolean checkUsername ( String username ) {
		String sql = "SELECT DISTINCT idMember FROM member WHERE username = \"" +username +"\";";
		return checkIfDataSetExists(sql);
	}
	
	// insert mail domain
	public boolean insertMailDomain ( String mailDomain ) {
		String sql = "INSERT IGNORE INTO emaildomain (domainName) VALUES (\"" +mailDomain +"\");";
		
		if ( executeUpdateStatement(sql) >= 1 ) {
			return true;
		} else {
			return false;
		}
	}
	
	// insert member
	public boolean insertMember ( Member member ) {
		String[] mailParts = member.getMail().split("@");
		
		// add mail domain if necessary
		if ( insertMailDomain(mailParts[1]) ) {
			System.out.printf("Added %s as a new domain!\n", mailParts[1]);
		} else {
			System.out.printf("%s is already stored!\n", mailParts[1]);
		}
		
		// get domain id
		ResultSet result = executeSelectStatement(
				"SELECT idDomain FROM emaildomain WHERE domainName = \"" +mailParts[1] +"\";");
		String domainID = null;
		try {
			result.next();
			domainID = result.getString(1);
		} catch (SQLException e) {
        	System.out.println("----- SQLDatabaseAgent insertMember_domainID failure -----");
			e.printStackTrace();
        	System.out.println("----- SQLDatabaseAgent insertMember_domainID failure -----");
		}
		
		// insert member
		String sql = "INSERT INTO member (username,preName,surName,memberPW,mailContact,mailDomain)"
				+ (" VALUES (\"" +member.getUsername() +"\",")
				+ (member.getPrename() == null ? "NULL," : "\"" +member.getPrename() +"\",")
				+ (member.getSurname() == null ? "NULL," : "\"" +member.getSurname() +"\",")
				+ ("\"" +member.getPassword() +"\",")
				+ ("\"" +mailParts[0] +"\",")
				+ (domainID +");");
		
		if ( executeUpdateStatement(sql) >= 1 ) {
			return true;
		} else {
			return false;
		}
	}

	// get all categories
	public HashMap<Integer, String> getCategories() {	
		
		// SQL select result
		ResultSet result = this.executeSelectStatement("SELECT * FROM category");
		
		// evaluate result
    	HashMap<Integer, String> categories = new HashMap<Integer, String>();
    	
    	try {
			while ( result.next() ) {
				categories.put( result.getInt(1), result.getString(2) );
			}
	    	return categories;
		} catch (SQLException e) {
			// failure
        	System.out.println("----- SQLDatabaseAgent getCategories failure -----");
			e.printStackTrace();
        	System.out.println("----- SQLDatabaseAgent getCategories failure -----");
			return null;
		}    	
	}
	
	
	
}


