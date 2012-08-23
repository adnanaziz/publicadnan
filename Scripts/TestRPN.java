import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestRPN {

  public static int score = 0;

  @Before
  public void setUp() {
    // System.out.println("Setting up");
  }

  @AfterClass
  public static void oneTimeTearDown() {
    System.out.println("\nFinal score is " + score );
    Class rpn = RPN.class;
    Annotation[] annos = rpn.getDeclaredAnnotations();
    // System.out.println("The annotations are:");
    for ( Annotation anno : annos ) {
      System.out.println(anno);
    }
  }

  @Test
  public void testBadSum() throws DataFormatException {
    assertEquals( RPN.evalRPN("2 2 +"), 3 );
    // note: the increment below does not happen since
    // the assert fails. furthermore, no additional
    // code will be invoked (see example below)
    // therefore all tests should have a SINGLE assert
    // (since if it fails, all subsequent ones are skipped
    score += 10;
    // this code will not be invoked since the previous
    // assert fails. in particular the score will not be incremented
    // by 10 in the line following it
    assertEquals( RPN.evalRPN("1 2 +"), 3 );
    score += 10;
  }

  @Test
  public void testEmptyStackExceptionThrown() throws DataFormatException {
    try {
      RPN.evalRPN("1 +");
      fail("Should have thrown DataFormatException");
    } catch (DataFormatException e) {
      assertTrue(true);
      score += 10;
    }
  }
}
