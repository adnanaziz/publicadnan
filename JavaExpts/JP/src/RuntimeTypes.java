public class RuntimeTypes {

  public static void main(String [] args ) {

    Number m = (Number) (new Integer(2));
    Integer k = (Integer) m;
    Integer i = numtoint( m );
    System.out.println("i is " + i );

    Number d = (Number) (new Double(2.0d));
    // fails, because  d's runtime type is integer
    Integer l = (Integer) d;
    Integer j = numtoint( d );
    System.out.println("j is " + i );

    // compile fails, because c's compile-time time is integer
    Double x = (Integer) new Integer(123);
    // double x = (double) new Integer(123);
  }

  static Integer numtoint( Number n ) {
    System.out.println("n's class is " + n.getClass());
    Integer i = (Integer) n;
    return i;
  }
}



