import org.junit.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class TestLab {

  public static final Class<DataCenter> CLASSUNDERTEST = DataCenter.class;

  public static int score = 0;

  @Before
  public void setUp() {
    // System.out.println("Setting up")); score += 5;}
  }

  // Ang's suggestion on getting annotation values
  public static String getClassAnnotationValue(Class<DataCenter> classType, 
                                               Class<Author> annotationType, 
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
    machines.add( new Machine( 3, 3) );
    machines.add( new Machine( 3, 3) );
    List<Task> tasks = new ArrayList<Task>();
    tasks.add( new Task( 2, 2) );
    tasks.add( new Task( 2, 1) );
    tasks.add( new Task( 1, 2) );
    tasks.add( new Task( 1, 1) );
    Map<Machine,List<Task>> result = DataCenter.solve( machines, tasks );
    assertTrue( result != null );
    score += 5;
  }

  @Test 
  public void testDcComplexDirected() {
    List<Machine> machines = new ArrayList<Machine>();
    machines.add( new Machine( 1179, 1000 ) );
    machines.add( new Machine( 1180, 1000 ) );

    List<Task> tasks = new ArrayList<Task>();
    int[] taskCpuArray = {65, 35, 245, 195, 65, 150, 275, 155, 120, 320, 75, 40, 200, 100, 220, 99};
    int[] taskRamArray = {12, 13, 11,  8,   3,  14,  7,   2,   1,   5,   15, 12, 4,   9,   1,   12};
    for ( int i = 0 ; i < taskCpuArray.length; i++ ) {
      tasks.add( new Task( taskCpuArray[i], taskRamArray[i] ) );
    }

    Map<Machine,List<Task>> result = DataCenter.solve( machines, tasks );
    assertTrue( result != null );
    score += 10;
  }

  @Test 
  public void testTicTacToeSimple() {
    assertTrue( GenTicTacToe.winnable(3, "X", "E,E,E", "O,X,E", "E,E,E") ); 
    score += 5;
  }

  @Test 
  public void testTicTacToeLimitedMovesSimple1() {
    assertFalse( GenTicTacToe.winnable(3, "X", 2, "E,E,E", "O,X,E", "E,E,E") ); 
    score += 5;
  }

  @Test 
  public void testTicTacToeLimitedMovesSimple2() {
    assertFalse( GenTicTacToe.winnable(3, "X", 3, "E,E,E", "O,X,E", "E,E,E") ); 
    score += 5;
  }
}
