// Example 51 from page 39 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example51 {
  public static void main(String[] args) {
    System.out.println(absolute(-12));
    System.out.println(absolute(12));
    System.out.println(absolute(0));
  }

  // Returns the absolute value of x (always non-negative)
  static double absolute(double x) 
  { return (x >= 0 ? x : -x); }
}

