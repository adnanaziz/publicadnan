import org.junit.*;
import static org.junit.Assert.*;

import java.lang.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class TestLab {

  public static final Class<DataCenter> CLASSUNDERTEST = DataCenter.class;

  public static int score = 0;

  @Ignore @Test 
  public void testTicTacToeSimple_0() {
    assertTrue( GenTicTacToe.winnable(3, "X", "E,E,E", "O,X,E", "E,E,E") ); 
    score += 5;
  }
   @Ignore @Test 
   public void testTicTacToeSimple_1() {
     assertTrue( GenTicTacToe.winnable(3, "X", "E,E,E", "O,X,E", "E,E,E") ); 
     score += 5;
   }
   @Ignore @Test 
   public void testTicTacToeSimple_2() {
     assertTrue( GenTicTacToe.winnable(4, "X","E,E,O,E", "O,X,E,O", "X,O,X,E","E,E,O,X") ); 
     score += 5;
   }
   @Ignore @Test 
   public void testTicTacToeSimple_3() {
     assertFalse( GenTicTacToe.winnable(3, "X","O,E,E", "X,O,O", "X,O,X") ); 
     score += 5;
   }
   @Ignore @Test 
   public void testTicTacToeSimple_4() {
     assertTrue( GenTicTacToe.winnable(3, "O","O,E,E", "X,O,O", "X,O,X") ); 
     score += 5;
   }
  @Test 
  public void testTicTacToeLimitedMoves_1() {
    assertFalse( GenTicTacToe.winnable(3, "X", 1, "E,E,E", "O,X,E", "E,E,E") ); 
    score += 5;
  }

    @Ignore @Test 
  public void testTicTacToeLimitedMoves_2() {
    assertFalse( GenTicTacToe.winnable(3, "X", 2, "E,E,O", "O,X,E", "E,E,E") ); 
    score += 5;
  }
   @Ignore @Test 
   public void testTicTacToeLimitedMoves_3() {
     assertTrue( GenTicTacToe.winnable(4, "O", 2, "O,E,E,E", "O,X,E,E", "O,O,X,X","E,E,X,E") ); 
     score += 5;
   }
   @Ignore @Test 
   public void testTicTacToeLimitedMoves_4() {
     assertTrue( GenTicTacToe.winnable(4, "O", 2, "X,E,E,O", "E,X,E,E", "E,X,E,O","O,E,X,O") ); 
     score += 5;
   }
}
