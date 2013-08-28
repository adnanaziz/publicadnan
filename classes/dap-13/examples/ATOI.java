
public class ATOI {
	
	static int decimalCharToInt(char c) {
	  int result = c - '0';
	  if ( result >=0 && result <= 9) {
		  return result;
	  } else {
		  throw new IllegalArgumentException("nondecimal char:" + c);
	  }
	  
	}
	
	static int _decimalCharToInt(char c) {
		switch (c) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		default:
			throw new IllegalArgumentException("nondecimal char:" + c);
		}
	}

	static int stringToInt(String s) {
		int result = 0;
		int currentPlace = 1;
		int factor = 1;
		if ( s == null ) {
			return 0;
		}
		s = s.trim();
		if ( s.length() == 0) {
			return 0;
		}
		if ( s.charAt(0) == '-') {
			if ( s.length() == 1 ) {
				throw new IllegalArgumentException("bad input: \"-\"");
			}
			factor = -1;
			s = s.substring(1);
		}
		for ( int i = s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			result += (decimalCharToInt(c) * currentPlace);
			if ( result < 0 ) {
				throw new ArithmeticException("overflow, cannot represent with integer");
			}
			currentPlace *= 10;
		}
		result *= factor;
		return result;
	}
	
	// TODO: not handling -2^31 correctly

	public static void main(String[] args) {
		System.out.println("123 to integer is = " + stringToInt("123"));
	}

}
