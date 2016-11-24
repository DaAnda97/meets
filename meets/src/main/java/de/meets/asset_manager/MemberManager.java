package de.meets.asset_manager;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.meets.assets.Member;

public final class MemberManager extends AssetManager<Member> {

	public MemberManager() {
		super("Member");
	}
	
	@Override
	public int add( Member asset ) {
		asset.setUsername( asset.getUsername().toLowerCase() );
		asset.setEmail( asset.getEmail().toLowerCase() );
		return super.add(asset);
	}
	
	// check if username exists already
	public boolean existsUsername( String username ) {
		long count = this.count("username", username.toLowerCase());
		
		if ( count == 0L ) {
			// username NOT exists
			return false;
		} else {
			// username exists
			return true;
		}
	}

	// check if email exists already
	public boolean existsEMail( String email ) {
		long count = this.count("email", email.toLowerCase());
		
		if ( count == 0L ) {
			// username NOT exists
			return false;
		} else {
			// username exists
			return true;
		}
	}
	
	private long count( String column, String value ) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		long count = 0;
		
		try {
			tx = session.beginTransaction();
			count = (long) session.createQuery("SELECT COUNT(*) FROM Member m WHERE m." +column +"='" +value +"'")
					.getSingleResult();
			tx.commit();			
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return count;
	}
	
	// check if login is correct
	public Member get( String email, String password ) {	
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Member member = null;
		
		email = email.toLowerCase();
		
		try {
			tx = session.beginTransaction();
			member = (Member) session.createQuery(
					"FROM Member m WHERE m.email='" +email +"' and m.password='" +password +"'")
					.getSingleResult();
			tx.commit();			
		} catch ( NoResultException e ) {
			// record not found - login failed
			return null;
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return member;
	}	

}
