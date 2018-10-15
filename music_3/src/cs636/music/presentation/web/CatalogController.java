package cs636.music.presentation.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs636.music.domain.Product;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;

public class CatalogController implements Controller {
	private UserServiceAPI userService;
	private String view;

	public CatalogController(UserServiceAPI userService, String view) {
		this.userService = userService;
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<Product> products = new ArrayList<Product>();
		try {
			products = new ArrayList<Product>(userService.getProductList());
		} catch (ServiceException e) {
			System.out.println("CatalogController: " + e);
			throw new ServletException(e);
		}
		request.setAttribute("products", products);
		return view;
	}
}
