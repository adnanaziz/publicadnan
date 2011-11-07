package guestbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class TestingLabLoginServlet extends HttpServlet {

	String [] users = {"andy", "bob", "charley"};
	String [] passwords = {"apple", "bathtub", "china" };

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String userId = request.getParameter("userId");
		
		if (userId == null) {
			out.println("<html><head><title>Bad Login</title>"
					+ "</head><body><h2>Need to enter a login id!"
					+ "</h2></body></html>");
			out.close();
			return;
		}
		userId = userId.toLowerCase().trim();
		
		String userPassword = request.getParameter("userPassword");
		if (userPassword == null) {
			out.println("<html><head><title>Bad Login</title>"
					+ "</head><body><h2>Need to enter a password!"
					+ "</h2></body></html>");
	       out.close();
	       return;
		}
        userPassword = userPassword.trim();
        boolean succLogin = false;
        int i = 0;
        for ( String id : users) {
           if (id.equals(userId) && passwords[i].equals(userPassword)) {
        	    succLogin = true;
        	    break;
           } 
           i++;
        }
        if (!succLogin) {
			out.println("<html><head><title>Bad Login</title>"
					+ "</head><body><h2>This combination of user id and password is incorrect."
					+ "</h2><br>Here's a <a href=\"http://adnan.appspot.com/testing-lab-login.html\">link</a>"
					+ "to the login page.</body></html>");
          return;
        }
        // TODO(AA): does setting the html type for response interfere with this?
        response.sendRedirect("http://adnan.appspot.com/testing-lab-calculator.html");
		// TODO(AA): do we need to close out?
		out.close();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}

