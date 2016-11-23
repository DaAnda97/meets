package de.meets.assetManager;

import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import de.meets.hibernate.HibernateInit;


public abstract class ManagerAsset<E> {

	private SessionFactory factory;
	private String table;
	
	// constructor
	public ManagerAsset( String table ) {
		if ( factory == null ) {
			factory = HibernateInit.getInstance();
		}
		this.table = table;
	}
	
	protected SessionFactory getFactory() {
		return this.factory;
	}
	
	protected String getTable() {
		return this.table;
	}
	
	// CREATE a asset
	public int add( E asset ) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = 0;
		
		try {
			tx = session.beginTransaction();			
			id = (Integer) session.save(asset);
			tx.commit();
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}
	
	// READ all records of a asset
	@SuppressWarnings("unchecked")
	public Iterator<E> get() {
		Session session = factory.openSession();
		Transaction tx = null;
		Iterator<E> assets = null;
		
		try {
			tx = session.beginTransaction();
			List<?> listCategories = session.createQuery("FROM " +this.table).getResultList();
			assets = (Iterator<E>) listCategories.iterator();
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
	
	// READ a record of a asset
	@SuppressWarnings("unchecked")
	public E get( int id ) {
		Session session = factory.openSession();
		Transaction tx = null;
		E asset = null;
		
		try {
			tx = session.beginTransaction();
			asset = (E) session.createQuery("FROM " +this.table +" AS x WHERE x.id=" +id)
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
	
	// UPDATE a record of a asset
	public void alter( E asset ) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.update(asset);
			tx.commit();			
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	// DELETE a record of a asset
	public void delete( E asset ) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(asset);
			tx.commit();			
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
}
