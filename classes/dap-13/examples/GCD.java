public class GCD {
  static int gcd(int a, int b) {
    if ( a == 0 ) {
      return b;
    } else if ( a == 0 ) {
      return b;
    } else if ( a < b ) {
      return gcd( b % a, a );
    } else {
      return gcd( a % b, b );
    }
  }

  public static void main(String[] args) {
    assert( 5 == gcd(70,25) );
    assert( 6 == gcd(6,6) );
    assert( 12 == gcd(72,60) );
  }
}
