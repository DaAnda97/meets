package de.meets.asset_manager;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.meets.assets.Member;

public final class MemberManager extends AssetManager<Member> {

	public MemberManager() {
		super(Member.MEMBER_TABLE);
	}
	
	@Override
	public int add( Member asset ) {
		asset.setUsername( asset.getUsername().toLowerCase() );
		asset.setEmail( asset.getEmail().toLowerCase() );
		
		return super.add(asset);
	}
	
	// check if username is already in use
	public boolean checkUsername( String username ) {
		return this.exists( Member.MEMBER_USERNAME, username.toLowerCase() );
	}
	
	// check if email is already in use
	public boolean checkEMail( String email ) {
		return this.exists( Member.MEMBER_EMAIL, email.toLowerCase() );
	}
	
	// check if login is correct
	public boolean checkLogin( String email, String password ) {	
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
			return false;
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return true;
	}
	
	public Member getMember( String email ) {	
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Member member = null;
		
		email = email.toLowerCase();
		
		try {
			tx = session.beginTransaction();
			member = (Member) session.createQuery(
					"FROM Member m WHERE m.email='" +email)
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

	@Override
	public Member get(int id) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Member asset = null;
		
		try {
			tx = session.beginTransaction();
			asset = session.get(Member.class, id);
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
