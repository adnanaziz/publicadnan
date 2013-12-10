<<<<<<< Updated upstream
dummy
=======
public class MaxSubarray {

  public static int max( int... A ) {
    int result = Integer.MIN_VALUE;
    for ( int tmp : A ) {
      if ( result < tmp ) {
        result = tmp;
      }
    }
    return result;
  }

  public static int maxsubarray(int[] A) {
    if ( A.length == 0 ) {
      return 0;
    } else {
      return maxsubarray(A, 0, A.length-1);
    }
  }

  public static int maxsubarray(int[] A, int S, int F) {
    if ( S == F ) {
      return A[S] > 0 ? A[S] : 0;
    }

    int midIndex = (S+F)/2;

    int maxLeft = maxsubarray( A, S, midIndex );
    int maxRight = maxsubarray( A, midIndex + 1, F );

    int maxBeforeMid = 0;
    int tmpSum = 0;
    for ( int i = midIndex; i >= S; i-- ) {
      tmpSum += A[i];
      // System.out.println("\ni = " + i + " down tmpSum = " + tmpSum );
      if ( tmpSum > maxBeforeMid ) {
        maxBeforeMid = tmpSum;
      }
    }
    int maxAfterMid = 0;
    tmpSum = 0;
    for ( int i = midIndex + 1; i <= F; i++ ) {
      tmpSum += A[i];
      // System.out.println("\ni = " + i + " up tmpSum = " + tmpSum );
      if ( tmpSum > maxAfterMid ) {
        maxAfterMid = tmpSum;
      }
    }
    int result =  max( maxLeft, maxRight, maxBeforeMid + maxAfterMid );
    // System.out.println("Recur call, " + S + ":" + F + " max of " + maxLeft + " " + maxRight + " " + maxAfterMid );
    // System.out.println("Recur call, " + S + ":" + F + " = " + result );
    return result;
  }
}
>>>>>>> Stashed changes
