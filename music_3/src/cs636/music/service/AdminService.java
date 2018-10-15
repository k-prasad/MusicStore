package cs636.music.service;

import java.util.HashSet;
import java.util.Set;

import cs636.music.dao.AdminDAO;
import cs636.music.dao.DbDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;

/**
 * 
 * Provide admin level services to user app through accessing DAOs 
 * @author Chung-Hsien (Jacky) Yu
 */
public class AdminService implements AdminServiceAPI {
	
	private DbDAO db;
	private DownloadDAO downloadDb;
	private InvoiceDAO invoiceDb;
	private AdminDAO adminDb;
	
	/**
	 * construct a admin service provider 
	 * @param dbDao
	 * @param downloadDao
	 * @param invoiceDao
	 */
	public AdminService(DbDAO dbDao, DownloadDAO downloadDao ,InvoiceDAO invoiceDao, AdminDAO adminDao) {
		db = dbDao;
		downloadDb = downloadDao;
		invoiceDb = invoiceDao;
		adminDb = adminDao;
		
	}
	
	/**
	 * Clean all user table, not product and system table to empty
	 * and then set the index numbers back to 1
	 * @throws ServiceException
	 */
	public void initializeDB()throws ServiceException {
		try {
			db.startTransaction();
			db.initializeDb();
			db.commitTransaction();
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			db.rollbackAfterException();
			throw new ServiceException(
					"Can't initialize DB: (probably need to load DB)", e);
		}
	}
	
	/**
	 * process the invoice
	 * @param invoiceId
	 * @throws ServiceException
	 * JPA note: we can do the set to "y" here (it was in the DAO in music1)
	 * JPA tracks the change and does the update.
	 */
	public void processInvoice(long invoice_id) throws ServiceException {
		try {
			db.startTransaction();
			Invoice invoice = invoiceDb.findInvoice(invoice_id);
			invoice.setIsProcessed("y");
			db.commitTransaction();
		} catch (Exception e)
		{
			db.rollbackAfterException();
			throw new ServiceException("Invoice was not processed successfully: ",
			e);
		}
	}

	/**
	 * Get a list of all invoices
	 * @return list of all invoices
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofInvoices() throws ServiceException {
		Set<Invoice> invoices = null;
		try {
			db.startTransaction();
			invoices = invoiceDb.findAllInvoices();
			db.commitTransaction();
		} catch (Exception e) {
			throw new ServiceException("Can't find invoice list in DB: ", e);
		}
		Set<InvoiceData> invoices1 = new HashSet<InvoiceData>();
		for (Invoice i : invoices) {
			invoices1.add(new InvoiceData(i));
		}
		return invoices1;
	}
	
	/**
	 * Get a list of all unprocessed invoices
	 * @return list of all unprocessed invoices
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofUnprocessedInvoices() throws ServiceException {
		Set<Invoice> invoices = null;
		try {
			db.startTransaction();
			invoices = invoiceDb.findAllUnprocessedInvoices();
			db.commitTransaction();
		} catch (Exception e)
		{
			db.rollbackAfterException();
			throw new ServiceException("Can't find unprocessed invoice list: ", e);
		}
		Set<InvoiceData> invoices1 = new HashSet<InvoiceData>();
		for (Invoice i : invoices) {
			invoices1.add(new InvoiceData(i));
		}
		return invoices1;
	}
	
	/**
	 * Get a list of all downloads
	 * @return list of all downloads
	 * @throws ServiceException
	 */
	public Set<DownloadData> getListofDownloads() throws ServiceException {
		
		Set<Download> downloads = null;
		try {
			db.startTransaction();
			downloads = downloadDb.findAllDownloads();
			// track is to-one, then its Product is to-one
			// related user is to-one, so all eagerly loaded by default
			db.commitTransaction();
		} catch (Exception e)
		{
			db.rollbackAfterException();
			throw new ServiceException("Can't find download list: ", e);
		}
		Set<DownloadData> downloads1 = new HashSet<DownloadData>();
		for (Download d: downloads) {
			downloads1.add(new DownloadData(d));
		}
		return downloads1;
	}
	
	
	/**
	 * Check login user
	 * @param username
	 * @param password
	 * @return true if useranme and password exist, otherwise return false
	 * @throws ServiceException
	 */
	public Boolean checkLogin(String username,String password) throws ServiceException {
		try {
			db.startTransaction();
			Boolean b = adminDb.findAdminUser(username, password);
			db.commitTransaction();
			return b;
		} catch (Exception e)
		{
			db.rollbackAfterException();
			throw new ServiceException("Check login error: ", e);
		}
	}
	
}
