package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.Cart;
import cs636.music.domain.Product;
import cs636.music.service.UserServiceAPI;

public class CartController implements Controller {
	private UserServiceAPI userService;
	private String view;

	public CartController(UserServiceAPI userService, String view) {
		this.userService = userService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		Cart userCart = userBean.getCart();
		boolean addItem = request.getParameter("addItem") != null;
		Product product = null;
		if (addItem)
			product = userBean.getProduct();

		// Make a cart for this user, if they don't already have one
		if (userCart == null) 
			userCart = userService.createCart();
		
		// Add new product to cart, if there is one
		if (addItem && product != null) 
			userService.addItemtoCart(product, userCart, 1);
		
		String invoiceNextUrl = userBean.getUser() == null ? "register.html?checkout=true" : "invoice.html";

		userBean.setCart(userCart);
		session.setAttribute("user", userBean);
		request.setAttribute("invoiceNextUrl", invoiceNextUrl);
		request.setAttribute("cart", userCart);
		return view;
	}
}
