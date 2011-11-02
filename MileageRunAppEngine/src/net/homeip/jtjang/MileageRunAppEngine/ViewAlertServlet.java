package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class ViewAlertServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		logger.log(Level.INFO, "ManageAlertServlet.doGet(): nothing to do here");

		return;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		// obtain alert and associated deals
		String username = req.getParameter("username");
		long alertID = Long.parseLong(req.getParameter("alertid"));
		Entity alert = Alert.getSingleAlert(username, alertID);
		String dest = (String) alert.getProperty("dest");
		List<Entity> dealList = new ArrayList<Entity>(
				Util.listChildrenAsList(DealDBAdaptor.KIND,	alert.getKey()));
		Collections.sort(dealList, new Comparator<Entity>() {

			@Override
			public int compare(Entity ent0, Entity ent1) {
				return Double.compare((Double) ent0.getProperty("ppm"),
															(Double) ent1.getProperty("ppm"));
			}
		});

		// table of alert information
		String alertTableStr = alertTableHeader +
			"<tr>\n" +
			"<td>" + alert.getProperty("name") + "</td>" +
			"<td>" + alert.getProperty("origin") + "</td>" +
			"<td>" + alert.getProperty("dest") + "</td>" +
			"<td>" + alert.getProperty("monthyear") + "</td>" +
			"<td>" + alert.getProperty("checkfreq") + "</td>" +
			"<td>" + alert.getProperty("email") + "</td>" +
			"<td>" + alert.getProperty("targetppm") + "</td>" +
			"</tr>\n" +
			tableFooter;
		
		// table of deals information
		StringBuilder dealRows = new StringBuilder();
		Iterator<Entity> dealIter = dealList.iterator();
		int count=0;
		while (count<100 && dealIter.hasNext()) {
			Entity deal = dealIter.next();

			if (!"".equals(dest) && !deal.getProperty("dest").equals(dest)) continue; 

			dealRows.append("<tr>\n" +
				"<td>" + deal.getProperty("origin") + "</td>" +
				"<td>" + deal.getProperty("dest") + "</td>" +
				"<td>" + deal.getProperty("departdate") + "</td>" +
				"<td>" + deal.getProperty("returndate") + "</td>" +
				"<td>" + deal.getProperty("airline") + "</td>" +
				"<td>" + Long.toString((Long) deal.getProperty("distance")) + "</td>" +
				"<td>" + Long.toString((Long) deal.getProperty("price")) + "</td>" +
				"<td>" + new DecimalFormat("#.##").format((Double) deal.getProperty("ppm")) + "</td>" +
				"<td>" + deal.getProperty("founddate") + "</td>\n" +
				"</tr>\n");
			++count;
		}
		String dealTableStr = dealTableHeader + dealRows + tableFooter;
		
		PrintWriter out = resp.getWriter();
		out.println("<p>" + alertTableStr + "</p><hr /><p>" + dealTableStr + "<p>");
		
		
		return;
	}

	private static final String alertTableHeader =
		"  <table cellspacing=\"0\" cellpadding=\"0\">\n" + 
		"    <tbody>\n" + 
		"      <tr>\n" + 
		"        <td>Name</td><td>Origin</td><td>Dest</td><td>Month</td><td>Frequency</td>" +
		"				 <td>Destination Email</td><td>Target Price ($/mi)</td>\n" +
		"      </tr>\n";

	private static final String dealTableHeader =
		"  <table cellspacing=\"0\" cellpadding=\"0\">\n" + 
		"    <tbody>\n" + 
		"      <tr>\n" + 
		"        <td>From</td><td>To</td><td>Depart</td><td>Return</td><td>Airline</td>" +
		"				 <td>Distance</td><td>Price ($)</td><td>$/mile</td><td>Date found</td>\n" +
		"      </tr>\n";

	private static final String tableFooter =
		"    </tbody>\n" + 
		"  </table>\n";

}
