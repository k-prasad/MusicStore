package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController implements Controller {
	private String view;

	public RegistrationController(String view) {
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// Forward to either invoice page or listen page
		boolean checkout = request.getParameter("checkout") != null;
		String nextPageURL = checkout ? "invoice.html" : "listen.html";
		request.setAttribute("nextPageURL", nextPageURL);
		
		return view;
	}
}
