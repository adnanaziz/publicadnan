// Example 63 from page 47 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example63 {
  public static void main(String[] args) {
    System.out.println(absolute(-12));
    System.out.println(absolute(12));
    System.out.println(absolute(0));
  }

  // Behaves the same as absolute on page~\pageref{pgm-absolute-conditional}: 
  static double absolute(double x) { 
    if (x >= 0)
      return x;
    else 
      return -x;
  }
}

