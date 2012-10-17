import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;

import java.util.List;
import java.util.ArrayList;

public class TestLab {

  public static final Class CLASSUNDERTEST = RegExp.class;

  public static int score = 0;

  @Before
  public void setUp() {
    // System.out.println("Setting up")); score += 5;}
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

  @Test public void testRegExp1() { assertTrue( RegExp.match( ".", "a")); score += 5;}
  @Test public void testRegExp2() { assertTrue( RegExp.match( "a", "a")); score += 5;}
  @Test public void testRegExp3() { assertFalse( RegExp.match( "a", "b")); score += 5;}
  @Test public void testRegExp4() { assertTrue( RegExp.match( "a.9", "aW9")); score += 5;}
  @Test public void testRegExp5() { assertFalse( RegExp.match( "a.9", "aW19")); score += 5;}
  @Test public void testRegExp6() { assertTrue( RegExp.match( "^a.9", "aW9")); score += 5;}
  @Test public void testRegExp7() { assertFalse( RegExp.match( "^a.9", "baW19")); score += 5;}
  @Test public void testRegExp8() { assertTrue( RegExp.match( ".*", "a")); score += 5;}
  @Test public void testRegExp9() { assertTrue( RegExp.match( ".*", "")); score += 5;}
  @Test public void testRegExp10() { assertTrue( RegExp.match( ".*",  "asdsdsa")); score += 5;}
  @Test public void testRegExp11() { assertTrue( RegExp.match( "9$" , "xxxxW19")); score += 5;}
  @Test public void testRegExp12() { assertTrue( RegExp.match( ".*a", "ba")); score += 5;}
  @Test public void testRegExp13() { assertTrue( RegExp.match( ".*a", "ba")); score += 5;}
  @Test public void testRegExp14() { assertTrue( RegExp.match( "^a.*9$", "axxxxW19")); score += 5;}
  @Test public void testRegExp15() { assertTrue( RegExp.match( "^a.*W19$", "axxxxW19")); score += 5;}
  @Test public void testRegExp16() { assertTrue( RegExp.match( ".*a.*W19", "axxxxW19123")); score += 5;}
  @Test public void testRegExp17() { assertFalse( RegExp.match( ".*b.*W19", "axxxxW19123")); score += 5;}
  @Test public void testRegExp18() { assertTrue( RegExp.match( "n.*", "n")); score += 5;}
  @Test public void testRegExp19() { assertTrue( RegExp.match( "a*n.*", "an")); score += 5;}
  @Test public void testRegExp20() { assertTrue( RegExp.match( "a*n.*", "ana")); score += 5;}
  @Test public void testRegExp21() { assertTrue( RegExp.match( "a*n.*W19", "anaxxxxW19123")); score += 5;}
  @Test public void testRegExp22() { assertTrue( RegExp.match( ".*a*n.*W19", "asdaaadnanaxxxxW19123")); score += 5;}
  @Test public void testRegExp23() { assertTrue( RegExp.match( ".*.*.a*n.*W19", "asdaaadnanaxxxxW19123")); score += 5;}

  @Test 
  public void testSkyline1() {
    List<Building> lb = new ArrayList<Building>();
    lb.add( new Building( 0,1,1) );
    lb.add( new Building( 1,1,1) );
    lb.add( new Building( 2,1,1) );
    List<Point> expectedSkyline = new ArrayList<Point>();
    expectedSkyline.add( new Point(0,1) );
    expectedSkyline.add( new Point(3,1) );
    List<Point> skyline = Skyline.computeSkyline( lb );
    assertEquals( skyline, expectedSkyline );
    score += 10;
  }
}
