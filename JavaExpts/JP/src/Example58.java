// Example 58 from page 45 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example58 {
  double m(int i) { return i; }
  boolean m(boolean b) { return !b; }
  static double m(int x, double y) { return x + y + 1; }
  static double m(double x, double y) { return x + y + 3; }

  public static void main(String[] args) { 
    System.out.println(m(10, 20));              // Prints 31.0
    System.out.println(m(10, 20.0));            // Prints 31.0
    System.out.println(m(10.0, 20));            // Prints 33.0
    System.out.println(m(10.0, 20.0));          // Prints 33.0
  }
}

