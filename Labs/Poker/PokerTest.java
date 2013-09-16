import org.junit.*;
import static org.junit.Assert.*;

public class PokerTest {

  public static void checkEqualWithinTolerance(double[] A, double[] B, double epsilon) {
    if (A.length != B.length) {
      throw new IllegalArgumentException("Can't compare arrays of unequal length");
    }
    for ( int i = 0 ; i < A.length; i++ ) {
      if ( Math.abs( A[i] - B[i] ) > epsilon ) {
        assertTrue("checkEqualWithinTolerance fails, i = " + i + 
                    "\tA[i] = " + A[i] + 
                    "\tB[i] = " + B[i] + 
                    "\tepsilon = " + epsilon, false);
      }
    }
    assertTrue(true);
  }

  public static final double EPSILON = 0.1;

  @Test
  public void t1() {
    double[] result = Poker.probabilities("2H,5H,9H", "AC,10C", "AH,8H", "AD,3S");
    double[] expectedResult = new double[]{0.0, 1.0, 0.0};
    checkEqualWithinTolerance( expectedResult, result, EPSILON );
  }
}

