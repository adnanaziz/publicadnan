package net.homeip.jtjang.MileageRun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowMileageActivity extends ListActivity {
	public static final String KEY_PPM = "PPM";
	public static final String KEY_ORIGIN = "ORIGIN";
	public static final String KEY_DEST = "DEST";
	public static final String KEY_DEPARTDATE = "DEPART_DATE";
	public static final String KEY_RETURNDATE = "RETURN_DATE";
	public static final String KEY_AIRLINE = "AIRLINE";
	
	
	ArrayList<HashMap<String, String>> deals_list = new ArrayList<HashMap<String, String>>();
	private AirportDBAdapter mDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dealslist);
		ListView deals_lv = getListView();
		setDoneListener(R.id.return_button);

		Bundle extras = getIntent().getExtras();
		String departCode=null, yearMonth=null;
		if (extras != null) {
			departCode = extras.getString(MileageRunActivity.KEY_DEPARTCODE);
			yearMonth = extras.getString(MileageRunActivity.KEY_YEARMONTH);
		}

		ArrayList<KayakDeal> deals = null;
		try {
			if (departCode!=null && yearMonth!=null) {
				deals = KayakScraper.getKayakDeals(departCode, yearMonth);
				updatePPM(deals);
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

		// Display deals in default order (in PPM from small to large)
		Collections.sort(deals, new KayakDeal());
		
		HashMap<String, String> deal;
		for (int i=0; i<deals.size(); ++i) {
			if (deals.get(i).getPPM() > 0) {
				// Only display deals with positive distance
				deal = new HashMap<String, String>();
				deal.put(KEY_PPM, String.format("%.2f", deals.get(i).getPPM()));
				deal.put(KEY_ORIGIN, deals.get(i).getOrigin());
				deal.put(KEY_DEST, deals.get(i).getDest());
				deal.put(KEY_DEPARTDATE, deals.get(i).getDepartDate());
				deal.put(KEY_RETURNDATE, deals.get(i).getReturnDate());
				deal.put(KEY_AIRLINE, deals.get(i).getAirline());
				
				deals_list.add(deal);
			}
		}
		
		SimpleAdapter mDealsList = new SimpleAdapter(this, deals_list, R.layout.dealrow,
		            new String[] {KEY_PPM, KEY_ORIGIN, KEY_DEST, KEY_DEPARTDATE, KEY_RETURNDATE, KEY_AIRLINE},
		            new int[] {R.id.PPM, R.id.origin, R.id.dest, R.id.depart_date, R.id.return_date, R.id.airline});
		deals_lv.setAdapter(mDealsList);
		
		deals_lv.setTextFilterEnabled(true);

	}
	
	/**
	 * Return to the main screen
	 * 
	 * @param buttonID The done-button's ID
	 */
	private void setDoneListener(int buttonID) {
		final Button doneButton = (Button) findViewById(buttonID);
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}

		});
	}

	@Override
	public void onDestroy() {
    super.onDestroy();
 
    mDbHelper.close();
	}

	private void updatePPM(ArrayList<KayakDeal> deals) {
    mDbHelper = new AirportDBAdapter(this);
    mDbHelper.open();

    int length = deals.size();
    for (int i=0; i<length; ++i) {
    	String origin = deals.get(i).getOrigin();
    	String dest = deals.get(i).getDest();
    	
    	Cursor mCursor = mDbHelper.fetchDistance(origin, dest);
    	startManagingCursor(mCursor);

    	int distance = -1;
    	if (mCursor!=null && mCursor.getCount()!=0) {
    		// Exact one record if found
    		distance = mCursor.getInt(mCursor.getColumnIndex(AirportDBAdapter.KEY_DISTANCE));
    	}
    	
    	if (distance == -1) {
    		// Get distance from AirportScraper and save to database
    		distance = AirportScraper.lookupDistance(new String[] {origin, dest});
    		if (distance != -1) {
    			mDbHelper.createDistance(origin, dest, distance);
    		} else {
    			System.out.println("Error: Cannot get distance from database or scraper!");
    		}
    	}

    	deals.get(i).setDistance(distance);
    	deals.get(i).setPPM(deals.get(i).getPrice() / distance);
    }
	}

}
