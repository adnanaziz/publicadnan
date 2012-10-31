import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestMicroBlogger {

  public static final Class CLASSUNDERTEST = ClientMessage.class;

  public static int score = 0;

  public static int STARTPORT = 16666;
  public static int portOffset = 0;

  @Before
  public void setUp() {
    // System.out.println("Setting up");
  }

  // Ang's suggestion on getting annotation values
  public static String getClassAnnotationValue(Class classType, 
                                               Class annotationType, 
                                               String attributeName) {
    String value = null;
    Annotation annotation = classType.getAnnotation(annotationType);
    if (annotation != null) {
      try {
        value = (String) annotation.annotationType().getMethod(attributeName)
                                                    .invoke(annotation);
     } catch (Exception ex) {
        System.out.println("Failed loading class annotations");
     }
   }
   return value;
  }

  @AfterClass
  public static void oneTimeTearDown() {
    String name = getClassAnnotationValue(CLASSUNDERTEST,
                                           Author.class, "name");
    String uteid = getClassAnnotationValue(CLASSUNDERTEST,
                                            Author.class, "uteid");
    System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
  }

   @Test(timeout=2000) 
  public void testSerDeser1() {
    ClientMessage cm = new ClientMessage();
    cm.setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World")
                               .setBody("My first posting!");
    String cmJson = cm.toJson();
    ClientMessage cmobj = ClientMessage.fromJson( cmJson );
    assertEquals( cm, cmobj );
    score += 5;
  }

  static ServerMessage doTxRx( ClientMessage... cms ) {
     List<ClientMessage> cmArray = new ArrayList<ClientMessage>();
     for ( ClientMessage cm : cms ) {
       cmArray.add( cm );
     }
     ServerMessage result = doTxRx( cmArray );
     return result;
  }

  static ServerMessage doTxRx( List<ClientMessage> cmArray ) {
    MicroBlogServer ms = new MicroBlogServer();
    ServerMessage result = null;
    try {
      ms.start(STARTPORT + portOffset++); 
      for ( ClientMessage cm : cmArray ) {
        MicroBlogClient mc = new MicroBlogClient( cm );
        mc.transmit();
        result = mc.result;
      }
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
    ms.stop();
    return result;
  }

   @Test(timeout=2000) 
  public void testAdd1() {
    ClientMessage cm = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World")
                               .setBody("My first posting!");
    ServerMessage result = doTxRx( cm );
    assertEquals( result.getId(), 0 );
    score += 5;
  }

   @Test(timeout=2000) 
  public void testAdd2() {
    ClientMessage cm = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World")
                               .setBody("My first posting!");
    ServerMessage result = doTxRx( cm, cm, cm );
    assertEquals( result.getId(), 2 );
    score += 5;
  }

  @Test(timeout=5000) 
  public void testQuery1() {
    ClientMessage cmAdd1 = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World")
                               .setBody("My first posting!");

    ClientMessage cmAdd2 = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setAuthor("Don Bradman")
                               .setSubject("29")
                               .setBody("Still the greatest!");

    ClientMessage cmAdd3 = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Random musings")
                               .setBody("My second posting!");

    ClientMessage cmQuery = new ClientMessage()
                                .setType(ClientMessage.Type.QUERY)
                                .setAuthor("AdnanAziz1968");

    ServerMessage result = doTxRx( cmAdd1, cmAdd2, cmAdd3, cmQuery );

    assertEquals( result.getPostings().size(), 2 );
    for ( Posting p : result.getPostings() ) {
      assert( p.getAuthor().equals( cmQuery.getAuthor() ) );
    }

    ClientMessage cmBodyQuery = new ClientMessage()
                                .setType(ClientMessage.Type.QUERY)
                                .setBody("posting My");
    assertEquals( 2, result.getPostings().size() );
    for ( Posting p : result.getPostings() ) {
      assertTrue( p.getBody().contains("My") );
      assertTrue( p.getBody().contains("posting") );
    }
    score += 15;
  }


  @Test(timeout=1000)
  public void testTimeRangeQuery1() {
    ClientMessage cm1 = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setDate(1L)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World")
                               .setBody("My first posting!");
    ClientMessage cm2 = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setDate(100L)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello Cruel World")
                               .setBody("My second posting!");
    ClientMessage cm3 = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setDate(250L)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Goodbye Wonderful World")
                               .setBody("My third posting!");
    ClientMessage cm4 = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setDate(200L)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Goodbye Cruel World")
                               .setBody("My fourth posting!");
    ClientMessage cm5 = new ClientMessage()
                               .setType(ClientMessage.Type.CREATE)
                               .setDate(1000L)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World")
                               .setBody("My fifth posting!");
    ClientMessage cmTimeQuery = new ClientMessage()
                                 .setType(ClientMessage.Type.QUERY)
                                 .setDateStart(100L)
                                 .setDateEnd(200L);
    ServerMessage result = doTxRx( cm1, cm2, cm3, cm4, cm5, cmTimeQuery );
    assertEquals( 3, result.getPostings().size());
    assertEquals( "My third posting", result.getPostings().get(0));
    assertEquals( "My middle posting", result.getPostings().get(1));
    assertEquals( "My second posting", result.getPostings().get(2));

    ClientMessage cmTimeAndSubjectQuery = new ClientMessage()
                                 .setType(ClientMessage.Type.QUERY)
                                 .setDateStart(100L)
                                 .setDateEnd(200L)
                                 .setSubject("Cruel");
    
    result = doTxRx( cm1, cm2, cm3, cm4, cm5, cmTimeAndSubjectQuery );
    assertEquals( 2, result.getPostings().size());
    assertEquals( "My third posting", result.getPostings().get(0));
    assertEquals( "My second posting", result.getPostings().get(1));
  }


  @Test(timeout=5000) 
  public void testStress1() {
    List<ClientMessage> cmArray = new ArrayList<ClientMessage>();
    int N = 1000;
    for ( int i = 0 ; i < N; i++ ) {
      cmArray.add( new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz1968")
                              .setSubject("Stressing...")
                              .setBody("Hello World for the " + i + "-th time"));
    }
    ServerMessage result = doTxRx( cmArray );

    assertEquals( result.getId(), N-1 );
    score += 5;
  }

  @Test(timeout=2000) 
  public void testDistanceQuery1() {
    List<ClientMessage> cmArray = new ArrayList<ClientMessage>();
    int N = 100;
    for ( int i = 0 ; i < N; i++ ) {
      cmArray.add( new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz1968")
                              .setSubject("Distancing...")
                              .setBody("Coordinates of " + i + "," + i + "-th time")
                              .setLatitude( (double) i )
                              .setLongitude( (double) i ) );
    }
    cmArray.add( new ClientMessage()
                              .setType(ClientMessage.Type.QUERY)
                              .setLatitude( 50.0d )
                              .setLongitude( 50.0d ) 
                              .setDistance( 2.0d ) );
    ServerMessage result = doTxRx( cmArray );
    assertEquals( 2, result.getPostings() );
    score += 10;
  }

  @Test(timeout=2000) 
  public void testUpdate1() {

    ClientMessage cm1 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz1968")
                              .setSubject("First");
    ClientMessage cm2 = new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz1968")
                              .setSubject("Second");
    ClientMessage cm3 = new ClientMessage() 
                              .setType(ClientMessage.Type.UPDATE)
                              .setId(0)
                              .setSubject("New First");
    ClientMessage cm4 = new ClientMessage()
                              .setType(ClientMessage.Type.QUERYBYID)
                              .setId( 0 );
    ServerMessage result = doTxRx( cm1, cm2, cm3, cm4 );
    assertEquals("New First", result.getPostings().get(0).getSubject() );
    score += 10;
  }

  @Test(timeout=2000) 
  public void testLikes1() {

    ClientMessage cm1 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz")
                              .setSubject("First");
    ClientMessage cm2 = new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AngLi")
                              .setSubject("Second");
    ClientMessage cm3 = new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("LisaHua")
                              .setSubject("Third");
    ClientMessage cm4 = new ClientMessage()
                              .setType(ClientMessage.Type.LIKE)
                              .setId( 0 );
    ServerMessage result = doTxRx( cm1, cm2, cm3, cm4 );
    assertEquals(1, result.getPostings().get(0).getAuthorLikes() );
    score += 10;
  }

  @Test(timeout=2000) 
  public void testUpvotesAndTrending1() {
    ClientMessage cm1 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz")
                              .setSubject("First")
                              .setTime(100);
    ClientMessage cm2 = new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AngLi")
                              .setSubject("Second")
                              .setTime(200+3600*1000);
    ClientMessage cm3 = new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("LisaHua")
                              .setSubject("Third")
                              .setTime(300+3600*1000);
    ClientMessage cm4 = new ClientMessage()
                              .setType(ClientMessage.Type.UPVOTE)
                              .setId( 2 );
    ClientMessage cm5 = new ClientMessage()
                              .setType(ClientMessage.Type.UPVOTE)
                              .setId( 1 );
    ClientMessage cm6 = new ClientMessage()
                              .setType(ClientMessage.Type.UPVOTE)
                              .setId( 2 );
    ServerMessage result = doTxRx( cm1, cm2, cm3, cm4, cm5, cm6 );
    // postings are ordered by decreasing posting time
    assertEquals(2, result.getPostings().get(0).getAuthorLikes() );
    assertEquals(1, result.getPostings().get(1).getAuthorLikes() );
    assertEquals(0, result.getPostings().get(2).getAuthorLikes() );
    ClientMessage cm7 = new ClientMessage()
                              .setType(ClientMessage.Type.TRENDING);
    score += 10;

    result = doTxRx( cm1, cm2, cm3, cm4, cm5, cm6, cm7 );
    assertEquals("LisaHua", result.getPostings().get(0).getAuthor() );
    assertEquals("AngLi", result.getPostings().get(1).getAuthor() );

    score += 10;
  }

  @Test(timeout=2000) 
  public void testDelete1() {
    ClientMessage cm1 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz")
                              .setSubject("First")
                              .setTime(100);
    ClientMessage cm2 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz")
                              .setSubject("First")
                              .setTime(200);
    ClientMessage cm3 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz")
                              .setSubject("First")
                              .setTime(300);
    ClientMessage cm4 =  new ClientMessage()
                              .setType(ClientMessage.Type.DELETE)
                              .setId(1);
    ClientMessage cmQuery = new ClientMessage()
                                .setType(ClientMessage.Type.QUERY);
    ServerMessage result = doTxRx( cm1, cm2, cm4, cmQuery );
    assertEquals( "LisaHua", result.getPostings().get(0).getAuthor() );
    assertEquals( "AdnanAziz", result.getPostings().get(1).getAuthor() );
  }

  @Test(timeout=2000)
  public void testPagination1() {
    ClientMessage cm1 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz")
                              .setSubject("First")
                              .setTime(100);
    ClientMessage cm2 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz")
                              .setSubject("Second")
                              .setSubject("Random body")
                              .setTime(200);
    ClientMessage cm3 =  new ClientMessage()
                              .setType(ClientMessage.Type.CREATE)
                              .setAuthor("AdnanAziz")
                              .setSubject("Third")
                              .setTime(300);
    ClientMessage cmQuery = new ClientMessage()
                              .setType(ClientMessage.Type.QUERY)
                              .setPageSize(2);
    ServerMessage result = doTxRx( cm1, cm2, cm2, cmQuery );

    assertEquals(2, result.getPostings().size());
    assertEquals("Third", result.getPostings().get(0).getSubject() );
    assertEquals("Second", result.getPostings().get(1).getSubject() );
    score += 5;

    cmQuery = new ClientMessage().setType(ClientMessage.Type.QUERY)
                              .setPageSize(2)
                              .setPageOffset(1);

    result = doTxRx( cm1, cm2, cm3, cmQuery );
    assertEquals(2, result.getPostings().size());
    assertEquals("Second", result.getPostings().get(0).getSubject() );
    assertEquals("First", result.getPostings().get(1).getSubject() );
    score += 5;

    cmQuery = new ClientMessage().setType(ClientMessage.Type.QUERY)
                              .setPageSize(2)
                              .setSubject("Second")
                              .setPageOffset(1);

    result = doTxRx( cm1, cm2, cm3, cmQuery );
    assertEquals(1, result.getPostings().size());
    assertEquals("Second", result.getPostings().get(0).getSubject() );
    score += 5;
  }

}
