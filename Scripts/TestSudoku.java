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

  public static int score = 0;

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
    // String name = getClassAnnotationValue(CLASSUNDERTEST,
    //                                       Author.class, "name");
    // String uteid = getClassAnnotationValue(CLASSUNDERTEST,
    //                                        Author.class, "uteid");
   // System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
  }

  @Ignore @Test
  public void testTcEasy() {
    String tc = "111 222 333 444 555 666 777 888 000 ";
    String result = SudokuSolver.solve( tc );
    assertTrue( SudokuSolver.isLegalSolution( result ));
    score += 5;
  }

  @Ignore @Test
  public void testTcHard() {
    String tc = "153 " + "178 " + "185 " + "221 " + "242 " 
                       + "335 " + "357 " + "424 " + "461 " 
                       + "519 " + "605 " + "677 " + "683 " 
                       + "722 " + "741 " + "844 " + "889 ";
    String result = SudokuSolver.solve( tc );
    assertTrue( SudokuSolver.isLegalSolution( result ));
    score += 5;
  }

  @Test 
  public void basicServerTest() {
    try {
      SudokuServer.start();
    } catch (Exception e) {
      fail();
    }
    SudokuClient client = new SudokuClient("111 222 333 444 555 666 777 888 000 ");
    client.solve();
    if ( client.success ) {
      score += 10;
    }
  }

  @Test 
  public void twoNonConcurrentServerTest() {
    SudokuClient c2 = new SudokuClient("113 222 333 444 555 666 777 888 000 ");
    SudokuClient c1 = new SudokuClient("113 222 331 445 554 666 778 887 000 ");
    c1.solve();
    c2.solve();
    if ( c1.success && c2.success ) {
      score += 10;
    }
  }

  @Test 
  public void twoConcurrentServerTest() {
    final SudokuClient c1 = new SudokuClient("113 222 331 445 554 666 778 887 000 ");
    final SudokuClient c2 = new SudokuClient("113 222 333 444 555 666 777 888 000 ");
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

    if ( c1.success && c2.success ) {
      score += 20;
    }
  }
}
