package de.meets.djbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class SQLDatabase {

	// declare database constants
	private static final String DATABASE_DRIVER;
    private static final String DATABASE_URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final String MAX_POOL;
	
    // declare connection object
    private static Connection connection;
    // declare properties object
    private static Properties properties;
    
    // initial database connection constants
    static {
    	String[] properties = new ConfigReader().getConfigValues();
    	
    	DATABASE_DRIVER = properties[0];
        DATABASE_URL = properties[1];
        USERNAME = properties[2];
        PASSWORD = properties[3];
        MAX_POOL = "250";
    }
    
    // private constructor
    private SQLDatabase() {}
    
    // create properties
    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }
    
    // connect to database and return connection
    public static Connection connect() {
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
    public static void disconnect() {
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
	
