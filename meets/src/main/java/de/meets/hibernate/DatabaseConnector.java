package de.meets.hibernate;

import java.util.logging.Level;

import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import de.meets.assets.*;

public class DatabaseConnector {

	private SessionFactory factory;
	private ServiceRegistry registry;
	
	public DatabaseConnector() {
		setUp();
	}
	
	public SessionFactory getSessionFactory() {
		if ( factory == null ) {
			setUp();
		}//if
		return factory;
	}
	
	public void setUp() {
		// Logger
		Logger logger = java.util.logging.Logger.getLogger("org.hibernate");
		logger.setLevel(Level.SEVERE);
		
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		
		createHibernateClasses(config);
		
		// SessionFactory is set up once for an application!
		registry = new StandardServiceRegistryBuilder()
				.applySettings(config.getProperties())
				.build();
		factory = config.buildSessionFactory(registry);
	}
	
	public void tearDown() {
		if ( factory != null ) {
			if ( factory.isOpen() ) {
				System.out.println("Tear down...");
				factory.close();
				registry = null;
			}
		}//if
	}

	private void createHibernateClasses( Configuration configuration ) {
		configuration.addAnnotatedClass(Category.class);
		configuration.addAnnotatedClass(Location.class);
		configuration.addAnnotatedClass(Meeting.class);
		configuration.addAnnotatedClass(Member.class);
	}
	
	
}
