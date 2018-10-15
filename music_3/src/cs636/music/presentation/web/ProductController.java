package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.Product;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;

public class ProductController implements Controller {
	private UserServiceAPI userService;
	private String view;

	public ProductController(UserServiceAPI userService, String view) {
		this.userService = userService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		Product product = null;
		String productCode = (String) request.getParameter("productCode");
		
		String listenNextUrl = userBean.getUser() == null ? "register.html" : "listen.html";
		
		if (productCode != null && !productCode.equals("")) {
			try {
				product = userService.getProduct(productCode);
				userBean.setProduct(product);
			} catch (ServiceException e) {
				System.out.println("ProductController: " + e);
				throw new ServletException(e);
			}
		}
		request.setAttribute("listenNextUrl", listenNextUrl);
		request.setAttribute("product", product);
		session.setAttribute("user", userBean);
		return view;
	}
}
