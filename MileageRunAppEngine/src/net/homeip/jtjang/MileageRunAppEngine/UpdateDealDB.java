package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class UpdateDealDB {
  private static final Logger logger = Logger.getLogger(UpdateDealDB.class.getCanonicalName());
	
	public static void updateByAlert(Key alertKey) {
		// get alert details
		Entity alert = Util.findEntity(alertKey);
		String origin = (String) alert.getProperty("origin");
		String dest = (String) alert.getProperty("dest");
		String monthYear = (String) alert.getProperty("monthyear");
		long dealID = DealDBAdaptor.getDealMaxID(alertKey);
		
		// get deals from kayak.com
		List<KayakDeal> dealList = fetchKayak(origin, dest, monthYear);
		String todayMDY = Util.getTodayMDYSlash();

		// cache deals into local db
		Iterator<KayakDeal> dealIter = dealList.iterator();
		while (dealIter.hasNext()) {
			KayakDeal deal = dealIter.next();
			DealDBAdaptor.createDeal(alertKey, ++dealID, deal.getOrigin(),
					deal.getDest(), deal.getDepartDate(), deal.getReturnDate(),
					deal.getAirline(), (long) deal.getDistance(), (long) deal.getPrice(),
					deal.getPPM(), todayMDY);
		}
	}
	
	public static List<KayakDeal> fetchKayak(String origin, String dest, String monthYear) {
		List<KayakDeal> kayakDeals = null;
		try {
			if (origin!=null && monthYear!=null) {
				kayakDeals = KayakScraper.getKayakDeals(origin, monthYear);

				if (!"".equals(dest) && kayakDeals!=null) {
					for (int i=0; i<kayakDeals.size(); ++i) {
						if (!kayakDeals.get(i).getDest().equals(dest)) {
							kayakDeals.remove(i);
						}
					}
				}
				
				updatePPM(kayakDeals);
			} else {
				System.err.println("departCode or yearMonth is null!");
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return kayakDeals;
	}

	
	private static void updatePPM(List<KayakDeal> deals) {
    for (int i=0; i<deals.size(); ++i) {
    	String origin = deals.get(i).getOrigin();
    	String dest = deals.get(i).getDest();
    	
    	// Get distance from AirportScraper and save to database
    	int distance = AirportScraper.lookupDistance(new String[] {origin, dest});

    	if (distance < 0) {
    		deals.remove(i);
    		--i;
    	} else {
    		int price = deals.get(i).getPrice();
	    	deals.get(i).setDistance(distance);
	    	double ppm = price / (2.0*distance);
	    	deals.get(i).setPPM(ppm);	    	
    	}
    }
	}

}
