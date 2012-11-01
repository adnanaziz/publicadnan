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
    System.out.println("result = " + result.toString() );
    assertEquals( result.size(), 1 );
    assertEquals( tasksValue( result), 3 );
    score += 5;
  }

  @Test 
  public void testDcComplexDirected() {
    Machine machine = new Machine( 2, 2 );

    List<Task> tasks = new ArrayList<Task>();
    int[] taskCpuArray =   {1,1,1,1};
    int[] taskRamArray =   {1,1,1,1};
    int[] taskPriceArray = {1,2,3,4};
    for ( int i = 0 ; i < taskCpuArray.length; i++ ) {
      tasks.add( new Task.TaskBuilder()
                            .setMips(taskCpuArray[i])
                            .setRam( taskRamArray[i])
                            .setPrice(taskPriceArray[i] ).build() );
    }

    List<Task> result = DataCenter.solve( machine, tasks );
    System.out.println("result = " + result.toString() );
    assertEquals( 7, tasksValue(result) );
    score += 10;
  }
   @Test 
  public void testDcComplex_2() {
    Machine machine = new Machine( 11, 14 );

    List<Task> tasks = new ArrayList<Task>();
    int[] taskCpuArray =   {1,3,2,7,5};
    int[] taskRamArray =   {4,1,6,3,8};
    int[] taskPriceArray = {5,2,7,3,1};
    for ( int i = 0 ; i < taskCpuArray.length; i++ ) {
      tasks.add( new Task.TaskBuilder()
                            .setMips(taskCpuArray[i])
                            .setRam( taskRamArray[i])
                            .setPrice(taskPriceArray[i] ).build() );
    }

    List<Task> result = DataCenter.solve( machine, tasks );
    System.out.println("result = " + result.toString() );
    assertEquals( 14, tasksValue(result) );
    score += 10;
  }
  
   @Test 
  public void testDcComplex_3() {
    Machine machine = new Machine( 16, 14 );

    List<Task> tasks = new ArrayList<Task>();
    int[] taskCpuArray =   {2,4,1,9,6,3,8};
    int[] taskRamArray =   {1,3,2,5,9,4,7};
    int[] taskPriceArray = {5,2,3,4,1,6,9};
    for ( int i = 0 ; i < taskCpuArray.length; i++ ) {
      tasks.add( new Task.TaskBuilder()
                            .setMips(taskCpuArray[i])
                            .setRam( taskRamArray[i])
                            .setPrice(taskPriceArray[i] ).build() );
    }

    List<Task> result = DataCenter.solve( machine, tasks );
    System.out.println("result = " + result.toString() );
    assertEquals( 16, tasksValue(result) );
    score += 10;
  }
  
  
  static String tasksToString(List<Task> list) {
    StringBuilder result = new StringBuilder();
    for ( Task t : list )
      result.append(t.toString() + ":");
    return result.toString();
  }

  static int tasksValue(List<Task> list) {
    int result = 0;
    for ( Task t : list )
      result += t.getPrice();
    return result;
  }

   @Test 
  public void testTicTacToeSimple_0() {
    assertTrue( GenTicTacToe.winnable(3, "X", "E,E,E", "O,X,E", "E,E,E") ); 
    score += 5;
  }
   @Test 
   public void testTicTacToeSimple_1() {
     assertTrue( GenTicTacToe.winnable(3, "O", "E,E,E", "O,X,E", "E,E,E") ); 
     score += 5;
   }
   @Test 
   public void testTicTacToeSimple_2() {
     assertTrue( GenTicTacToe.winnable(4, "X","E,E,O,E", "O,X,E,O", "X,O,X,E","E,E,O,X") ); 
     score += 5;
   }
   @Test 
   public void testTicTacToeSimple_3() {
     assertFalse( GenTicTacToe.winnable(3, "X","O,E,E", "X,O,O", "X,O,X") ); 
     score += 5;
   }
   @Test 
  public void testTicTacToeSimple_4() {      
    assertFalse( GenTicTacToe.winnable(3, "X","E,E,E", "X,O,X", "O,X,O") );     
    score += 5;    
   }
  @Test 
  public void testTicTacToeLimitedMoves_1() {
    assertFalse( GenTicTacToe.winnable(3, "X", 1, "E,E,E", "O,X,E", "E,E,E") ); 
    score += 5;
  }

    @Test 
  public void testTicTacToeLimitedMoves_2() {
    assertFalse( GenTicTacToe.winnable(3, "X", 2, "O,E,E", "X,O,X", "E,O,E") );
    score += 5;
  }
   @Test 
   public void testTicTacToeLimitedMoves_3() {
     assertTrue( GenTicTacToe.winnable(4, "O", 2, "O,E,E,E", "O,X,E,E", "O,O,X,X","E,E,X,E") ); 
     score += 5;
   }
   @Test 
   public void testTicTacToeLimitedMoves_4() {
     assertTrue( GenTicTacToe.winnable(4, "O", 2, "X,E,E,O", "E,X,E,E", "E,X,E,O","O,E,X,O") ); 
     score += 5;
   }
}
