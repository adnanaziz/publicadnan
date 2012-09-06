import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestPostfix {

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
    Class rpn = Postfix.class;
    Annotation[] annos = rpn.getDeclaredAnnotations();
    String name = getAnnotationAttributeValue( annos, "@Author", "name" );
    String uteid = getAnnotationAttributeValue( annos, "@Author", "uteid" );
    System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
  }

  @Test
  public void testSimpleAdd() {
    assertEquals( 2, Postfix.evalPostfix("1 1 +") );
    score += 5;
  }

  @Test
  public void testSimpleSubtract() {
    assertEquals( 1, Postfix.evalPostfix("2 1 -") );
    score += 5;
  }

  @Test
  public void testSimpleDivide() {
    assertEquals( 2, Postfix.evalPostfix("4 2 /") );
    score += 5;
  }

  @Test
  public void testSimpleMultiply() {
    assertEquals( 42, Postfix.evalPostfix("7 6 *") );
    score += 5;
  }

  @Test
  public void testAddWithNegative() {
    assertEquals( -1, Postfix.evalPostfix("-7 6 +") );
    score += 5;
  }

  @Test
  public void testSubtractWithNegative() {
    assertEquals( -1, Postfix.evalPostfix("-7 -6 -") );
    score += 5;
  }

  @Test
  public void testComplex() {
    assertEquals( -30, Postfix.evalPostfix(" 2 -7 + -6 * -1 /") );
    score += 5;
  }

  @Test
  public void testComplexWhitespace1() {
    assertEquals( -30, Postfix.evalPostfix(" 2    -7    + -6    * -1 /  ") );
    score += 5;
  }

  @Test
  public void testComplexWhitespace2() {
    assertEquals( -26, Postfix.evalPostfix(" 4 555 + -555 + 2    -7    + -6    * -1 /  + ") );
    score += 5;
  }

  @Test
  public void testEmptyStackExceptionThrown() {
    try {
      Postfix.evalPostfix("1 +");
      fail("Should have thrown EmptyStackException");
    } catch (EmptyStackException e) {
      assertTrue(true);
      score += 10;
    }
  }

  @Test 
  public void testDivideByZero() {
    try {
      Postfix.evalPostfix("100 0 /");
      fail("Should have thrown ArithmeticException");
    } catch (ArithmeticException e) {
      assertTrue(true);
      score += 10;
    }
  }

  @Test 
  public void testDivideByZeroCorrectMessage() {
    try {
      Postfix.evalPostfix("100 0 /");
      fail("Should have thrown ArithmeticException");
    } catch (ArithmeticException e) {
      assertTrue(e.toString().indexOf("Divide by zero:2") != -1);
      score += 10;
    }
  }

  @Test 
  public void testStackLeftoverException() {
    try {
      Postfix.evalPostfix("-1 23 + 456");
      fail("Should have thrown StackLeftoverException");
    } catch (StackLeftoverException e) {
      assertTrue(true);
      score += 10;
    }
  }

  @Test 
  public void testStackLeftoverExceptionCorrectMessage() {
    try {
      Postfix.evalPostfix("-1 23 + 456");
      fail("Should have thrown StackLeftoverException");
    } catch (StackLeftoverException e) {
      assertTrue(e.toString().indexOf("Stack contains more than one entry:22,456") != -1);
      score += 10;
    }
  }

  @Test 
  public void testNumberFormatException() {
    try {
      Postfix.evalPostfix("1.0 2 + ");
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      score += 10;
    }
  }

  @Test 
  public void testUnsupportedOperandException() {
    try {
      Postfix.evalPostfix("1 2 % ");
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
      score += 10;
    }
  }

  @Test 
  public void testUnsupportedOperandAndNumberFormatException() {
    try {
      Postfix.evalPostfix("1 1 + 2.0 % ");
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertTrue(e.toString().indexOf("Bad input:3") != -1);
      score += 10;
    }
  }
}
