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
    Machine machine = new Machine( 2, 2 );
    List<Task> tasks = new ArrayList<Task>();
    tasks.add( new Task.TaskBuilder().setMips(2).setRam(1).setPrice(3).build());
    tasks.add( new Task.TaskBuilder().setMips(1).setRam(1).setPrice(1).build());
    tasks.add( new Task.TaskBuilder().setMips(1).setRam(1).setPrice(1).build());
    List<Task> result = DataCenter.solve( machine, tasks );
    assertTrue( result.size() == 1 );
    assertTrue( result.get(0).equals( new Task.TaskBuilder().setMips(2).setRam(1).setPrice(3).build() ) );
    score += 5;
  }

  @Test 
  public void testDcComplexDirected() {
    Machine machine = new Machine( 1179, 1000 );

    List<Task> tasks = new ArrayList<Task>();
    int[] taskCpuArray =   {65, 35, 245, 195, 65, 150, 275, 155, 120, 320, 75, 40, 200, 100, 220, 99};
    int[] taskRamArray =   {12, 13, 11,  8,   3,  14,  7,   2,   1,   5,   15, 12, 4,   9,   1,   12};
    int[] taskPriceArray = {1,  1,  1,   1,   1,  1,   1,   1,   1,   1,   1,  1,  1,   1,   1,   1};
    for ( int i = 0 ; i < taskCpuArray.length; i++ ) {
      tasks.add( new Task.TaskBuilder()
                            .setMips(taskCpuArray[i])
                            .setRam( taskRamArray[i])
                            .setPrice(taskPriceArray[i] ).build() );
    }

    List<Task> result = DataCenter.solve( machine, tasks );
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
