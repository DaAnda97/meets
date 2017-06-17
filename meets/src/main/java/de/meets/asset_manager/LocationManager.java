package de.meets.asset_manager;

import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import de.meets.assets.Location;
import de.meets.hibernate.DatabaseConnector;

public final class LocationManager extends AssetManager<Location> {

	public LocationManager( DatabaseConnector connector ) {
		super(Location.LOCATION_TABLE, connector);
	}

	@Override
	public Location get(int id) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Location asset = null;
		
		try {
			tx = session.beginTransaction();
			asset = session.get(Location.class, id);
			tx.commit();		
		} catch ( NoResultException e ) {
			// record not found
			return null;
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return asset;
	}
	
	// get location by city
	public Location get(String city) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Location asset = null;
		
		try {
			tx = session.beginTransaction();
			asset = (Location) session.createQuery("FROM Location l WHERE l.city='" +city +"'")
					.getSingleResult();
			tx.commit();
		} catch ( NoResultException e ) {
			// record not found
			return null;
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return asset;
	}
	
	public Iterator<Location> getByArea(
			double latitude, double longitude, int kilometre, 
			int begin, int end) {
		
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Iterator<Location> assets = null;
		
		try {
			tx = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			Query<Location> query = session.createQuery(
					" FROM " + Location.LOCATION_TABLE
					+ " HAVING "
					+ "(6371 * acos(cos(radians( " + latitude + " )) * "
					+ "cos(radians( " + Location.LOCATION_LATITUDE + ")) *"
					+ "cos(radians( " + Location.LOCATION_LONGITUDE + ") - "
					+ "radians( " + longitude + " )) + "
					+ "sin(radians( " + latitude  + " )) *"
					+ "sin(radians( " + Location.LOCATION_LATITUDE + "))))"
					+ " <= " + kilometre);
			query.setFirstResult(begin);
			query.setMaxResults(end);
			
			/*
			Query<Meeting> query = session.createQuery(
					"SELECT *"
					+ ", (6371 * acos(cos(radians( :lat )) * cos(radians( lat)) "
					+ "* cos(radians( lng) - radians( :lng )) + sin(radians( :lat )) "
					+ "* sin(radians( lat)))) AS distance"
					
					+ " FROM " + Location.LOCATION_TABLE
					+ " HAVING distance <= " + kilometre
					+ " ORDER BY distance ASC");
			query.setFirstResult(begin);
			query.setMaxResults(end);
			*/
			
			List<Location> listCategories = query.getResultList();
			assets = (Iterator<Location>) listCategories.iterator();
			
			tx.commit();			
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return assets;
	}
	
	
}
