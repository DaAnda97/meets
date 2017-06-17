package de.meets.asset_manager;

import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import de.meets.assets.Category;
import de.meets.assets.Location;
import de.meets.assets.Meeting;
import de.meets.hibernate.DatabaseConnector;

public final class MeetingManager extends AssetManager<Meeting> {

	public MeetingManager( DatabaseConnector connector ) {
		super(Meeting.MEETING_TABLE, connector);
	}

	@Override
	public Meeting get(int id) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Meeting asset = null;
		
		try {
			tx = session.beginTransaction();
			asset = session.get(Meeting.class, id);
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
	
	public Iterator<Meeting> get(int begin, int end, Category category) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Iterator<Meeting> assets = null;
		
		try {
			tx = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			Query<Meeting> query = session.createQuery(
					"FROM " +Meeting.MEETING_TABLE 
					+" WHERE " + Meeting.MEETING_CATEGORY + " = " +category.getCategoryID());
			query.setFirstResult(begin);
			query.setMaxResults(end);
			
			List<Meeting> listCategories = query.getResultList();
			assets = (Iterator<Meeting>) listCategories.iterator();
			
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
	
	public Iterator<Meeting> get(int begin, int end, Location... locations) {
		if (locations != null) {
			return getByLocations(begin, end, locations);
		} else {
			return get(begin, end);
		}
	}
	
	private Iterator<Meeting> getByLocations(int begin, int end, Location... locations) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Iterator<Meeting> assets = null;
		
		try {
			tx = session.beginTransaction();
			
			String sql = "FROM " +Meeting.MEETING_TABLE 
					+" WHERE " + Meeting.MEETING_LOCATION + " = " +locations[0].getLocationID();
			
			for (int i = 1; i < locations.length; i++) {
				sql += " OR " + Meeting.MEETING_LOCATION + " = " +locations[i].getLocationID();
			}
			
			@SuppressWarnings("unchecked")
			Query<Meeting> query = session.createQuery(sql);
			query.setFirstResult(begin);
			query.setMaxResults(end);
			
			List<Meeting> listCategories = query.getResultList();
			assets = (Iterator<Meeting>) listCategories.iterator();
			
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
