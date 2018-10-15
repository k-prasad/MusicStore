package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserWelcomeController implements Controller {
	private String view;

	public UserWelcomeController(String view) {
		this.view = view;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		
		// See if this user already has a bean.  If not, put one in session.
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null) {
			userBean = new UserBean();
			session.setAttribute("user", userBean);
		}
		return view;
	}
}
