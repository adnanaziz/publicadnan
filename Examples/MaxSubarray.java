
public class MaxSubarray {
	
	public static int maxsubarray(int[] A) {
		if ( A.length == 0 ) {
			return 0;
		} else {
			return maxsubarray(A, 0, A.length-1);
		}
	}
	
	// L is the index of the start of the subarray to search in,
	// H s the index of the end of the subarray. (both are inclusive)
	public static int maxsubarray(int[] A, int L, int H) {
		if ( L == H ) {
			return (A[L]) > 0 ? A[L] : 0;
		}
		
		int midIndex = (L+H)/2;
		
		int bestLeft = maxsubarray( A, L, midIndex );
		int bestRight = maxsubarray( A, midIndex + 1, H);
		
		int maxRight = 0;
		int tmp = 0;
		for ( int i = midIndex + 1; i <= H; i++ ) {
			tmp += A[i];
			if ( maxRight < tmp) {
				maxRight = tmp;
			}
		}
		
		int maxLeft = 0;
		tmp = 0;
		for ( int i = midIndex; i >= L; i-- ) {
			tmp += A[i];
			if ( maxLeft < tmp) {
				maxLeft = tmp;
			}
		}
		
		return max(bestLeft, bestRight, maxLeft + maxRight);
	}
	
	public static int max( int... T) {
		int result = Integer.MIN_VALUE;
		for ( int tmp : T) {
			if ( result < tmp) {
				result = tmp;
			}
		}
		return result;
	}

}
