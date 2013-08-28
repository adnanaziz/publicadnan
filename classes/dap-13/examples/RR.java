
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
		int initLength = computeInitialLength(A);

		int writeLocation = 0;
		int readLocation = 0;

    int lengthAfterRemoveB = 0;
		while ( readLocation < initLength ) {
			if ( A[readLocation] == 'b') {
				readLocation++;
			} else {
        // replace 'a' by 'dd' in second pass
				A[writeLocation] = A[readLocation];
				writeLocation++;
				readLocation++;
        lengthAfterRemoveB++;
			} 
    }
    if ( writeLocation < finalLength - 1 ) {
      // null terminate A so we dont read old entries
      A[writeLocation] = '0';
    }
    System.out.println("\nDone removing b, this is current state:");
		for (char c : A) {
			System.out.print(c + ":");
		}
    System.out.println("");

    // now replace 'a' by 'dd'
    writeLocation = finalLength -1;
    readLocation = lengthAfterRemoveB-1;
    while ( readLocation >= 0 ) {
      System.out.println("writeLocation,readLocation=" + writeLocation + "," + readLocation );
      // turn on to debug
		  // for (char c : A) {
		  // 	System.out.print(c + ":");
		  // }
      if ( A[readLocation] != 'a' ) {
				A[writeLocation] = A[readLocation]; // just copy it over
        writeLocation--;
      } else {
				A[writeLocation] = 'd';
				A[writeLocation-1] = 'd';
				writeLocation -= 2;
			}
      readLocation--;
		}
	}
	
	public static void main(String[]args) {
		char[] A0 = {'a','b', 'c', 'd', 'a', '0'};
    System.out.println("A0 before remove-and-replace = ");
		for (char c : A0) {
			System.out.print(c + ":");
		}
		removeAndReplace(A0);
    System.out.println("\nA0 after remove-and-replace = ");
		for (char c : A0) {
			System.out.print(c + ":");
		}
		System.out.println();
	}
	
}
