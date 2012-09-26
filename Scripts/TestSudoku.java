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
    String name = getClassAnnotationValue(CLASSUNDERTEST,
                                          Author.class, "name");
    String uteid = getClassAnnotationValue(CLASSUNDERTEST,
                                           Author.class, "uteid");
   System.out.println("\n@score" + "," + name + "," + uteid + "," + score);
  }

  @Test
  public void testTc1() {
    String tc1 = "111 222 333 444 555 666 777 888 999 ";
    String result = SudokuSolver.solve( tc1 );
    assertTrue( SudokuSolver.isLegalSolution( result ));
    score += 5;
  }

}
