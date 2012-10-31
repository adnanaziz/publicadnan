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

   @Test(timeout=2000) 
  public void testAdd1() {
    ClientMessage cm = new ClientMessage();
    cm.setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World")
                               .setBody("My first posting!");

    MicroBlogServer ms = null;
    MicroBlogClient mc = null;
    try {
      ms = new MicroBlogServer();
      ms.start(STARTPORT + portOffset++);
      mc = new MicroBlogClient( cm );
      mc.transmit();
      ms.stop();
      score += 5;
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
    assertEquals( mc.result.getId(), 0 );
    score += 5;
  }

   @Test(timeout=2000) 
  public void testAdd2() {
    ClientMessage cm = new ClientMessage();
    cm.setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World")
                               .setBody("My first posting!");

    MicroBlogServer ms = null;
    MicroBlogClient mc = null;
    try {
      ms = new MicroBlogServer();
      ms.start(STARTPORT + portOffset++);
      mc = new MicroBlogClient( cm );
      mc.transmit();
      mc.transmit();
      mc.transmit();
      ms.stop();
      score += 5;
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
    assertEquals( mc.result.getId(), 2 );
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

    MicroBlogServer ms = null;
    MicroBlogClient mc1, mc2, mc3, mc4;
    mc1 = mc2 = mc3 = mc4 = null;
    try {
      ms = new MicroBlogServer();
      ms.start(STARTPORT + portOffset++);

      mc1 = new MicroBlogClient( cmAdd1 );
      mc1.transmit();
      mc2 = new MicroBlogClient( cmAdd2 );
      mc2.transmit();
      mc3 = new MicroBlogClient( cmAdd3 );
      mc3.transmit();
      System.out.println("cmQuery = " + cmQuery );
      mc4 = new MicroBlogClient( cmQuery );
      mc4.transmit();
      ms.stop();
      score += 5;
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
    System.out.println("mc4.result = " + mc4.result);
    assertEquals( mc4.result.getPostings().size(), 2 );
    for ( Posting p : mc4.result.getPostings() ) {
      assert( p.getAuthor().equals( cmQuery.getAuthor() ) );
    }
    score += 15;
  }

  @Test(timeout=10000) 
  public void testStress1() {
    ClientMessage cm = new ClientMessage();
    cm.setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World");

    ClientMessage cmQuery = new ClientMessage()
                                .setType(ClientMessage.Type.QUERY)
                                .setAuthor("AdnanAziz1968");

    MicroBlogServer ms = null;
    MicroBlogClient mc = null;
    try {
      ms = new MicroBlogServer();
      ms.start(STARTPORT + portOffset++);
      for ( int i = 0 ; i < 1000; i++ ) {
        mc = new MicroBlogClient( cm.setBody("Msg id: "  + i ));
        mc.transmit();
      }
      mc = new MicroBlogClient( cmQuery );
      mc.transmit();
      ms.stop();
      score += 5;
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
    assertEquals( 1000, mc.result.getPostings().size() );
    score += 5;
  }

  @Test(timeout=10000) 
  public void testDistance1() {
    ClientMessage cm = new ClientMessage();
    cm.setType(ClientMessage.Type.CREATE)
                               .setAuthor("AdnanAziz1968")
                               .setSubject("Hello World");

    ClientMessage cmQuery = new ClientMessage()
                                .setType(ClientMessage.Type.QUERY)
                                .setLongitude(50.0)
				.setLatitude(50.0)
				.setDistance(1);

    MicroBlogServer ms = null;
    MicroBlogClient mc = null;
    try {
      ms = new MicroBlogServer();
      ms.start(STARTPORT + portOffset++);
      for ( int i = 0 ; i < 100; i++ ) {
        mc = new MicroBlogClient( cm.setLatitude((double) i).setLongitude(( double) i) );
        mc.transmit();
      }
      mc = new MicroBlogClient( cmQuery );
      mc.transmit();
      ms.stop();
      score += 5;
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
    assertEquals( 3, mc.result.getPostings().size() );
    score += 5;
  }

}
