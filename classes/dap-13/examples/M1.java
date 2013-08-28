
public class M1 {
	public static int count(int n) {
		int a;
		int b;
		int result = 0;
		if ( n <= 0) {
			throw new IllegalArgumentException(
					"count argument must"
					+ " be positive, input n = " 
					+ n);
		}
		for ( a = 1 ; a < n - 1; a++ ) {
			for ( b = a + 1 ; b < n ; b++ ) {
				// int tmp = ( a^2 + b^2 + 1 ) % (a * b );
				int tmp1 = a*a;
				int tmp2 = b*b;
				int tmp3 = tmp1 + tmp2 + 1;
				int tmp4 = a * b;
				int tmp = tmp3 % tmp4;
				System.out.println("t1, t2, t3, t4 = " + tmp1 + "," + tmp2 + "," 
							+ tmp3 + "," + tmp4);
				System.out.println("a, b, tmp = " + a + "," + b + "," + tmp);
				if ( tmp == 0 ) {
					result++;
				}
			}
		}
		return result;
	}

}
