package de.meets.asset_manager;

import java.util.Iterator;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import de.meets.hibernate.DatabaseConnector;


public abstract class AssetManager<E> {

	private SessionFactory factory;
	private CriteriaBuilder criteria;
	private String table;
	
	// constructor
	public AssetManager( String table, DatabaseConnector connector ) {
		if ( factory == null ) {
			factory = connector.getSessionFactory();
		}
		if ( criteria == null ) {
			criteria = factory.getCriteriaBuilder();
		}
		this.table = table;
	}
	
	protected SessionFactory getFactory() {
		return this.factory;
	}
	
	public String getTable() {
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
	
	// READ a record of a asset
	public abstract E get( int id );
	
	// READ all records of a asset
	@SuppressWarnings("unchecked")
	public Iterator<E> get( int begin, int end ) {
		Session session = factory.openSession();
		Transaction tx = null;
		Iterator<E> assets = null;
		
		try {
			tx = session.beginTransaction();
			
			Query<E> query = session.createQuery("FROM " +this.table);
			query.setMaxResults(end);
			
			List<E> listCategories = query.getResultList();
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
	
	// READ all records of a asset
	@SuppressWarnings("unchecked")
	public Iterator<E> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		Iterator<E> assets = null;
		
		try {
			tx = session.beginTransaction();
			List<E> listCategories = session.createQuery("FROM " +this.table).getResultList();
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
	
	// UPDATE a record of a asset
	public void update( E asset ) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(asset);
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
	public int delete( E asset ) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(asset);
			tx.commit();			
		} catch ( ConstraintViolationException e ) {
			return 0;
		} catch ( HibernateException e ) {
			if ( tx != null ) {
				tx.rollback();
			}
			// failure
			e.printStackTrace();
			return -1;
		} finally {
			session.close();
		}
		return 1;
	}
	
	// EXISTS value of column?
	protected boolean exists( String column, String value ) {
		return exists( new String[]{column}, new String[]{value} );
	}
	
	private boolean exists( String[] columns, String[] values ) {
		long count = count(columns, values);
		
		if ( count == 0L ) {
			// value NOT exists
			return false;
		} else {
			// value exists
			return true;
		}
	}
	
	// COUNT 
	private long count( String[] columns, String[] values ) {
		Session session = this.getFactory().openSession();
		Transaction tx = null;
		long count = 0;
		
		if ( columns != null && values != null && (columns.length == values.length) ) {
			// VALID input
			try {
				tx = session.beginTransaction();
				
				String hql = "SELECT COUNT(*) FROM " +this.table +" x WHERE x." 
						+columns[0] +"='" +values[0] +"'";
				
				if ( columns.length == 1 ) {
					// no AND needed
					count = (long) session.createQuery(hql).getSingleResult();
				} else {
					// AND needed
					for (int i = 1; i < values.length; i++) {
						hql += " AND " +columns[i] +"='" +values[i] +"'";
					}
					count = (long) session.createQuery(hql).getSingleResult();
				}			
				tx.commit();			
			} catch ( HibernateException e ) {
				if ( tx != null ) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		} else {
			// INVALID input
			System.err.println("Parameters must have the same array size!");
		}//else	
		
		return count;
	}//count()
	
}
