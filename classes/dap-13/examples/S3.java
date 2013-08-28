
public class S3 {
	public static int _wc(String s) {
		String[] A =  s.split("[ ]+");
		for ( String t : A) {
			System.out.println(t);
		}
		return A.length;
	}
	public static int wc(String s) {
		int result = 0;
		int L = s.length();
		for ( int i = 0 ; i < L - 1; i++ ) {
			char c = s.charAt(i);
			char cn = s.charAt(i+1);
			System.out.println();
			if ( c != ' ' &&  cn == ' ' )  {
				result++;				
			}
		}
		if ( L == 0 || s.charAt(L-1) == ' ') {
			return result;
		} else {
			return result + 1;
		}
	}
}
