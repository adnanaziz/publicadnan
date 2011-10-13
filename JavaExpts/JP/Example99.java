// Example 99 from page 75 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example99 {
  public static void main(String[] args) {
    mathtest();
  }

  static void mathtest() {
    print("Illegal arguments, NaN results:");
    print(Math.sqrt(-1));               // NaN
    print(Math.log(-1));                // NaN
    print(Math.pow(-1, 2.5));           // NaN
    print(Math.acos(1.1));              // NaN
    print("Infinite results:");
    print(Math.log(0));                 // -Infinity
    print(Math.pow(0, -1));             // Infinity
    print(Math.exp(1000.0));            // Infinity (overflow)
    print("Infinite arguments:");
    double infinity = Double.POSITIVE_INFINITY;
    print(Math.sqrt(infinity));         // Infinity
    print(Math.log(infinity));          // Infinity
    print(Math.exp(-infinity));         // 0.0
    print("NaN arguments and special cases:");
    double nan = Math.log(-1);
    print(Math.sqrt(nan));              // NaN
    print(Math.pow(nan, 0));            // 1.0 (special case)
    print(Math.pow(0, 0));              // 1.0 (special case)
    print(Math.round(nan));             // 0   (special case)
    print(Math.round(1E50));            // 9223372036854775807 (Long.MAX_VALUE)
    // For all (x, y) except (0.0, 0.0):
    // sign(cos(atan2(y, x))) == sign(x) && sign(sin(atan2(y, x))) == sign(y)
    for (double x=-100; x<=100; x+=0.125) {
      for (double y=-100; y<=100; y+=0.125) {
        double r = Math.atan2(y, x);
        if (!(sign(Math.cos(r))==sign(x) && sign(Math.sin(r))==sign(y)))
          print("x = " + x + "; y = " + y);
      }
    }
  }

  static int sign(double x) {
    final double tolerance = 1E-14;
    if (x < -tolerance) 
      return -1;
    else if (x > +tolerance)
      return +1;
    else 
      return 0;
  }

  static void print(String d) {
    System.out.println(d);
  }

  static void print(double d) {
    System.out.println(d);
  }

  static void print(long d) {
    System.out.println(d);
  }
}

