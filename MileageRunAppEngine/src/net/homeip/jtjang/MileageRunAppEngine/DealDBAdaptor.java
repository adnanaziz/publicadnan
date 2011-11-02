package net.homeip.jtjang.MileageRunAppEngine;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DealDBAdaptor {
	public static final String KIND = "Deal";
	
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

	// TODO use static factory with chaining instead
	public static void createDeal(Key alertKey, long dealID, 
			String origin, String dest, String departDate, String returnDate,
			String airline, long distance, long price, double ppm, String foundDate) {
		Entity alert = new Entity(KIND, dealID, alertKey);
		alert.setProperty("dealid", dealID);
		alert.setProperty("origin", origin);
		alert.setProperty("dest", dest);
		alert.setProperty("departdate", departDate);
		alert.setProperty("returndate", returnDate);
		alert.setProperty("airline", airline);
		alert.setProperty("distance", distance);
		alert.setProperty("price", price);
		alert.setProperty("ppm", ppm);
		alert.setProperty("founddate", foundDate);

		Util.persistEntity(alert);
	}

	/**
	 * Searches for a deal by id and returns the entity.
	 * 
	 * @param id: id of the deal
	 * @return the entity with id as the key
	 */
	public static Entity getSingleDeal(Key alertKey, long dealID) {
		Key dealKey = KeyFactory.createKey(alertKey, KIND, dealID);
		return Util.findEntity(dealKey);
	}


	public static Iterable<Entity> getAllDeals(Key alertKey) {
		Iterable<Entity> entities = Util.listChildren(KIND, alertKey);
		return entities;
	}


	public static void deleteEntity(Key dealKey) {
		Util.deleteEntity(dealKey);
	}

	/**
	 * Return the max ID for username. 0 if the dataset is empty.
	 */
	public static long getDealMaxID(Key alertKey) {
		Entity maxEntity = Util.getDealMax(alertKey, KIND, "dealid");

		if (maxEntity == null) {
			return 0;
		} else {
			return (Long) maxEntity.getProperty("dealid");
		}
	}

}
