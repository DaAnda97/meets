package de.meets.djbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	private static final String CONFIG_FILE;
	private static final String[] PROPERTIES_TO_READ;
	
	static {
		// initial configuration file
		CONFIG_FILE = "/properties/config.properties";
		PROPERTIES_TO_READ = new String[] {
				"databaseDriver", 
				"databaseURL", 
				"username", 
				"password"};
	}
	
	public String[] getConfigValues() {
		
		// read properties file
		try ( InputStream inputStream = this.getClass().getResourceAsStream(CONFIG_FILE); ) {
			
			String[] results = new String[PROPERTIES_TO_READ.length];
			
			if (inputStream != null) {
				Properties prop = new Properties();
				prop.load(inputStream);
				
				// read every property		
				for (int i = 0; i < PROPERTIES_TO_READ.length; i++) {
					results[i] = prop.getProperty(PROPERTIES_TO_READ[i]);	
				}
				return results;
			} else {
				System.out.println("Properties file not found!");
			}
		} catch (IOException e) {
        	System.out.println("----- ConfigReader input failure -----");
			e.printStackTrace();
        	System.out.println("----- ConfigReader input failure -----");
        }//catch
	
		return null;
	}
	
}
