package net.homeip.jtjang.MileageRun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class KayakScraper {

	public static void main(String args[]) throws Exception {

		String kayakFeedUrlName = 
			"http://www.kayak.com/h/rss/buzz?code=nyc&tm=201112";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(kayakFeedUrlName);

		// AA: The Feed class mimics a stream from a string
		// 
		// AA: I use it for debugging: it avoids hitting
		// AA: the Kayak server, resulting in faster processing
		// AA: and reduces the chances of Kayay thinking
		// AA: I'm launching a denial-of-service attack.
		// 
		// AA: In production I would use the line above
		// AA: that's commented out.
		//		Document doc = builder.parse( KayakMockFeed.rawStream() );
		//
		Element root = doc.getDocumentElement();
		String rootName = root.getTagName();
		System.out.println("rootName = " + rootName );
		recursivelyTraverse( root );
		getKayakDeals("AUS", "201112");
	}


	public static ArrayList<KayakDeal> getKayakDeals(String departCode, String yearMonth)
	throws ParserConfigurationException, SAXException, IOException {
		//		String kayakFeedUrlName = 
		//			"http://www.kayak.com/h/rss/buzz?code=nyc&tm=201112";
		String kayakFeedUrlName = 
			"http://www.kayak.com/h/rss/buzz?code=" + departCode.toLowerCase() + "&tm=" + yearMonth;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(kayakFeedUrlName);
		//		Document doc = builder.parse( KayakMockFeed.rawStream() );

		Element root = doc.getDocumentElement();
		//		String rootName = root.getTagName();
		//		System.out.println("rootName = " + rootName );
		//		KayakDeal[] deals = recursivelyTraverse( root );
		ArrayList<KayakDeal> deals = parseDeals(root);

		return deals;
	}


	private static ArrayList<KayakDeal> parseDeals(Node root) {
		ArrayList<KayakDeal> deals = new ArrayList<KayakDeal>();
		if (root instanceof Element) {
			Element rootElement = (Element) root;
			NodeList dealNodes = rootElement.getElementsByTagName("item");
			for (int i=0; i<dealNodes.getLength(); i++) {
				KayakDeal deal = new KayakDeal();
				deal.setOrigin(getOrigin(dealNodes.item(i)));
				deal.setDest(getDest(dealNodes.item(i)));
				deal.setPrice(getPrice(dealNodes.item(i)));
				deal.setDepartDate(getDepartDate(dealNodes.item(i)));
				deal.setReturnDate(getReturnDate(dealNodes.item(i)));
				deal.setAirline(getAirline(dealNodes.item(i)));
				deal.print();
				deals.add(deal);
			}
		}

		return deals;
	}

	private static String getOrigin(Node dealNode) {
		if (dealNode instanceof Element) {
			Element dealElement = (Element) dealNode;
			Node originNode = dealElement.getElementsByTagName("kyk:originCode").item(0);
			Element originElement = (Element) originNode;
			Text textNode = (Text) originElement.getFirstChild();
			return textNode.getData().trim();
		}

		return null;
	}

	private static String getDest(Node dealNode) {
		if (dealNode instanceof Element) {
			Element dealElement = (Element) dealNode;
			Node originNode = dealElement.getElementsByTagName("kyk:destCode").item(0);
			Element originElement = (Element) originNode;
			Text textNode = (Text) originElement.getFirstChild();
			return textNode.getData().trim();
		}

		return null;
	}

	private static double getPrice(Node dealNode) {
		if (dealNode instanceof Element) {
			Element dealElement = (Element) dealNode;
			Node originNode = dealElement.getElementsByTagName("kyk:price").item(0);
			Element originElement = (Element) originNode;
			Text textNode = (Text) originElement.getFirstChild();
			return Double.valueOf(textNode.getData().trim());
		}

		return 0.0;
	}

	private static String getDepartDate(Node dealNode) {
		if (dealNode instanceof Element) {
			Element dealElement = (Element) dealNode;
			Node originNode = dealElement.getElementsByTagName("kyk:departDate").item(0);
			Element originElement = (Element) originNode;
			Text textNode = (Text) originElement.getFirstChild();
			String departDate = textNode.getData().trim();
			departDate = departDate.substring(0, departDate.length()-2) + "20" +
			departDate.substring(departDate.length()-2);
			return departDate;
		}

		return null;
	}

	private static String getReturnDate(Node dealNode) {
		if (dealNode instanceof Element) {
			Element dealElement = (Element) dealNode;
			Node originNode = dealElement.getElementsByTagName("kyk:returnDate").item(0);
			Element originElement = (Element) originNode;
			Text textNode = (Text) originElement.getFirstChild();
			String returnDate = textNode.getData().trim();
			returnDate = returnDate.substring(0, returnDate.length()-2) + "20" +
			returnDate.substring(returnDate.length()-2);
			return returnDate;
		}

		return null;
	}

	private static String getAirline(Node dealNode) {
		if (dealNode instanceof Element) {
			Element dealElement = (Element) dealNode;
			Node originNode = dealElement.getElementsByTagName("kyk:airline").item(0);
			Element originElement = (Element) originNode;
			Text textNode = (Text) originElement.getFirstChild();
			String airline = textNode.getData().trim();
			return airlineCode(airline);
		}

		return null;
	}

	/**
	 * Convert airline name from Kayak to code names using
	 * http://en.wikipedia.org/wiki/Airline_codes-All
	 */
	private static final String airlineCode(String airline) {
		final HashMap<String, String> codes = new HashMap<String, String>();
		codes.put("American Airlines", "AA");
		codes.put("US Airways", "US");
		codes.put("Spirit Airlines", "NK");
		codes.put("Continental", "CO");
		codes.put("Delta", "DL");
		codes.put("Multiple Airlines", "MA");
		codes.put("United", "UA");
		codes.put("JetBlue Airways", "B6");
		codes.put("Frontier", "F9");
		codes.put("Sun Country Air", "SY");
		codes.put("Aeromexico", "AM");
		codes.put("AirTran", "FL");
		codes.put("Virgin America", "VX");
		codes.put("Air Canada", "AC");
		codes.put("Iberia", "IB");
		codes.put("Virgin Atlantic", "VS");
		codes.put("Copa Airlines", "CM");
		codes.put("Caribbean Airlines", "BW");
		codes.put("Alaska Airlines", "AS");
		codes.put("China Southern", "CZ");
		codes.put("Jetstar Asia", "3K");
		codes.put("KLM Royal Dutch", "KL");
		codes.put("Malaysia Airlines", "MH");
		codes.put("Korean Air", "KE");
		codes.put("Philippine Air", "PR");
		codes.put("China Eastern Air", "MU");
		codes.put("EVA Air", "BR");
		codes.put("Jetstar", "JQ");
		codes.put("China Airlines", "CI");
		codes.put("Cathay Pacific", "CX");
		codes.put("Japan Airlines", "JL");
		codes.put("Hong Kong Airlines", "HX");
		codes.put("All Nippon", "NH");
		
		if (codes.containsKey(airline)) {
			return codes.get(airline);
		} else {
			return airline;
		}
	}


	public static KayakDeal[] recursivelyTraverse( Node aNode ) {
		if ( aNode instanceof Element) {
			Element aNodeElement = (Element) aNode;
			Text textNode = (Text) aNodeElement.getFirstChild();
			String text = textNode.getData().trim();
			System.out.println("tagName = " + aNodeElement.getTagName() );
			System.out.println("text = " + text );
			NodeList children = aNode.getChildNodes();
			for ( int i = 0; i < children.getLength(); i++ ) {
				recursivelyTraverse( children.item(i) );
			}
		}

		return null;
	}


}

