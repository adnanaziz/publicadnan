import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.zip.DataFormatException;

// for querying results: http://www.dc.fi.udc.es/ai/tp/practica/junit/doc/cookbook/cookbook.htm
// look for testresult object

public class TestRPN {

  public static int score = 100;

  @Before
  public void setUp() {
    // System.out.println("Setting up");
  }

  @AfterClass
  public static void oneTimeTearDown() {
    System.out.println("Final score is " + score );
  }

  @Test
  public void testRPNBasic() throws DataFormatException {
    assertEquals( RPN.evalRPN("1 2 +"), 3 );
  }

  @Test
  public void testRPNEmptyStack() throws DataFormatException {
    try {
      RPN.evalRPN("1 +");
      fail("Should have thrown DataFormatException");
    } catch (DataFormatException e) {
      assertTrue(true);
    }
  }
}
