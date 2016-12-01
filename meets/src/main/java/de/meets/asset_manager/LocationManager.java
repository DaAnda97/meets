package de.meets.asset_manager;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.meets.assets.Location;

public final class LocationManager extends AssetManager<Location> {

	public LocationManager() {
		super(Location.LOCATION_TABLE);
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
	
	
}
