package cs636.music.dao;

import static cs636.music.dao.DBConstants.DOWNLOAD_TABLE;
import static cs636.music.dao.DBConstants.INVOICE_TABLE;
import static cs636.music.dao.DBConstants.LINEITEM_TABLE;
import static cs636.music.dao.DBConstants.SYS_TABLE;
import static cs636.music.dao.DBConstants.USER_TABLE;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Database connection and initialization.
 * Implemented singleton on this class.
 * 
 * @author Chung-Hsien (Jacky) Yu
 * 
 */
public class DbDAO {
	   
	private EntityManagerFactory emf;
	private ThreadLocal<EntityManager> threadEM = new ThreadLocal<EntityManager>();

	public EntityManager getEM() {
		return threadEM.get();
	}

	public DbDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public void startTransaction() {
		EntityManager em = emf.createEntityManager();
		threadEM.set(em); // save in thread-local storage
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	}

	public void commitTransaction() {
		// the commit call can throw, and then the caller needs to rollback
		getEM().getTransaction().commit();
		// We are using an application-managed entity manager, so we need
		// to explicitly close it to release its resources.
		// See Keith & Schincariol, pg. 138, first paragraph.
		// By closing the em at the end of the transaction, we are
		// following the pattern of transaction-scoped entity managers
		// used in EJBs by default.
		getEM().close(); // this causes the entities to become detached
		
		// Drop reference from thread to EM object, a possibly significant 
		// amount of memory even after it has been closed. In an application 
		// server, threads are pooled, so an individual thread actually lives 
		// longer than a request. Also, Tomcat 6.0.2x checks for this kind
		// of possible memory leak and outputs nasty messages "SEVERE: ..."
		// when it detects objects ref'd from ThreadLocals
		threadEM.set(null);
	}

	public void rollbackTransaction() {
		try {
			getEM().getTransaction().rollback();
		} finally {
			getEM().close();
			threadEM.set(null);  // see comment in commitTransaction()
		}
	}
	
	// Exceptions occurring in JPA code are almost always fatal to the
	// EntityManager context, meaning that we need to rollback the transaction
	// (and also close the EntityManager in our setup) and start over
	// or fail the action. An exception to this rule is the NoResultException
	// from the method singleResult()--it's OK to handle the exception and
	// continue the EntityManager/transaction after that particular exception.
	// If the caller has already seen an exception, it probably
	// doesn't want to handle a failing rollback, so it can use this.
	// Then the caller should issue its own exception based on the
	// original exception.
	public void rollbackAfterException() {
		try {
			rollbackTransaction();
		} catch (Exception e) {
			// discard secondary exception--probably server can't be reached
		}
	}
	
	/**
	*  bring DB back to original state
	*  @throws  SQLException
	**/
	public void initializeDb() throws SQLException {
		clearTable(DOWNLOAD_TABLE);
		clearTable(LINEITEM_TABLE);
		clearTable(INVOICE_TABLE);
		clearTable(USER_TABLE);	
		initSysTable();
	}

	// We can use direct SQL for DB setup easily as follows.
	// Any SQLException is handled by EL, marking the
	// transaction as rollback-only, and then EL throws a
	// org.eclipse.persistence.exceptions.DatabaseException
	private void clearTable(String tableName) {
		Query q = getEM().createNativeQuery("delete from " + tableName);
		int n = q.executeUpdate(); // SQL of update shows in FINE logging
		System.out.println("deleted " + n + " rows from " + tableName);
	}
	
	/**
	*  Set all the index number used in other tables back to 1
	*  @throws  SQLException
	**/
	private void initSysTable() {
		System.out.println("inserting new id start values into " + SYS_TABLE);
		Query q = getEM().createNativeQuery("update " + SYS_TABLE + " set gen_val=0");
		q.executeUpdate();
	}
}
