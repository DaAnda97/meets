package de.meets.hibernate;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import de.meets.assets.*;

public class HibernateInit {

	private static SessionFactory factory;
	private static ServiceRegistry registry;
	private static CriteriaBuilder criteria;
	
	private HibernateInit() {}
	
	public static SessionFactory getInstance() {
		if ( factory == null ) {
			try {
				setUp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//if
		return factory;
	}
	
	public static CriteriaBuilder getCriteriaInstance() {
		if ( criteria == null ) {
			try {
				setUp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//if
		return criteria;
	}
	
	private static void setUp() throws Exception {
		// logger
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
		criteria = factory.getCriteriaBuilder();
	}
	
	private static void createHibernateClasses( Configuration configuration ) {
		configuration.addAnnotatedClass(Category.class);
		configuration.addAnnotatedClass(Location.class);
		configuration.addAnnotatedClass(Meeting.class);
		configuration.addAnnotatedClass(Member.class);
	}
	
	public static void tearDown() {
		if ( factory.isOpen() )
			factory.close();
	}

}
