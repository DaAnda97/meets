package de.meets.asset_manager;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.meets.assets.Meeting;

public final class MeetingManager extends AssetManager<Meeting> {

	public MeetingManager() {
		super(Meeting.MEETING_TABLE);
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
	
}
