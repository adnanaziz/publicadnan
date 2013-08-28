
public class MaxProfit {
	
	public static int maxProfit(int[] A) {
		int maxProfitSoFar = 0;
		if ( A == null ) {
			throw new IllegalArgumentException("null input");
		} else if ( A.length == 1 ) {
			throw new IllegalArgumentException("length 1 input");
		}
		int minValueSoFar = A[0];
		for ( int i = 1; i < A.length; i++ ) {
			if ( maxProfitSoFar < ( A[i] - minValueSoFar) ) {
				maxProfitSoFar = A[i] - minValueSoFar;
			}
			if ( minValueSoFar > A[i]) {
				minValueSoFar = A[i];
			}
		}
		return maxProfitSoFar;
	}
	
	public static void main(String[] args) {
		int[] A = new int[]{1,2,3,4};
		int p = maxProfit(A);
		if ( 3 != p ) {
			System.out.println("Error on {1,2,3,4}, got " + p 
					+ " expected 3");
		} else {
			System.out.println("Success");
		}
	}
}
