package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class TestAlertServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		logger.log(Level.INFO, "ManageAlertServlet.doGet(): nothing to do here");

		return;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String username = req.getParameter("username");
		int alertID = Integer.parseInt(req.getParameter("alertid"));
		logger.log(Level.INFO, "TestAlertServlet.doPost(): test alert" + alertID);
		StringBuilder testTableStr = new StringBuilder();
		
		PrintWriter out = resp.getWriter();

		Entity alert = Alert.getSingleAlert(username, alertID);
		String origin = alert.getProperty("origin").toString();
		String monthYear = alert.getProperty("monthyear").toString();
		try {
			// First test Kayak
			KayakScraper.setDEBUG(true);
			KayakScraper.setDEBUG_MSG("");
			List<KayakDeal> kayakDeal = KayakScraper.getKayakDeals(origin, monthYear);
			String debug_msg = KayakScraper.getDEBUG_MSG().replaceAll("\n", "<br>");
			KayakScraper.setDEBUG(false);
			KayakScraper.setDEBUG_MSG("");
			// Second test www.world-airport-codes.com
			int testDistance = 1;
			if (!origin.toUpperCase().equals("AUS")) {
				testDistance = AirportScraper.lookupDistance(new String[] {"AUS", origin});
			}
			
			if (testDistance==-1) {
				testTableStr.append(reportDistanceBadAirport(origin, monthYear));
			} else if (kayakDeal.size()==0) {
				testTableStr.append(reportKayakBadAirport(origin, monthYear));
			} else {
				testTableStr.append(reportGoodAirport(origin, monthYear));
			}
			testTableStr.append("<br><br><hr />");
			testTableStr.append(debug_msg);
		} catch (UnknownHostException e) {
			testTableStr.append(ParseErrorHandler(e, origin, monthYear));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			testTableStr.append(ParseErrorHandler(e, origin, monthYear));
		}

		out.println(testTableStr);

		
		return;
	}

	private StringBuilder ParseErrorHandler(Exception e, String origin, String monthYear) {
		StringBuilder testTableStr = new StringBuilder();
		testTableStr.append(reportBadURL(origin, monthYear));
		testTableStr.append("<br><hr />");
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		testTableStr.append(sw.toString().replaceAll("\n", "<br>"));
		return testTableStr;
	}
	
	private String reportBadURL(String origin, String monthYear) {
		final String problemStr =
			"This alert has the problem that the URL is invalid, resulting in an " +
			"exception.";
			
		final String problemStmt =
			"<p>" + alertURL(origin, monthYear) + "</p>"+
			"<p>" + ProblemHeader + "</p>" + "<br>" +
			"<ul><li>" + problemStr + "</li>" +
			"<li>" + ProblemItem1 + "</li>" +
			"<li>" + ProblemItem2 + "</li></ul>"; 
		
		return problemStmt;
	}

	private String reportDistanceBadAirport(String origin, String monthYear) {
		final String problemStr =
			"This alert has the problem that the airport code is invalid. Kayak " +
			"didn't tell us that the airport code was bad, but " +
			"http://www.world-airport-codes.com will complain.";
		final String problemStmt =
			"<p>" + alertURL(origin, monthYear) + "</p>" + "<br><br>" +
			"<p>" + ProblemHeader + "</p>" + "<br>" +
			"<ul><li>" + problemStr + "</li>" +
			"<li>" + ProblemItem1 + "</li></ul>"; 
		
		return problemStmt;
	}

	private String reportKayakBadAirport(String origin, String monthYear) {
		final String problemStr =
			"This alert has the problem that the airport code is invalid. Kayak " +
			"didn't tell us that the airport code was bad, just gave an empty " +
			"flight list.";
		final String problemStmt =
			"<p>" + alertURL(origin, monthYear) + "</p>" + "<br><br>" +
			"<p>" + ProblemHeader + "</p>" + "<br>" +
			"<ul><li>" + problemStr + "</li>" +
			"<li>" + ProblemItem1 + "</li></ul>"; 
		
		return problemStmt;
	}

	private String reportGoodAirport(String origin, String monthYear) {
		final String problemStmt =
			"<p>" + alertURL(origin, monthYear) + "</p>" + "<br><br>" +
			"<p>" + ProblemHeader + "</p>" + "<br>" +
			"<li>" + ProblemItem1 + "</li></ul>"; 
		
		return problemStmt;
	}
	
	private String alertURL(String origin, String monthYear) {
		String kayakURL = KayakScraper.getKayakFeedUrl(origin, monthYear);

		return "<p>URL corresponding to this alert = <a href=\"" +
						kayakURL + "\">" + kayakURL + "</a>";
	}

	private static final String ProblemHeader = 
		"The output from opening and parsing the XML feed from the URL is given " +
		"below. A well-formed URL should lead to a list of flights.";
	private static final String ProblemItem1 =
		"If the date is  incorrect, e.g., 13/2011, Kayak returns best deals for " +
		"the current month";
	private static final String ProblemItem2 =
		"If the date is  incorrect (e.g., 13-2011), Kayak just returns the latest " +
		"month's deals";

}
