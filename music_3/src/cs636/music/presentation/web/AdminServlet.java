package cs636.music.presentation.web;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.AdminServiceAPI;
import cs636.music.service.ServiceException;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AdminServiceAPI adminService=null;

	private static final String ADMIN_JSP_DIR = "/WEB-INF/admin/";
	
	// Initialization of servlet: runs before any request is
	// handled in the web app. It does PizzaSystemConfig initialization
	// then sets up all the controllers
	@Override
	public void init() throws ServletException {
		System.out.println("Starting admin servlet initialization");
		// Dispatcher servlet has initialized system first
		// by load-on-startup settings in web.xml
		adminService = MusicSystemConfig.getAdminService();
		if (adminService == null)
			System.out.println("AdminServlet initialization problem!!!");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
         doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url=null;
		String requestURI = request.getRequestURI();
		System.out.println("doGet requestURI = " + requestURI);
		String uname = (String)request.getSession().getAttribute("adminUser");
		if (requestURI.contains("listVariables.html")) {
			url =  ADMIN_JSP_DIR + "listVariables.jsp"; // allow this without login
		} else if (uname == null) {
			requestURI = "adminWelcome.html"; // if not logged in, force login
			url=handleAdminLogin(request, response);
		} else if(requestURI.contains("adminWelcome.html")){
			url=handleAdminLogin(request, response);
		} else if (requestURI.contains("processInvoices.html")) {
			url=handleProcessInvoices(request, response);
		} else if (requestURI.contains("initDB.html")) {
			url=initializeDB(request, response);
		} else if (requestURI.contains("displayReports.html")) {
			url=handleDisplayReports(request, response);
		} else if (requestURI.contains("viewInvoice.html")) {
			url=handleUserInvoice(request, response);
		} else if (requestURI.contains("processInvoice.html")) {
			url = handleProcessInvoice(request, response);	
		} else if (requestURI.contains("logout.html")) {
			request.getSession().invalidate();  // drop session
			url =  ADMIN_JSP_DIR + "logout.jsp"; 

		} else {
			System.out.println("Unknown request URI: " + requestURI);
			throw new ServletException("Unknown request URI: "+requestURI);
		}
		
		//Need to check what needs to be done if the url is null
		RequestDispatcher dispatcher=request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}
	
	/*
	 *  Returns the url of the page that needs to be forwarded to
	 */
	private String handleAdminLogin(HttpServletRequest request, HttpServletResponse response){
		String url=null;
		String uname = (String)request.getSession().getAttribute("adminUser");
		if (uname!=null) {
			url = ADMIN_JSP_DIR + "adminMenu.jsp";
			return url;  // already logged in
		}
		// get credentials if there
		uname=request.getParameter("username");
		String pwd=request.getParameter("password");
		
		if (uname != null && pwd != null) {
			try {
				Boolean adminExistence = adminService.checkLogin(uname, pwd);
				// Success: Need to transfer the admin to the Admin Menu page
				// Bad credentials: Need to make the admin stay at the same login page.
				// Inform user 'Invalid Credentials'.
				if (adminExistence) {
					// save uname as session variable
					request.getSession().setAttribute("adminUser", uname); 
					url = ADMIN_JSP_DIR + "adminMenu.jsp";
				} else {
					request.setAttribute("error", "Invalid Credentials");
					url = ADMIN_JSP_DIR + "adminLogin.jsp";
				}
			} catch (ServiceException e) {
				String error = "Error in checking credentials: " + e;
				request.setAttribute("error", error);
				url = ADMIN_JSP_DIR + "error.jsp";
				System.out
						.println("Problem with the Admin Service during login"
								+ e);
			}
		}
		// missing or incomplete username/password: show login page
		else{			
			url = ADMIN_JSP_DIR + "adminLogin.jsp";
		}
		return url;
	}
	
	private String handleProcessInvoices(HttpServletRequest request,HttpServletResponse response){
		
		String url=null;
		try {
			Set<InvoiceData> pendingInvoices=adminService.getListofUnprocessedInvoices();
			request.setAttribute("unProcessedInvoices",pendingInvoices);
			System.out.println("#pendingInvoices: "+pendingInvoices.size() );
			url = ADMIN_JSP_DIR + "pendingInvoices.jsp";
		} catch (ServiceException e) {
			String error = "Error: "+ e;
			request.setAttribute("error", error);
			url = ADMIN_JSP_DIR + "error.jsp";
		}
		return url;
	}
	
	private String initializeDB(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String info = null;
		try {
			adminService.initializeDB();
			info = "success";
		} catch (ServiceException e) {
			info = "failed: " + MusicSystemConfig.exceptionReport(e);
		}
		request.setAttribute("info", info);
		String url = ADMIN_JSP_DIR + "initializeDB.jsp";
		return url;
	}
	
	private String handleDisplayReports(HttpServletRequest request,HttpServletResponse response){
		
		String url=null;
		String error = null;
		try {			
			Set<DownloadData> downloadReport=adminService.getListofDownloads();
			request.setAttribute("downloads", downloadReport);
			Set<InvoiceData> invoiceReport=adminService.getListofInvoices();
			request.setAttribute("invoices", invoiceReport);
			url= ADMIN_JSP_DIR + "reports.jsp";
		} catch (ServiceException e) {
			error = "Error: "+ e;
			request.setAttribute("error", error);
			url = ADMIN_JSP_DIR + "error.jsp";
		}
		return url;
	}
	
	private String handleUserInvoice(HttpServletRequest request,
			HttpServletResponse response) {
		
		String url="/adminController/processInvoices.html"; // success
		String error = null;
		String invoiceid=request.getParameter("invoiceId");
		long invId=Long.parseLong(invoiceid);
		// System.out.println(" Processing invoice " + invId + " .....");
		try {
		adminService.processInvoice(invId);
		} catch (ServiceException e) {
			error = "Error: "+ e;   
			request.setAttribute("error", error);
			url = ADMIN_JSP_DIR + "error.jsp";
		}
		
		return url;
	}
	private String handleProcessInvoice(HttpServletRequest request,
			HttpServletResponse response) {
		String url = ADMIN_JSP_DIR + "processInvoice.jsp";
		try {
		long processInvId=Long.parseLong((String)request.getParameter("pId"));
		adminService.processInvoice(processInvId);
		} catch (Exception e) {
			String error = "Error: "+ e;   
			request.setAttribute("error", error);
			url = ADMIN_JSP_DIR + "error.jsp";
		}	
		return url;
	}

}
