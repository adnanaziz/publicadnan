package net.homeip.jtjang.MileageRunAppEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.SortDirection;

public class Alert {
	public static final String KIND = "Alert";
	
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

	public static void createAlert(long alertID, String username, String name,
			String origin, String dest, String monthYear, String email, double ppm,
			int checkFreq, String expireDate) {
		Entity alert = getSingleAlert(getAlertKeyStr(username, alertID));
		if (alert == null) {
			alert = new Entity("Alert", getAlertKeyStr(username, alertID));
			alert.setProperty("alertid", alertID);
			alert.setProperty("username", username);
			alert.setProperty("name", name);
			alert.setProperty("origin", origin);
			alert.setProperty("dest", dest);
			alert.setProperty("monthyear", monthYear);
			alert.setProperty("email", email);
			alert.setProperty("targetppm", ppm);
			alert.setProperty("checkfreq", checkFreq);
			alert.setProperty("createdate", Util.getTodayMDYSlash());
			alert.setProperty("expiredate", expireDate);
		} else {
			logger.log(Level.WARNING, "Alert.createAlert(): alert already exists!");
		}
		Util.persistEntity(alert);
	}

	private static String getAlertKeyStr(String username, long alertID) {
		return (username + Long.toString(alertID));
	}

	/**
	 * Searches for an alert by id and returns the entity.
	 * 
	 * @param id: id of the alert
	 * @return the entity with id as the key
	 */
	public static Entity getSingleAlert(String alertKeyStr) {
		Key alertKey = KeyFactory.createKey("Alert", alertKeyStr);
		return Util.findEntity(alertKey);
	}

	public static Entity getSingleAlert(String username, long alertID) {
		Key alertKey = KeyFactory.createKey("Alert", getAlertKeyStr(username, alertID));
		return Util.findEntity(alertKey);
	}

	public static Iterable<Entity> getAllAlerts(String username) {
		Iterable<Entity> entities = Util.listEntities("Alert", "username", username);
		return entities;
	}

	public static Iterable<Entity> getAllAlerts(String username, String sortBy, SortDirection direction) {
		Iterable<Entity> entities = Util.listEntities("Alert", "username", username, sortBy, direction);
		return entities;
	}
	
	public static void deleteEntity(String username, long alertID) {
		Key alertKey = KeyFactory.createKey("Alert", getAlertKeyStr(username, alertID));
		Iterable<Entity> childKeys = Util.listChildKeys(DealDBAdaptor.KIND, alertKey);
		Iterator<Entity> dealIter = childKeys.iterator();
		List<Key> childKeyList = new ArrayList<Key>();
		while (dealIter.hasNext()) {
			childKeyList.add(dealIter.next().getKey());
		}
		Util.deleteEntity(childKeyList);
		Util.deleteEntity(alertKey);
	}
	
	/**
	 * Return the max ID for username. 0 if the dataset is empty.
	 */
	public static long getAlertMaxID(String username) {
		Entity maxEntity = Util.getAlertMax("Alert", username, "alertid");
		
		if (maxEntity == null) {
			return 0;
		} else {
			return (Long) maxEntity.getProperty("alertid");
		}
	}
}
