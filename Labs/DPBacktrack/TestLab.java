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
import java.util.Map;

public class TestLab {

  public static final Class CLASSUNDERTEST = DataCenter.class;

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

  @Test 
  public void testDcSimple() {
    List<Machine> machines = new ArrayList<Machine>();
    machines.add( new Machine( 1000, 1000 ) );
    machines.add( new Machine( 1000, 1000 ) );
    List<Task> tasks = new ArrayList<Task>();
    tasks.add( new Task( 1000, 1000 ) );
    tasks.add( new Task( 1000, 1000 ) );
    tasks.add( new Task( 1000, 1000 ) );
    Map<Machine,List<Task>> result = DataCenter.solve( machines, tasks );
    assertTrue( result == null );
    score += 5;
  }

  @Test 
  public void testTicTacToeSimple() {
    assertFalse( GenTicTacToe.winnable("X", "X,O,X", "O,X,E", "E,O,E") ); 
    score += 5;
  }
}
