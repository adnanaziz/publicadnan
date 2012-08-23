import junit.framework.*;
import java.util.zip.DataFormatException;
import java.util.EmptyStackException;

public class TestRPN extends TestCase {

  @Override
  protected void setUp() {
  }

  public void testRPN() throws EmptyStackException, DataFormatException {
    assertEquals( RPN.evalRPN("1 2 +"), 3 );
  }
}
