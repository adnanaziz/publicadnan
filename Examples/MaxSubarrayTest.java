import org.junit.*;
import static org.junit.Assert.*;

public class MaxSubarrayTest {

  @Test
  public void t1() {
   int[] A = {1,2,3,4};
   int result = MaxSubarray.maxsubarray(A);
   assertEquals(10, result);
  }

}
