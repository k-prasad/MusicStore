package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.Cart;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.UserData;

public class InvoiceController implements Controller {
	private UserServiceAPI userService;
	private String view;

	public InvoiceController(UserServiceAPI userService, String view) {
		this.userService = userService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		Cart userCart = userBean.getCart();
		UserData user = userBean.getUser();
		InvoiceData invoice = null;
		try {
			// The user registered just before getting to this page - create user
			if (user == null) {
				String firstName = (String) request.getParameter("firstName");
				String lastName = (String) request.getParameter("lastName");
				String email = (String) request.getParameter("email");
				userService.registerUser(firstName, lastName, email);
				user = userService.getUserInfo(email);
				userBean.setUser(user);
			}
			// We could have more interface objects to describe the invoice
			// and avoid using the internal Invoice object
			invoice = userService.checkout(userCart, user.getId());
		} catch (ServiceException e) {
			System.out.println("InvoiceController: " + e);
			throw new ServletException(e);
		}
		request.setAttribute("invoice", invoice);
		session.setAttribute("user", userBean);
		return view;
	}
}
