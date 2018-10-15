package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.UserServiceAPI;

// like Spring MVC DispatcherServlet and its config file, but simpler.
// This servlet is handling the pages of the music project,
// calling on various controllers, one for each page.
// Note that all the jsp filenames (for views) are in this file, not
// in the controllers themselves.  Each controller is set up
// here and given its forward-to URLs in its constructor.

public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 3971217231726348088L;
	private static UserServiceAPI userService;

	// The controllers, one per user page
	private static Controller userWelcomeController;
	private static Controller cartController;
	private static Controller catalogController;
	private static Controller invoiceController;
	private static Controller listenController;
	private static Controller downloadController;
	private static Controller productController;
	private static Controller registrationController;
		
	// String constants for URL's
	private static final String WELCOME_URL = "/welcome.html";
	private static final String WELCOME_VIEW = "/welcome.jsp";
	private static final String USER_WELCOME_URL = "/userWelcome.html";
	private static final String USER_WELCOME_VIEW = "/WEB-INF/jsp/userWelcome.jsp";
	private static final String CATALOG_URL = "/catalog.html";
	private static final String CATALOG_VIEW = "/WEB-INF/jsp/catalog.jsp";
	private static final String CART_URL = "/cart.html";
	private static final String CART_VIEW = "/WEB-INF/jsp/cart.jsp";
	private static final String PRODUCT_URL = "/product.html";
	private static final String PRODUCT_VIEW = "/WEB-INF/jsp/product.jsp";
	private static final String LISTEN_URL = "/listen.html";
	private static final String SOUND_VIEW = "/WEB-INF/jsp/sound.jsp";
	private static final String DOWNLOAD_URL = "/download.do"; // download.html didn't work
	// the download controller computes a forward URL
	private static final String REGISTER_URL = "/register.html";
	private static final String REGISTER_VIEW = "/WEB-INF/jsp/register.jsp";
	private static final String INVOICE_URL = "/invoice.html";
	private static final String INVOICE_VIEW = "/WEB-INF/jsp/invoice.jsp";
	// Initialization of servlet: runs before any request is
	// handled in the web app. It does MusicSystemConfig initialization
	// then sets up all the controllers
	@Override
	public void init() throws ServletException {
		System.out.println("Starting dispatcher servlet initialization");
		// If configureServices fails, it logs errors to the tomcat log, 
		// then throws (not caught here), notifying tomcat of its failure,
		// so tomcat won't allow any requests to be processed
		try {
		MusicSystemConfig.configureServices();
		} catch (Exception e) {
			System.out.println("DispatcherServlet: configureServices threw");
			System.out.println(MusicSystemConfig.exceptionReport(e));
		}
		userService = MusicSystemConfig.getUserService();
		// create all the controllers and their forward URLs
		userWelcomeController = new UserWelcomeController(USER_WELCOME_VIEW);
		cartController = new CartController(userService, CART_VIEW);
		catalogController = new CatalogController(userService, CATALOG_VIEW);
		invoiceController = new InvoiceController(userService, INVOICE_VIEW);
		listenController = new ListenController(userService, SOUND_VIEW);
		downloadController = new DownloadController(userService);  // computes redirect URL
		productController = new ProductController(userService, PRODUCT_VIEW);
		registrationController = new RegistrationController(REGISTER_VIEW);
	}
	
	// Called when app server is shutting this servlet down
	// because it is shutting the app down.
	// Since this servlet is in charge of this app, it is
	// the one to respond by shutting down the BL+DAO
	// (the SysTestServlet ignores the shutdown)
	@Override
	public void destroy() {
		System.out.println("DispatcherServlet: shutting down");
		MusicSystemConfig.shutdownServices();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURL = request.getServletPath(); 
		System.out.println("DispatcherServlet: requestURL = " + requestURL);
		// At userWelcome, the user gets a UserBean.
		// If it's not there for subsequent pages, hand the request to
		// studentWelcome. Having the bean is like being "logged in".
		boolean hasBean = (request.getSession().getAttribute("user") != null);
		if (hasBean) {
			UserBean bean = (UserBean)request.getSession().getAttribute("user");
			if (bean.getUser() == null)
				System.out.println("bean has no user");
			else
			    System.out.println("bean's user:" + bean.getUser().getEmailAddress());
			if (bean.getCart() == null)
				System.out.println("bean has no cart");
			else
				System.out.println("bean's cart itemcount = " + bean.getCart().getItems().size());
		} 

		// Dispatch to the appropriate Controller, which will determine
		// the next URL to use as well as do its own actions.
		// The URL returned by handleRequest will be a servlet-relative URL, 
		// like /WEB-INF/jsp/foo.jsp (a view) 
		// or /something.html (for a controller).
		// Note that although resources below /WEB-INF are inaccessible
		// to direct HTTP requests, they are accessible to forwards
		String forwardURL = null; 
		if (requestURL.equals(WELCOME_URL))
			forwardURL = WELCOME_VIEW; // no controller needed
        // test for bean, and if not there, send user to user welcome page
		else if (requestURL.equals(USER_WELCOME_URL) || !hasBean)  
			forwardURL = userWelcomeController.handleRequest(request, response);
		else if (requestURL.equals(CATALOG_URL))
			forwardURL = catalogController.handleRequest(request, response);
		else if (requestURL.equals(CART_URL))
			forwardURL = cartController.handleRequest(request, response);
		else if (requestURL.equals(PRODUCT_URL))
			forwardURL = productController.handleRequest(request, response);
		else if (requestURL.equals(LISTEN_URL)) 
			forwardURL = listenController.handleRequest(request, response);
		else if (requestURL.equals(DOWNLOAD_URL))   
			forwardURL = downloadController.handleRequest(request, response);
		else if (requestURL.equals(REGISTER_URL)) 
			forwardURL = registrationController.handleRequest(request, response); 
		else if (requestURL.equals(INVOICE_URL)) 
			forwardURL = invoiceController.handleRequest(request, response);
		else {
			System.out.println("DispatcherServlet: Unknown servlet path: "
					+ requestURL);
			// send HTTP 404 not found for bad URL
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		System.out.println("DispatcherServlet: forwarding to "+ forwardURL);
	
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(forwardURL);
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
