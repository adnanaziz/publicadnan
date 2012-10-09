import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestLogProcessor {

  public static final Class CLASSUNDERTEST = LogProcessor.class;

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
  public void testBasicNoDups1() {
    LogProcessor lp = new LogProcessor(5);
    lp.add( new Record( "a.com", 0 ) );
    lp.add( new Record( "b.com", 1 ) );
    lp.add( new Record( "c.com", 2 ) );
    assertTrue( 3 == lp.windowSize());
    score += 5;
  }

  @Test(timeout=2000) 
  public void testBasicNoDups2() {
    LogProcessor lp = new LogProcessor(5);
    lp.add( new Record( "a.com", 0 ) );
    lp.add( new Record( "b.com", 1 ) );
    lp.add( new Record( "c.com", 2 ) );
    lp.add( new Record( "d.com", 20 ) );
    lp.add( new Record( "e.com", 22 ) );
    assertTrue( 2 == lp.windowSize());
    score += 5;

  @Test(timeout=2000) 
  public void testBasicDups1() {
    LogProcessor lp = new LogProcessor(5);
    lp.add( new Record( "a.com", 0 ) );
    lp.add( new Record( "b.com", 1 ) );
    lp.add( new Record( "a.com", 2 ) );
    assertTrue( 3 == lp.windowSize());
    score += 5;
  }

  @Test(timeout=2000) 
  public void testBasicDups2() {
    LogProcessor lp = new LogProcessor(10);
    lp.add( new Record( "a.com", 0 ) );
    lp.add( new Record( "b.com", 1 ) );
    lp.add( new Record( "a.com", 2 ) );
    lp.add( new Record( "a.com", 3 ) );
    lp.add( new Record( "b.com", 4 ) );
    lp.add( new Record( "c.com", 5 ) );
    assertTrue( 6 == lp.windowSize());
    score += 5;
  }

  @Test(timeout=2000) 
  public void testOutOfOrderNoDupTimes1() {
    LogProcessor lp = new LogProcessor(8);
    lp.add( new Record( "a.com", 0 ) );
    lp.add( new Record( "b.com", 2 ) );
    lp.add( new Record( "a.com", 3 ) );
    lp.add( new Record( "a.com", 4 ) );
    lp.add( new Record( "b.com", 5 ) );
    lp.add( new Record( "a.com", 1 ) );
    assertTrue( 6 == lp.windowSize());
    score += 5;
  }

  @Test(timeout=2000) 
  public void testOutOfOrderDupTimes1() {
    LogProcessor lp = new LogProcessor(2);
    lp.add( new Record( "a.com", 0 ) );
    lp.add( new Record( "b.com", 1 ) );
    lp.add( new Record( "a.com", 3 ) );
    lp.add( new Record( "a.com", 4 ) );
    lp.add( new Record( "b.com", 4 ) );
    lp.add( new Record( "a.com", 3 ) );
    assertTrue( 3 == lp.windowSize());
    score += 5;
  }


  private static String[] sites = {
    "google.com", "google.com", "yahoo.com", "facebook.com",
    "cnn.com", "cricinfo.org", "rediff.com", "abcnews.com"
  };


  @Test(timeout=10000) 
  public void testStressMedium() {
    final int numTrials = 100000;
    final int windowWidth = 10;
    private Random r;
    private long timeStamp;

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
    LogProcessor lp = new ReferenceLogProcessor(windowWidth);
    for ( int i = 0 ; i < numTrials; i++ ) {
      ReferenceLogProcessor.add( sites[ r.nextInt( sites.length )], timeStamp );
      timeStamp += r.nextInt(windowWidth);
    }
    long finishTime = System.nanoTime(); 
    assert( lp.windowSize() == rlp.windowSize() );
    List<String> rlpWindow = rlp.getWindow();
    List<String> lpWindow = lp.getWindow();
    Iterator<String> rlpWindowIterator = rlpWindowIterator.iterator();
    Iterator<String> lpWindowIterator = lpWindowIterator.iterator();
    while (true) {
      if ( !rlpWindowIterator.hasNext() ) {
        assertTrue( !lpWindowIterator.hasNext() );
        score +=5;
      }
      assertTrue( rlpWindowIterator.next().equals( lpWindowIterator.next() ) );
    }
  }
  

  @Test(timeout=20000) 
  public void basicServerTest() {
    try {
      SudokuServer.start(16789);
    } catch (Exception e) {
      fail();
    }
    SudokuClient client = new SudokuClient(easyTc);
    client.solve();
    if ( client.success ) {
      score += 10;
    }
  }

  @Tes(timeout=1000)
  public void testServerCachedHardInstance() {
    try {
      SudokuServer.start(16790);
    } catch (Exception e) {
      fail();
    }
    SudokuClient client1 = new SudokuClient(hardTc);
    SudokuClient client2 = new SudokuClient(hardTc);
    long startTime1 = System.nanoTime();
    client1.solve();
    long finishTime1 = System.nanoTime();
    long startTime2 = System.nanoTime();
    client2.solve();
    long finishTime2 = System.nanoTime();


    // should be much faster with cache
    assertTrue( client1.success && client2.success &&
            ( ( finishTime2 - startTime2 )  < (finishTime1 - startTime1 ) / CACHESPEEDUPFACTOR  ) );
      score += 10;
  }

  @Test(timeout=60000)
  public void twoNonConcurrentServerTest() {
    try {
      SudokuServer.start(16791);
    } catch (Exception e) {
      fail();
    }
    long startTime = System.nanoTime();
    SudokuClient c1 = new SudokuClient(hardTc, "nonconc1");
    SudokuClient c2 = new SudokuClient(hardTc2, "nonconc2");
    SudokuClient c3 = new SudokuClient(hardTc2, "nonconc3");
    System.out.println( new Date() );
    c1.solve();
    System.out.println( new Date() );
    c2.solve();
    System.out.println( new Date() );
    c3.solve();
    System.out.println( new Date() );
    long finishTime = System.nanoTime();
    seqTimeForTwoHardTests = finishTime - startTime;
    if ( c1.success && c2.success && c3.success) {
      score += 10;
    }
  }

  @Tes(timeout=60000) 
  public void twoConcurrentServerTest() {
    try {
      SudokuServer.start(16792);
    } catch (Exception e) {
      fail();
    }
    final SudokuClient c1 = new SudokuClient(hardTc, "conc1");
    final SudokuClient c2 = new SudokuClient(hardTc2, "conc2");
    final SudokuClient c3 = new SudokuClient(hardTc3, "conc3");
    long startTime = System.nanoTime();
    Thread t1 = new Thread() {
      public void run() {
        c1.solve();
      }
    };
    Thread t2 = new Thread() {
      public void run() {
        c2.solve();
      }
    };
    Thread t3 = new Thread() {
      public void run() {
        c3.solve();
      }
    };
    t1.start();
    t2.start();
    t3.start();
    try {
      t1.join();
      t2.join();
      t3.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    long finishTime = System.nanoTime();
    double delta = finishTime - startTime;

    System.out.println("Seq vs parallel speedup is " + seqTimeForTwoHardTests + " vs " + delta );

    if ( c1.success && c2.success && c3.success 
          &&( delta < seqTimeForTwoHardTests / PARALLELSPEEDUPFACTOR )  ) {
      score += 20;
    }
  }
}
