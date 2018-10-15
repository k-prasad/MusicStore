package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.Product;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;
import cs636.music.service.data.UserData;

public class ListenController implements Controller {
	private String view;
	private UserServiceAPI userService;

	public ListenController(UserServiceAPI userService, String view) {
		this.userService = userService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		Product product = userBean.getProduct();
		UserData user = userBean.getUser();
		request.setAttribute("product", product);

		// The user registered just before getting to this page - create user
		if (user == null) {
			try {
				String firstName = (String) request.getParameter("firstName");
				String lastName = (String) request.getParameter("lastName");
				String email = (String) request.getParameter("email");
				userService.registerUser(firstName, lastName, email);
				user = userService.getUserInfo(email);
				session.setAttribute("user", userBean);
				System.out.println("user registered");
				userBean.setUser(user);
			} catch (ServiceException e) {
				System.out.println("ListenController: " + e);
				throw new ServletException(e);
			}
		}
		System.out.println("Returning from ListenController");
		return view;
	}
}
