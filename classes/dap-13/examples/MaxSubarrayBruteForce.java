import java.math.*;
import java.util.Random;
import java.util.Date;

public class MaxSubarrayBruteForce {
  public static int maxSubarray(int[] A) {
    int maxSum = 0;
    for ( int i = 0 ; i < A.length; i++ ) {
      for ( int j = i; j < A.length; j++ ) {
        int thisSum = 0;
        for ( int k = i; k <= j; k++ ) {
          thisSum += A[k];
        }
        if ( thisSum > maxSum ) {
          maxSum = thisSum;
        }
      }
    }
    return maxSum;
  }

  private static int[] randomArray(int length) {
    int[] result = new int[length];
    Random r = new Random(0);
    for ( int i = 0 ; i < length; i++ ) {
      result[i] = 1-r.nextInt(3); 
    }
    return result;
  }

  public static void main(String[] args) {

    int[] A0 = {-1,2,3,-4,5,-8,3,3};
    assert(6 == maxSubarray(A0));

    for ( int i = 1; i <= 20; i++ ) {
      int L = (int) Math.pow(2,i);
      int[] A = randomArray( L );
      Date start = new Date();
      int max = maxSubarray( A );
      Date end = new Date();
      System.out.println("max(" + L + ") = " + max + "\tTime (ms) = " + (end.getTime() - start.getTime()) ); 
    }
  }
}
