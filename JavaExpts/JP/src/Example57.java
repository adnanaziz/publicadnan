// Example 57 from page 43 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example57 {
  public static void main(String[] args) {
    Point p = new Point(10, 20);
    int[] a = new int[5];
    int d = 8;
    System.out.println("p is " + p);          // Prints: p is (10, 20)
    System.out.println("a[3] is " + a[3]);    // Prints: a[3] is 0
    m(p, d, a);                            
    System.out.println("p is " + p);          // Prints: p is (18, 28)
    System.out.println("d is " + d);          // Prints: d is 8
    System.out.println("a[3] is " + a[3]);    // Prints: a[3] is 22
  }

  static void m(Point pp, int dd, int[] aa) {
    pp.move(dd, dd);
    dd = 117;
    aa[3] = 22;
  }
}


