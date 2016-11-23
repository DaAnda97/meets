package de.meets.assetManager;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.meets.assets.Member;

public final class ManagerMember extends ManagerAsset<Member> {

	public ManagerMember() {
		super("Member");
	}
	
	// check if login is correct
	public Member get( String email, String password ) {	
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Member member = null;
		
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
