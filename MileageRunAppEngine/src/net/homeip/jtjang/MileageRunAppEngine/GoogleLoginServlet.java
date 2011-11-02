package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class GoogleLoginServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		logger.log(Level.INFO, "GoogleLoginServlet.doGet(): use Google login service");

    UserService userService = UserServiceFactory.getUserService();

    String thisURL = req.getRequestURI();

    if (req.getUserPrincipal() != null) {
        resp.getWriter().println("<p>Hello, " + req.getUserPrincipal().getName() +
                                 "! <a href=\"" +
                                 userService.createLogoutURL(thisURL) +
                                 "\">sign out</a>.</p>");
        
        if (!"newsession".equals(req.getParameter("action"))) {
        	// User sign out, so redirect to / to avoid blank page.
        	resp.sendRedirect("/");
        }
    } else {
    		if (!"newsession".equals(req.getParameter("action"))) {
    			// User sign out, so redirect to / to avoid blank page.
    			resp.sendRedirect("/");
    		}
        resp.getWriter().println("<h3>Please <a href=\"" +
                                 userService.createLoginURL(thisURL) +
                                 "\">sign in</a>.</h3>");
    }
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		logger.log(Level.INFO, "GoogleLoginServlet.doPost(): nothing to do here");
		return;
	}
}
