package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public abstract class CronServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());
	protected Iterable<Entity> alertList;
	
	/*
	 * Cron job: remove expired alerts, fetch data, and email alerts.
	 */
	protected void cronJobs(int hours) {
		alertList = Util.listEntities(Alert.KIND, "checkfreq", hours);
		cronDeleteExpired(hours);
		cronFetchData(hours);
		cronEmailAlerts(hours);
	}
	
	
	private void cronDeleteExpired(int hours) {
		Iterator<Entity> alertIter = alertList.iterator();
		while (alertIter.hasNext()) {
			Entity alert = alertIter.next();
			String expireDate = (String) alert.getProperty("expiredate");
			String todayDate = Util.getTodayMDYSlash();
			if (expireDate.compareTo(todayDate) < 0) {
				Alert.deleteEntity((String) alert.getProperty("username"),
													 (Long) alert.getProperty("alertid"));
			}
		}		
	}


	protected void cronFetchData(int hours) {
		Iterator<Entity> alertIter = alertList.iterator();
		while (alertIter.hasNext()) {
			Entity alert = alertIter.next();
			logger.log(Level.INFO, "Util.cronUpdate(): Updating Alert " + alert.getProperty("name"));
			UpdateDealDB.updateByAlert(alert.getKey());
		}
	}

	private void cronEmailAlerts(int hours) {
		Iterator<Entity> alertIter = alertList.iterator();
		while (alertIter.hasNext()) {
			Entity alert = alertIter.next();
			String dealRows = collectDeals(alert);
			if (!"".equals(dealRows)) {
				try {
					sendAlertMail(dealRows, alert);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


  private String collectDeals(Entity alert) {
  	double targetPPM = (Double) alert.getProperty("targetppm");
  	StringBuilder dealRowsSB = new StringBuilder();
  	
  	Iterable<Entity> dealList = Util.listChildren(DealDBAdaptor.KIND, alert.getKey());
  	Iterator<Entity> dealIter = dealList.iterator();
  	while (dealIter.hasNext()) {
  		Entity deal = dealIter.next();
  		if (Double.compare((Double) deal.getProperty("ppm"), targetPPM) < 0) {
  			dealRowsSB.append("\nDeal: From " + deal.getProperty("origin").toString() +
  					" to " + deal.getProperty("dest").toString() +
  					", depart at " + deal.getProperty("departdate").toString() + 
  					", return at " + deal.getProperty("returndate").toString() +
  					", airline " + deal.getProperty("airline").toString() +
  					", price=" + Long.toString((Long) deal.getProperty("price")) +
  					", $/mi=" + new DecimalFormat("#.##").format((Double) deal.getProperty("ppm")) +
  					", date found=" + deal.getProperty("founddate").toString() );
  		}
  	}
  	
  	if (dealRowsSB.length() == 0) {
  		return new String("");
  	} else {
  		return new String(dealRowsSB);
  	}
	}


	private void sendAlertMail(String dealRows, Entity alert) throws IOException {
  	String email = alert.getProperty("email").toString();
  	String username = alert.getProperty("username").toString();
  	String subject = "MileageRun alert for " + username;
  	String msgBody = " Hi " + username +
  					", we have found the following deals for your alert (" +
  					alert.getProperty("name").toString() + ")\n" + dealRows;
  	SendMail sendmail = new SendMail();
  	sendmail.send(email, subject, msgBody);
  }

}
