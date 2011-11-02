package net.homeip.jtjang.MileageRunAppEngine;

import java.net.*;
import java.io.*;

public class AirportScraper {

  private static String src  = "AUS";
  private static String dest = "JFK";

  public static int extractDistance(String rawText ) {
    // URL open returns html with distance in this format:
    // ... </b> kilometres (<b> 1527</b > miles)</p> ...
    String anchor1 = " kilometres (<b>";
    String anchor2 = "</b > miles)</p>";
    int offset = rawText.indexOf( anchor1 ) + anchor1.length();
    int end = rawText.indexOf( anchor2 );
    if (offset<0 || end<0) {
    	return -1;
    }
    String distanceString = rawText.substring( offset, end );
    int result = new Integer( distanceString );
    return result;
  }

  public static void main( String [] args ) {
    // int distance = extractDistance( AirportFeedMock.rawText() );
    int distance = lookupDistance( args );
    System.out.println("Distance from " + src + " to " 
        + dest + " is " + distance + " miles");
  }

  public static int lookupDistance( String [] args ) {
    try {
      String airportUrl = "http://www.world-airport-codes.com/dist/?";
      if ( args.length == 2 ) {
        src = normalizeAirportCode(args[0]);
        dest = normalizeAirportCode(args[1]);
      } 
      String completeUrl = 
          airportUrl + "a1=" + src + "&" + "a2=" + dest;
      URL kUrl = new URL( completeUrl );
      StringBuilder sb = new StringBuilder();
      String str = null;
      BufferedReader in = 
          new BufferedReader(new InputStreamReader(kUrl.openStream()));
      while ((str = in.readLine()) != null) {
        sb.append( str );
      }
      in.close();
      return extractDistance( sb.toString() );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }
  
  private static final String normalizeAirportCode(String code) {
//  	if (code.equals("NYC")) {
//  		return new String("JFK");
//  	} else {
//  		return code;
//  	}
  	return code.toUpperCase();
  }
}
