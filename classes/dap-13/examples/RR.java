
public class RR {
	
	public static int computeFinalLength(char[] A) {
		int result = 0;
		for ( int i = 0 ; i < A.length; i++ ) {
			if ( A[i] == 'a') {
				result += 2;
			} else if ( A[i] == 'c' || A[i] == 'd') {
				result++;
			} 
		}
		return result;
	}
	
	public static int computeInitialLength(char[] A) {
		int result = 0;
		for ( int i = 0 ; i < A.length; i++ ) {
			if ( A[i] == '0') {
				break;
			}
			result++;
		}
		return result;
	}
	
	public static void removeAndReplace(char[] A) {
		int finalLength = computeFinalLength(A);
		int writeLocation = finalLength - 1;
		int readLocation = computeInitialLength(A);
		while ( readLocation >= 0 ) {
			if ( A[readLocation] == 'b') {
				readLocation--;
			} else if ( A[readLocation] == 'c' || A[readLocation] == 'd') {
				A[writeLocation] = A[readLocation];
				writeLocation--;
				readLocation--;
			} else {
				A[writeLocation] = 'd';
				A[writeLocation-1] = 'd';
				writeLocation -= 2;
			}
		}
	}
	
	public static void main(String[]args) {
		char[] A0 = {'a','b', 'c', 'd', 'a', '0'};
		removeAndReplace(A0);
		for (char c : A0) {
			System.out.print(c + ":");
		}
		System.out.println();
	}
	
}
