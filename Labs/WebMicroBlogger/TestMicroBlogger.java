package utweeter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TestMicroBlogger {

	public static final Class<ClientMessage> CLASSUNDERTEST = ClientMessage.class;

	public static int score = 0;

	public static int STARTPORT = 16666;
	public static int portOffset = 0;

	// added
	public static String siteURL = "http://localhost:8888/utweeter";
	public static String queryURL = "?arg=";

	@Before
	public void setUp() {
		// System.out.println("Setting up");
	}

	// Ang's suggestion on getting annotation values
	public static String getClassAnnotationValue(
			Class<ClientMessage> classType, Class<Author> annotationType,
			String attributeName) {
		String value = null;
		Annotation annotation = classType.getAnnotation(annotationType);
		if (annotation != null) {
			try {
				value = (String) annotation.annotationType()
						.getMethod(attributeName).invoke(annotation);
			} catch (Exception ex) {
				System.out.println("Failed loading class annotations");
			}
		}
		return value;
	}

	@AfterClass
	public static void oneTimeTearDown() {
		String name = getClassAnnotationValue(CLASSUNDERTEST, Author.class,
				"name");
		String uteid = getClassAnnotationValue(CLASSUNDERTEST, Author.class,
				"uteid");
		System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
	}

	// @Test(timeout = 2000)
	public void testSerDeser1() {
		cleanDataStore();
		ClientMessage cm = new ClientMessage();
		cm.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz1968")
				.setSubject("Hello World").setBody("My first posting!");
		String cmJson = cm.toJson();
		ClientMessage cmobj = ClientMessage.fromJson(cmJson);
		assertEquals(cm, cmobj);
		score += 5;
	}

	static ServerMessage doTxRx(ClientMessage... cms) {
		List<ClientMessage> cmArray = new ArrayList<ClientMessage>();
		for (ClientMessage cm : cms) {
			cmArray.add(cm);
		}
		ServerMessage result = doTxRx(cmArray);
		return result;
	}

	/**
	 * Send Query
	 * 
	 * @param cmArray
	 * @return ServerMessage result
	 */
	static ServerMessage doTxRx(List<ClientMessage> cmArray) {
		ServerMessage result = null;
		BufferedReader in = null;
		try {
			URL kUrl = new URL(siteURL + queryURL);
			for (ClientMessage cm : cmArray) {
				String cm_json = cm.toJson();
				kUrl = new URL(siteURL + queryURL
						+ URLEncoder.encode(cm_json, "utf-8"));
				HttpURLConnection connection = (HttpURLConnection) kUrl
						.openConnection();
				connection.setDoOutput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-Type", "text/plain");
				connection.setRequestProperty("charset", "utf-8");
				connection.connect();

				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				// write parameters
				writer.write(cm_json);
				writer.flush();

				in = new BufferedReader(
						new InputStreamReader(kUrl.openStream()));
			}
			StringBuilder sb = new StringBuilder();

			String str = null;
			if (in != null) {
				while ( (str = in.readLine()) != null) {
					sb.append(str);
				}
				in.close();

				String sm_json = sb.toString().trim();
				result = ServerMessage.fromJson(sm_json);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private void cleanDataStore() {
		// get all posting in dataStore
		ClientMessage cmBodyQuery = new ClientMessage()
				.setType(ClientMessage.Type.QUERY);
		ServerMessage result = doTxRx(cmBodyQuery);

		ArrayList<ClientMessage> cmList = new ArrayList<ClientMessage>();
		for (Posting p : result.getPostings()) {
			ClientMessage cm = new ClientMessage().setType(
					ClientMessage.Type.DELETE).setId(p.getId());
			cmList.add(cm);
		}
		doTxRx(cmList);
	}

	@Test
	// (timeout = 2000)
	public void testAdd1() {
		cleanDataStore();
		ClientMessage cm = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz1968")
				.setSubject("Hello World").setBody("My first posting!");
		ServerMessage result = doTxRx(cm);
		assertEquals(result.getId(), 0);
		score += 5;
	}

	@Test
	// (timeout = 2000)
	public void testAdd2() {
		cleanDataStore();
		ClientMessage cm = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz1968")
				.setSubject("Hello World").setBody("My first posting!");
		ServerMessage result = doTxRx(cm, cm, cm);

		assertEquals(result.getId(), 2);
		score += 5;
	}

	@Test
	// (timeout = 5000)
	public void testQuery1() {
		cleanDataStore();
		ClientMessage cmAdd1 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz1968")
				.setSubject("Hello World").setBody("My first posting!");

		ClientMessage cmAdd2 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("Don Bradman")
				.setSubject("29").setBody("Still the greatest!");

		ClientMessage cmAdd3 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz1968")
				.setSubject("Random musings").setBody("My second posting!");

		ClientMessage cmQuery = new ClientMessage().setType(
				ClientMessage.Type.QUERY).setAuthor("AdnanAziz1968");

		ServerMessage result = doTxRx(cmAdd1, cmAdd2, cmAdd3, cmQuery);

		assertEquals(result.getPostings().size(), 2);
		for (Posting p : result.getPostings()) {
			assert (p.getAuthor().equals(cmQuery.getAuthor()));
		}

		ClientMessage cmBodyQuery = new ClientMessage().setType(
				ClientMessage.Type.QUERY).setBody("posting My");
		result = doTxRx(cmAdd1, cmAdd2, cmAdd3, cmBodyQuery);

		assertEquals(2, result.getPostings().size());
		for (Posting p : result.getPostings()) {
			assertTrue(p.getBody().contains("My"));
			assertTrue(p.getBody().contains("posting"));
		}
		score += 15;
	}

	@Test
	// (timeout = 1000)
	public void testTimeRangeQuery1() {
		cleanDataStore();
		ClientMessage cm1 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE)
				.setDate("00:00:00 1/1/2000").setAuthor("AdnanAziz1968")
				.setSubject("Hello World").setBody("My first posting!");
		ClientMessage cm2 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE)
				.setDate("00:00:00 2/1/2000").setAuthor("AdnanAziz1968")
				.setSubject("Hello Cruel World").setBody("My second posting!");
		ClientMessage cm3 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE)
				.setDate("00:00:00 2/2/2000").setAuthor("AdnanAziz1968")
				.setSubject("Goodbye Wonderful World")
				.setBody("My third posting!");
		ClientMessage cm4 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE)
				.setDate("00:00:00 2/3/2000").setAuthor("LisaHua")
				.setSubject("Goodbye Cruel World")
				.setBody("My fourth posting!");
		ClientMessage cm5 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE)
				.setDate("00:00:00 3/5/2000").setAuthor("AdnanAziz1968")
				.setSubject("Hello World").setBody("My fifth posting!");
		ClientMessage cmTimeQuery = new ClientMessage()
				.setType(ClientMessage.Type.QUERY)
				.setDateStart("00:00:00 1/2/2000")
				.setDateEnd("00:00:00 2/28/2000");
		ServerMessage result = doTxRx(cm1, cm2, cm3, cm4, cm5, cmTimeQuery);
		assertEquals(3, result.getPostings().size());
		assertEquals(3, result.getPostings().get(0).getId());
		assertEquals("My third posting!", result.getPostings().get(1).getBody());
		assertEquals("Hello Cruel World", result.getPostings().get(2)
				.getSubject());

		ClientMessage cmTimeAndSubjectQuery = new ClientMessage()
				.setType(ClientMessage.Type.QUERY)
				.setDateStart("00:00:00 1/2/2000")
				.setDateEnd("00:00:00 2/28/2000").setSubject("Cruel");

		result = doTxRx(cm1, cm2, cm3, cm4, cm5, cmTimeAndSubjectQuery);
		assertEquals(2, result.getPostings().size());
		assertEquals("LisaHua", result.getPostings().get(0).getAuthor());
		assertEquals(1, result.getPostings().get(1).getId());
	}

	@Test
	// (timeout = 5000)
	public void testStress1() {
		cleanDataStore();
		List<ClientMessage> cmArray = new ArrayList<ClientMessage>();
		int N = 10;
		for (int i = 0; i < N; i++) {
			cmArray.add(new ClientMessage().setType(ClientMessage.Type.CREATE)
					.setAuthor("AdnanAziz1968").setSubject("Stressing...")
					.setBody("Hello World for the " + i + "-th time"));
		}
		ServerMessage result = doTxRx(cmArray);

		assertEquals(result.getId(), N - 1);
		score += 5;
	}

	@Test
	// (timeout = 2000)
	public void testDistanceQuery1() {
		cleanDataStore();
		List<ClientMessage> cmArray = new ArrayList<ClientMessage>();
		int N = 100;
		for (int i = 0; i < N; i++) {
			cmArray.add(new ClientMessage().setType(ClientMessage.Type.CREATE)
					.setAuthor("AdnanAziz1968").setSubject("Distancing...")
					.setBody("Coordinates of " + i + "," + i + "-th time")
					.setLatitude((double) i).setLongitude((double) i));
		}
		cmArray.add(new ClientMessage().setType(ClientMessage.Type.QUERY)
				.setLatitude(50.0d).setLongitude(50.0d).setDistance(2.0d));
		ServerMessage result = doTxRx(cmArray);
		assertEquals(3, result.getPostings().size());
		score += 10;
	}

	@Test
	// (timeout = 2000)
	public void testUpdate1() {
		cleanDataStore();
		ClientMessage cm1 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz1968")
				.setSubject("First");
		ClientMessage cm2 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz1968")
				.setSubject("Second");
		ClientMessage cm3 = new ClientMessage()
				.setType(ClientMessage.Type.UPDATE).setId(0)
				.setSubject("New First");
		ClientMessage cm4 = new ClientMessage().setType(
				ClientMessage.Type.QUERYBYID).setId(0);
		ServerMessage result = doTxRx(cm1, cm2, cm3, cm4);
		assertEquals("New First", result.getPostings().get(0).getSubject());
		score += 10;
	}

	@Test
	// (timeout = 2000)
	public void testLikes1() {
		cleanDataStore();
		ClientMessage cm1 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz")
				.setSubject("First");
		ClientMessage cm2 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AngLi")
				.setSubject("Second");
		ClientMessage cm3 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("LisaHua")
				.setSubject("Third");
		ClientMessage cm4 = new ClientMessage()
				.setType(ClientMessage.Type.LIKE).setId(2);
		ServerMessage result = doTxRx(cm1, cm2, cm3, cm4);
		assertEquals(1, result.getPostings().get(0).getAuthorLikes());
		score += 10;
	}

	@Test
	// (timeout = 2000)
	public void testUpvotesAndTrending1() {
		cleanDataStore();
		ClientMessage cm1 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz")
				.setSubject("First");
		ClientMessage cm2 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AngLi")
				.setSubject("Second");
		ClientMessage cm3 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("LisaHua")
				.setSubject("Third");
		ClientMessage cm4 = new ClientMessage().setType(
				ClientMessage.Type.UPVOTE).setId(2);
		ClientMessage cm5 = new ClientMessage().setType(
				ClientMessage.Type.UPVOTE).setId(1);
		ClientMessage cm6 = new ClientMessage().setType(
				ClientMessage.Type.UPVOTE).setId(2);
		ServerMessage result = doTxRx(cm1, cm2, cm3, cm4, cm5, cm6);
		// postings are ordered by decreasing posting time
		assertEquals(2, result.getPostings().get(0).getUpvotes());
		assertEquals(1, result.getPostings().get(1).getUpvotes());
		assertEquals(0, result.getPostings().get(2).getUpvotes());
		ClientMessage cm7 = new ClientMessage()
				.setType(ClientMessage.Type.TRENDING);
		score += 10;

		result = doTxRx(cm1, cm2, cm3, cm4, cm5, cm6, cm7);
		assertEquals("LisaHua", result.getPostings().get(0).getAuthor());
		assertEquals("AngLi", result.getPostings().get(1).getAuthor());

		score += 10;
	}

	@Test
	// (timeout = 2000)
	public void testDelete1() {
		cleanDataStore();
		ClientMessage cm1 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("LisaHua")
				.setSubject("First");
		ClientMessage cm2 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz")
				.setSubject("Second");
		ClientMessage cm3 = new ClientMessage()
				.setType(ClientMessage.Type.CREATE).setAuthor("AngLi")
				.setSubject("Third");
		ClientMessage cm4 = new ClientMessage().setType(
				ClientMessage.Type.DELETE).setId(1);

		ServerMessage result = doTxRx(cm1, cm2, cm3, cm4);
		assertEquals("AngLi", result.getPostings().get(0).getAuthor());
		assertEquals("First", result.getPostings().get(1).getSubject());
	}

	
	@Test//(timeout = 2000)
    public void testPagination1() {
        cleanDataStore();
        ClientMessage cm1 = new ClientMessage()
                .setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz")
                .setSubject("First");
        ClientMessage cm2 = new ClientMessage()
                .setType(ClientMessage.Type.CREATE).setAuthor("AngLi")
                .setSubject("Second").setBody("Random body");
        ClientMessage cm3 = new ClientMessage()
                .setType(ClientMessage.Type.CREATE).setAuthor("LisaHua")
                .setSubject("Third");
        ClientMessage cm4 = new ClientMessage()
                .setType(ClientMessage.Type.CREATE).setAuthor("AdnanAziz")
                .setSubject("Fourth").setBody("Random body");

        ClientMessage cmQuery = new ClientMessage().setType(
                ClientMessage.Type.QUERY).setPageSize(2);
        ServerMessage result = doTxRx(cm1, cm2, cm3, cmQuery);

        assertEquals(2, result.getPostings().size());
        assertEquals("LisaHua", result.getPostings().get(0).getAuthor());
        assertEquals("Second", result.getPostings().get(1).getSubject());
        score += 5;

        cmQuery = new ClientMessage().setType(ClientMessage.Type.QUERY)
                .setPageSize(2).setPageOffset(1);

        result = doTxRx(cmQuery);
        assertEquals(2, result.getPostings().size());
        assertEquals("Second", result.getPostings().get(0).getSubject());
        assertEquals("AdnanAziz", result.getPostings().get(1).getAuthor());
        score += 5;

        cmQuery = new ClientMessage().setType(ClientMessage.Type.QUERY)
                .setPageSize(2).setAuthor("AdnanAziz").setPageOffset(1);

        result = doTxRx(cm4, cmQuery);
        assertEquals(1, result.getPostings().size());
        assertEquals("First", result.getPostings().get(0).getSubject());
        score += 5;
    }
}