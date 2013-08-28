public class PrefixChecker {
	
	private static int mylength(String s) {
		int len = 0;
		try {
			while (true) {
				s.charAt(len);
				len++;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("e = " + e.toString());
			return len;
		}
	}
	
	public static boolean isPrefix(String s1, String s2) {
		int i = 0, len = 0, count = 0;
		int l1 = mylength(s1);
		int l2 = mylength(s2);
		
		if ( l2 < l1 ) {
			return false;
		}

		boolean result = true;
		for (i = 0; i < l1; i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				result = false;
				break;
			}
		}

		return result;
	}
}
