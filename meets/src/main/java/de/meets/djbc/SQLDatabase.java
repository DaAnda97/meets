package de.meets.djbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLDatabase {

	// initial database constants
	// Note: You have to use the correct USERNAME and the correct PASSWORD that it works!
	//----------------------------------------------------------------------------------------------------
	//--- IMPORTANT: BEFORE you PUSH your recent changes DELTE the VALUE of the USERNAME and PASSWORD! ---
	//----------------------------------------------------------------------------------------------------
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://noixesdevel.de:3306/meets";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MAX_POOL = "250";
	
    // declare connection object
    private Connection connection;
    // declare properties object
    private Properties properties;
    
    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }
    
    // connect to database and return connection
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }//if
    }
}
	
