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

  // should be a better way to do this using existing lib function
  static String getAnnotationAttributeValue( Annotation [] annos, String annoName, String attribute ) {
    String annoLine = null;
    for ( Annotation anno : annos ) {
      if ( anno.toString().startsWith( annoName ) ) {
        annoLine = anno.toString();
        break;
      }
    }
    if ( annoLine == null ) {
      return null;
    }
    // annLine is of the form:
    // "@Author(name=Adnan Aziz, uteid=aa123)"
    String tmp1 = annoLine.replace("@Author", "");
    String tmp2 = tmp1.replace("(", "");
    String tmp3 = tmp2.replace(")", "");
    String attribValuePairs[] = tmp3.split(",");
    for ( int i = 0 ; i < attribValuePairs.length; i++ ) {
      // System.out.println("a-v:" + attribValuePairs[i]);
      String pair[] = attribValuePairs[i].trim().split("=");
      for( int j = 0 ; j < pair.length; j++ ) {
        // System.out.println(pair[j]);
      }
      if ( pair[0].equals( attribute ) ) {
        return pair[1];
      }
    }
    return null;
  }

  @AfterClass
  public static void oneTimeTearDown() {
    Class rpn = RPN.class;
    Annotation[] annos = rpn.getDeclaredAnnotations();
    String name = getAnnotationAttributeValue( annos, "@Author", "name" );

    String uteid = getAnnotationAttributeValue( annos, "@Author", "uteid" );
    System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
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
