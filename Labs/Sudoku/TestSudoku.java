import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestSudoku {

  public static final Class CLASSUNDERTEST = SudokuServer.class;

  public static double seqTimeForTwoHardTests;

  public static int score = 0;

  public static int CACHESPEEDUPFACTOR = 100;

  public static double PARALLELSPEEDUPFACTOR = 1.50d;

  public static String easyTc = "111 222 333 444 555 666 777 888 000 ";

  public static String hardTc = "153 " + "178 " + "185 " + "221 " + "242 " 
                       + "335 " + "357 " + "424 " + "461 " 
                       + "519 " + "605 " + "677 " + "683 " 
                       + "722 " + "741 " + "844 " + "889 ";

  // interchange 3 and 4's position -> no cache hit
  public static String hardTc2 = "154 " + "178 " + "185 " + "221 " + "242 " 
                       + "335 " + "357 " + "423 " + "461 " 
                       + "519 " + "605 " + "677 " + "684 " 
                       + "722 " + "741 " + "843 " + "889 ";

  // interchange 7 and 8's position -> no cache hit
  public static String hardTc3 = "154 " + "177 " + "185 " + "221 " + "242 " 
                       + "335 " + "358 " + "423 " + "461 " 
                       + "519 " + "605 " + "678 " + "684 " 
                       + "722 " + "741 " + "843 " + "889 ";

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
   String name = getClassAnnotationValue(CLASSUNDERTEST, Author.class, "name");
   String uteid = getClassAnnotationValue(CLASSUNDERTEST, Author.class, "uteid");
   System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
  }

  @Test(timeout=20000) 
  public void testTcEasy() {
    String result = SudokuSolver.solve( easyTc );
    assertTrue( SudokuSolver.isLegalSolution( result, easyTc ));
    score += 5;
  }

  @Test(timeout=20000) 
  public void testTcHard() {
    String result = SudokuSolver.solve( hardTc );
    assertTrue( SudokuSolver.isLegalSolution( result,  hardTc ));
    score += 5;
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

  @Test(timeout=20000)
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
    SudokuClient c3 = new SudokuClient(hardTc3, "nonconc3");
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

  @Test(timeout=60000)
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
