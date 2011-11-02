package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class ListAlertServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		logger.log(Level.INFO, "ManageAlertServlet.doGet(): nothing to do here");

		return;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String action = req.getParameter("action");
		
		if (action.equals("list")) {
			listAlert(req, resp);
		} else if (action.equals("delete")) {
			deleteAlert(req, resp);
		}

		return;
	}

	private void deleteAlert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = req.getParameter("username");
		int alertID = Integer.parseInt(req.getParameter("alertid"));
		logger.log(Level.INFO, "ListAlertServlet.doPost(): delete alert" + alertID);

		Alert.deleteEntity(username, alertID);
	}

	private void listAlert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		logger.log(Level.INFO, "ListAlertServlet.doPost(): obtaining alert listing");
		String username = req.getParameter("username");

		PrintWriter out = resp.getWriter();
		Iterable<Entity> entities = null;

		entities = Alert.getAllAlerts(username, "createdate", SortDirection.ASCENDING);
		StringBuilder alertRows = new StringBuilder();
		if (entities != null) {
			Iterator<Entity> iterEnt = entities.iterator();
			while (iterEnt.hasNext()) {
				Entity alert = iterEnt.next();
				long alertid = (Long) alert.getProperty("alertid");
				String alertName = (String) alert.getProperty("name");
				String origin = (String) alert.getProperty("origin");
				String dest = (String) alert.getProperty("dest");
				String monthYear = (String) alert.getProperty("monthyear");
				String createDate = (String) alert.getProperty("createdate");
				alertRows.append(new StringBuilder("<tr>\n" + 
						"<td>" + alertid + "</td>\n" + 
						"<td>" + alertName + "</td>\n" + 
						"<td>" + generateDescr(origin, dest, monthYear) + "</td>\n" + 
						"<td><a href=\"#\" onclick=\"viewAlert(this.id); return false;\" id=\"view" + alertid + "\">View</a>&nbsp" +
						"<a href=\"#\" onclick=\"deleteAlert(this.id); return false;\" id=\"delete" + alertid + "\">Delete</a>&nbsp" +
						"<a href=\"#\" onclick=\"testAlert(this.id); return false;\" id=\"test" + alertid + "\">Test</a>&nbsp</td>\n" + 
						"<td>" + createDate + "</td>\n" + 
				"</tr>\n"));

			}
		}

		String alertTable = alertTableHeader + alertRows + alertTableFooter;
		out.println(alertTable);
	}

	private String generateDescr(String origin, String dest, String monthYear) {
		int month=0, year=0;
		if (monthYear.length()==5) {
			month = Integer.parseInt(monthYear.substring(0, 1));
			year = Integer.parseInt(monthYear.substring(1));
		} else if (monthYear.length()==6) {
			month = Integer.parseInt(monthYear.substring(0, 2));
			year = Integer.parseInt(monthYear.substring(2));
		} else {
			logger.log(Level.WARNING, "ListAlertServlet.generateDescr(): monthYear in wrong format!");
		}

		String monthStr = "";
		switch (month) {
		case 1:	monthStr = "January";	break;
		case 2:	monthStr = "February"; break;
		case 3:	monthStr = "March";	break;
		case 4:	monthStr = "April"; break;
		case 5:	monthStr = "May";	break;
		case 6:	monthStr = "June"; break;
		case 7:	monthStr = "July"; break;
		case 8:	monthStr = "Auguest"; break;
		case 9:	monthStr = "September";	break;
		case 10: monthStr = "October"; break;
		case 11: monthStr = "November";	break;
		case 12: monthStr = "December"; break;
		default: break;
		}

		String result = "";
		if (!"".equals(dest)) {
			result = origin + "-" + dest + ":" + monthStr + " " + year;
		} else {
			result = origin + ":" + monthStr + " " + year;
		}
		return result;
	}

	private String alertTableHeader =
		"  <table cellspacing=\"0\" cellpadding=\"0\">\n" + 
		"    <tbody>\n" + 
		"      <tr>\n" + 
		"        <td>id</td>\n" + 
		"        <td>Name</td>\n" + 
		"        <td>Description</td>\n" + 
		"        <td>Actions</td>\n" + 
		"        <td>Date Added</td>\n" + 
		"      </tr>\n" + 
		"";

	private String alertTableFooter =
		"    </tbody>\n" + 
		"  </table>\n";
}
