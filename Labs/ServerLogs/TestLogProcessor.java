import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.lang.annotation.*;


import ee422C.ReferenceLogProcessor;

public class TestLogProcessor {

  public static final Class<LogProcessor> CLASSUNDERTEST = LogProcessor.class;

  public static int score = 0;

  @Before
  public void setUp() {
    // System.out.println("Setting up");
  }

  // Ang's suggestion on getting annotation values
  public static String getClassAnnotationValue(Class<LogProcessor> classType, 
          Class<Author> annotationType, 
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
  public void testBasicNoDups1() {
    LogProcessor lp = new LogProcessor(5);
    lp.add( "a.com", 0  );
    lp.add( "b.com", 1  );
    lp.add( "c.com", 2  );
    assertTrue( 3 == lp.getWindowSize());
    score += 5;
  }

  @Test(timeout=2000) 
  public void testBasicNoDups2() {
    LogProcessor lp = new LogProcessor(5);
    lp.add( "a.com", 0  );
    lp.add( "b.com", 1 );
    lp.add( "c.com", 2 );
    lp.add( "d.com", 20 );
    lp.add( "e.com", 22 );
    assertTrue( 2 == lp.getWindowSize());
    score += 5;
  }

  @Test(timeout=2000) 
  public void testBasicDups1() {
    LogProcessor lp = new LogProcessor(5);
    lp.add( "a.com", 0 );
    lp.add( "b.com", 1 );
    lp.add( "a.com", 2 );
    assertTrue( 3 == lp.getWindowSize());
    score += 5;
  }

  @Test(timeout=2000) 
  public void testBasicDups2() {
    LogProcessor lp = new LogProcessor(10);
    lp.add( "a.com", 0 );
    lp.add( "b.com", 1 );
    lp.add( "a.com", 2 );
    lp.add( "a.com", 3 );
    lp.add( "b.com", 4 );
    lp.add( "c.com", 5 );
    assertTrue( 6 == lp.getWindowSize());
    score += 5;
    List<String> rlpWindow = new ArrayList<String>();
    rlpWindow.add("a"); // most common
    rlpWindow.add("b");
    rlpWindow.add("c"); // least common
    List<String> lpWindow = lp.getOrderedUrlsInWindow();
    Iterator<String> rlpWindowIterator = rlpWindow.iterator();
    Iterator<String> lpWindowIterator = lpWindow.iterator();
    while ( !rlpWindowIterator.hasNext() ) {
      assertTrue( !lpWindowIterator.hasNext() );
      assertTrue( rlpWindowIterator.next().equals( lpWindowIterator.next() ) );
    }
    score +=5;
  }

  @Test(timeout=2000) 
  public void testBasicDups3() {
    LogProcessor lp = new LogProcessor(10);
    lp.add( "a.com", 0 );
    lp.add( "b.com", 1 );
    lp.add( "b.com", 2 );
    lp.add( "a.com", 3 );
    lp.add( "b.com", 4 );
    lp.add( "c.com", 5 );
    lp.add( "a.com", 1 );
    List<String> rlpWindow = new ArrayList<String>();
    rlpWindow.add("a"); // a and b are most common, break times lexico
    rlpWindow.add("b");
    rlpWindow.add("c"); // least common
    List<String> lpWindow = lp.getOrderedUrlsInWindow();
    Iterator<String> rlpWindowIterator = rlpWindow.iterator();
    Iterator<String> lpWindowIterator = lpWindow.iterator();
    while ( !rlpWindowIterator.hasNext() ) {
      assertTrue( !lpWindowIterator.hasNext() );
      assertTrue( rlpWindowIterator.next().equals( lpWindowIterator.next() ) );
    }
    score +=5;
  }



  @Test(timeout=2000) 
  public void testOutOfOrderNoDupTimes1() {
    LogProcessor lp = new LogProcessor(8);
    lp.add( "a.com", 0 );
    lp.add( "b.com", 2 );
    lp.add( "a.com", 3 );
    lp.add( "a.com", 4 );
    lp.add( "b.com", 5 );
    lp.add( "a.com", 1 );
    assertTrue( 6 == lp.getWindowSize());
    score += 5;
  }

  @Test(timeout=2000) 
  public void testOutOfOrderDupTimes1() {
    LogProcessor lp = new LogProcessor(2);
    lp.add( "a.com", 0 );
    lp.add( "b.com", 1 );
    lp.add( "a.com", 3 );
    lp.add( "a.com", 4 );
    lp.add( "b.com", 4 );
    lp.add( "a.com", 3 );
    assertTrue( 4 == lp.getWindowSize());
    score += 5;
  }


  private static String[] sites = {
    "google.com", "google.com", "yahoo.com", "facebook.com",
    "cnn.com", "cricinfo.org", "rediff.com", "abcnews.com"
  };


  @Test(timeout=1000) 
  public void testStressSmall() {
    stressTest( 1000, 10, 5 );
  }

  @Test(timeout=10000) 
  public void testStressMedium() {
    stressTest( 100000, 1000, 5 );
  }

  @Ignore @Test(timeout=500000) 
  public void testStressLarge() {
    stressTest( 100000000, 10000, 20 );
  }

  public static void stressTest( int numTrials, int windowWidth, int pointsToAdd ) {
    Random r;
    int timeStamp;

    timeStamp = 0;
    r = new Random(0);
    ReferenceLogProcessor rlp = new ReferenceLogProcessor(windowWidth);
    long startTimeReference = System.nanoTime();
    for ( int i = 0 ; i < numTrials; i++ ) {
      rlp.add( sites[ r.nextInt( sites.length )], timeStamp );
      timeStamp += r.nextInt(windowWidth);
    }
    long finishTimeReference = System.nanoTime(); 

    timeStamp = 0;
    r = new Random(0);
    long startTime = System.nanoTime();
    LogProcessor lp = new LogProcessor(windowWidth);
    for ( int i = 0 ; i < numTrials; i++ ) {
      lp.add( sites[ r.nextInt( sites.length )], timeStamp );
      timeStamp += r.nextInt(windowWidth);
    }
    long finishTime = System.nanoTime(); 
    assert( lp.getWindowSize() == rlp.getWindowSize() );
    List<String> rlpWindow = rlp.getOrderedUrlsInWindow();
    List<String> lpWindow = lp.getOrderedUrlsInWindow();
    Iterator<String> rlpWindowIterator = rlpWindow.iterator();
    Iterator<String> lpWindowIterator = lpWindow.iterator();
    while ( !rlpWindowIterator.hasNext() ) {
      assertTrue( !lpWindowIterator.hasNext() );
      assertTrue( rlpWindowIterator.next().equals( lpWindowIterator.next() ) );
    }
    score +=pointsToAdd;
    
    if ( 2*( finishTimeReference - startTimeReference) 
             > ( finishTime - startTime ) ) {
      score += 2*pointsToAdd;
    }
  }
}
