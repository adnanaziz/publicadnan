<<<<<<< Updated upstream
dummy
=======
import org.junit.*;
import static org.junit.Assert.*;

public class MaxSubarrayTest {

  @Test
  public void t1() {
   int[] A = {1,2,3,4};
   int result = MaxSubarray.maxsubarray(A);
   assertEquals(10, result);
  }

  @Test
  public void t2() {
   int[] A = {3,-2,3,4,-4,3};
   int result = MaxSubarray.maxsubarray(A);
   assertEquals(8, result);
  }

  @Test
  public void t3() {
    int[] A = {-3,-2,-3,-4,-4,-3};
    int result = MaxSubarray.maxsubarray(A);
    assertEquals(0, result);
  }

  @Test
  public void t4() {
    int[] A = {-3,2,-3,-4,-4,-3};
    int result = MaxSubarray.maxsubarray(A);
    assertEquals(2, result);
  }
  
  @Test
  public void t5() {
    int[] A = {};
    int result = MaxSubarray.maxsubarray(A);
    assertEquals(0, result);
  }

}
>>>>>>> Stashed changes
