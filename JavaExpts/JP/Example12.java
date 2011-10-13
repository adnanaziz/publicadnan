// Example 12 from page 11 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example12 {
  public static void main(String[] args) {
    Point p1 = new Point(10, 20);
    Point p2 = new Point(30, 40);
    System.out.println("p1 is " + p1);        // Prints: p1 is (10, 20)
    System.out.println("p2 is " + p2);        // Prints: p2 is (30, 40)
    p2.move(7, 7);
    System.out.println("p2 is " + p2);        // Prints: p2 is (37, 47)
  }
}

