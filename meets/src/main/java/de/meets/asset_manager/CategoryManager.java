package de.meets.asset_manager;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.meets.assets.Category;
import de.meets.hibernate.DatabaseConnector;

public final class CategoryManager extends AssetManager<Category> {

	public CategoryManager( DatabaseConnector connector ) {
		super(Category.CATEGORY_TABLE, connector);
	}

	@Override
	public Category get(int id) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Category asset = null;
		
		try {
			tx = session.beginTransaction();
			asset = session.get(Category.class, id);
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
	
	// get Category by title
	public Category get(String title) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		Category asset = null;
		
		try {
			tx = session.beginTransaction();
			asset = (Category) session.createQuery("FROM Category l WHERE l.title='" +title +"'")
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
