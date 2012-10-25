import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


public class TestTicTacToe {

  @Before
  public void setUp() {
  }

  @AfterClass
  public static void oneTimeTearDown() {
  }

  @Test(timeout=20000) 
  public void test2D1() {
    Board b = new Board("X,X", "X,X");
    assertTrue( b.winFor(SquareState.X));
  }

}
