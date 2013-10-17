import java.util.Map;
import java.util.HashMap;


public class ChangeMaker {
	final static int[] denoms = {1,3,6,12,24,30};
	// static Map<Integer,Integer> cache = new HashMap<Integer,Integer>();
	static int[] cache;
	
	static int mincoins(int K) {
		cache = new int[K+1];
		return mincoinsrecur(K);
	}
	
	static int mincoinsrecur(int K) {
		for ( int c : denoms ) {
			if ( c == K ) {
				return 1;
			}
		}
		if ( cache[K] != 0) {
			return cache[K];
		}
		
		System.out.println("K = " + K);
		int minSoFar = Integer.MAX_VALUE;
		for ( int c : denoms) {
			if ( c > K ) {
				continue;
			}
			int minWithC = mincoinsrecur( K - c);
			if ( 1 + minWithC < minSoFar ) {
				minSoFar = 1 + minWithC;
			}
		}
		cache[K] = minSoFar;
		return minSoFar;
	}
}
