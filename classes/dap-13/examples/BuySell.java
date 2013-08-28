
public class BuySell {
	public static int buysell(int[] P) {
		int minSoFar = P[0];
		int buyDay = 0;
		int sellDay = -1;
		int mostProfitSoFar = -1;
		for( int i = 1 ; i < P.length; i++) {
			if (P[i] - minSoFar > mostProfitSoFar) {
				mostProfitSoFar = P[i] - minSoFar;
				sellDay = i;
			} else if ( minSoFar > P[i] ) {
				minSoFar = P[i];
				buyDay = i;
			}
		}
		System.out.println("buysell: " + buyDay + ":" + sellDay );
		return mostProfitSoFar;
	}
	
	public static void main(String[] args) {
		int[] P0 = {1,2,3};
		if (2!=buysell(P0)) {
			System.out.println("Error: should have been 2");
		}
	}
	
}
