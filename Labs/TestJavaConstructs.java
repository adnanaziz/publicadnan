import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestJavaConstructs {

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
    Class postfix = PostfixSolution.class;
    Annotation[] annos = postfix.getDeclaredAnnotations();
    String name = getAnnotationAttributeValue( annos, "@Author", "name" );
    String uteid = getAnnotationAttributeValue( annos, "@Author", "uteid" );
    System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
  }


  public static Student [] createTestArray() {
    Student s0 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    Student s1 = new Student( 3.6d, "Imran Aziz", 543, "MIT");
    Student s2 = new Student( 3.5d, "Aardvark Smith", 459, "Berkeley");
    Student s3 = new Student( 2.9d, "Thomas Jefferson", 453, "UT Austin");
    Student s4 = new Student( 3.3d, "Matt Biondi", 3383, "Berkeley");
    Student [] testarray = {s0, s1, s2, s3, s4};
    return testarray;
 }

  @Test
  public void testIterator1() {
    Student [] testCase = Student.createTestArray();
    JavaConstructs dummy = new JavaConstructs();
    Iterator<Student> berkeleyIterator = 
        dummy.new StudentIteratorBySchool(testCase, "Berkeley");
    int count = 0;
    while ( berkeleyIterator.hasNext() ) {
      assertTrue( berkeleyIterator.next().school.equals( "Berkeley"));
      count++;
    }
    assertEquals( count, 2 );
  }

  @Test
  public void testIterator2() {
    Student [] testCase = Student.createTestArray();
    JavaConstructs dummy = new JavaConstructs();

    Iterator<Student> mitIterator = 
        dummy.new StudentIteratorBySchool(testCase, "MIT");
    int count = 0;
    while ( mitIterator.hasNext() ) {
      assertTrue( mitIterator.next().school.equals("MIT"));
      count++;
    }
    assertEquals( count, 1 );
  }

  @Test
  public void testIterator3() {
    Student [] testCase = Student.createTestArray();
    JavaConstructs dummy = new JavaConstructs();

    Iterator<Student> stanfordIterator = 
        dummy.new StudentIteratorBySchool(testCase, "Stanford");
    assertFalse( stanfordIterator.hasNext() );
  }

  @Test
  public void testPredicate1() {
    Student [] testCase = Student.createTestArray();
    final double lowRange = 3.5;
    final double highRange = 3.5;
    Student [] filteredTestCase =  JavaConstructs.apply( testCase, new JavaConstructs.StudentPredicate() {
      public boolean check( Student s ) {
        return (s.GPA >= lowRange && s.GPA <= highRange );
      }
    });
    for ( Student s : filteredTestCase ) {
      assertTrue( s.GPA >= lowRange && s.GPA <= highRange );
    }
  }

  @Test
  public void testPredicate2() {
    Student [] testCase = Student.createTestArray();
    Student [] filteredTestCase = JavaConstructs.apply( testCase, new  JavaConstructs.GpaPredicate(3.5, 3.6) );
    for ( Student s : filteredTestCase ) {
      assertTrue( s.GPA >= 3.5 && s.GPA <= 3.6 );
    }
  }

  @Test(timeout=1000)
  public void testString() {
    for ( int i = 1 ; i < 16; i++ ) {
       long startTime = System.nanoTime();
       String tmp = JavaConstructs.nCopies( (int) Math.pow(2,i), "test" );
       long finishTime = System.nanoTime();
       System.out.println("Iteration " + i + ": string of length " + tmp.length() + 
         " took " + ((double)(finishTime - startTime))/1000000000.0d + " seconds");
    }
  }

  @Test 
  public void testEquals() {
    Student s0 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    Student s1 = new Student( 3.9d, "Adnan Aziz", 123, "UT Austin");
    Student s2 = new Student( 3.8d, "Adnan Aziz", 123, "UT Austin");
    StudentAthelete sa0 = new StudentAthelete( 3.9d, "Adnan Aziz", 123, "UT Austin", 
        SportType.Baseball);
    StudentAthelete sa1 = new StudentAthelete( 3.9d, "Adnan Aziz", 123, "UT Austin",
        SportType.Football);

    assertFalse( s0.equals(s1) );
    assertFalse( s1.equals(s0) );
    assertTrue( s0.equals(s0) );
    assertTrue( s0.equals(s2) );
    assertTrue( sa0.equals(s1) );
    assertTrue( sa1.equals(s1) );
    assertFalse( sa0.equals(sa1) );
  }
}
