package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		logger.log(Level.INFO, "In doGet()");
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		logger.log(Level.INFO, "In doPost()");
		String action = req.getParameter("action");
		String username = req.getParameter("username");
		if (action.equalsIgnoreCase("greeting")) {
			resp.setContentType("text/plain");
			resp.getWriter().println("Hello, " + username);
			return;
		} else if (action.equalsIgnoreCase("put")) {
			doPut(req, resp);
			return;
		}
	}

}
