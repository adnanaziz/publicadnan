package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class CreateAlertServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		logger.log(Level.INFO, "CreateAlertServlet.doGet(): nothing to do here");
		return;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		logger.log(Level.INFO, "ManageAlertServlet.doPost(): obtaining alert listing");
		String username = req.getParameter("username");
		String alertName = req.getParameter("alertName");
		String origin = req.getParameter("origin");
		String dest = req.getParameter("dest");
		String monthYear = req.getParameter("monthYear");
		String email = req.getParameter("email");
		double targetppm = Double.parseDouble(req.getParameter("targetppm"));
		String expireDate = req.getParameter("expireDate");
		String checkFreq = req.getParameter("checkFreq");

		if (!"".equals(monthYear) && monthYear.length()!=6) {
			monthYear = "0" + monthYear;	// prefix with "0"
		}
		String expireDay = expireDate.substring(0, 2);
		String expireMonth = expireDate.substring(3, 5);
		String expireYear = expireDate.substring(6, 10);
		int checkFreqHours=6;
		if (checkFreq.equals("sixhours")) {
			checkFreqHours = 6;
		} else if (checkFreq.equals("oneday")) {
			checkFreqHours = 24;
		} else if (checkFreq.equals("oneweek")) {
			checkFreqHours = 168;
		}
		
		long maxID = Alert.getAlertMaxID(username);
		Alert.createAlert(maxID+1, username, alertName, origin, dest, monthYear, email, targetppm, checkFreqHours, expireDate);
		
		
		PrintWriter out = resp.getWriter();
		out.print("test" + Long.toString(maxID+1));
	}
}
