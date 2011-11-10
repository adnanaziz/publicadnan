import junit.framework.*;

public class TestLargest extends TestCase {

 public TestLargest(String s) {
   super(s);
 }

 static public Test suite() {
   TestSuite suite = new TestSuite();
   suite.addTest(new TestLargest("testEmpty"));
   suite.addTest(new TestLargest("testOrder"));
   return suite;
 }

  public void testOrder() {
    assertEquals(9, Largest.largest(new int[] {9,8,7}));
    assertEquals(9, Largest.largest(new int[] {8,9,7}));
    assertEquals(9, Largest.largest(new int[] {7,8,9}));
  }

  public void testEmpty() {
    try {
      Largest.largest(new int[] {});
      fail("Should have thrown an exception");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  public void testOrder2() {
    int[] arr = new int[3];
    arr[0] = 8;
    arr[1] = 9;
    arr[2] = 7;
    assertEquals(9, Largest.largest(arr));
  }
}
