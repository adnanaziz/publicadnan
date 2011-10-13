// Example 49 from page 39 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example49 {
  public static void main(String[] args) {
    Point p1 = new Point(10, 20);
    System.out.println("p1 is " + p1);        // Prints: p1 is (10, 20)
    Point p2 = p1;                            // p1 and p2 are same object
    p2.move(8, 8);                            
    System.out.println("p2 is " + p2);        // Prints: p2 is (18, 28)
    System.out.println("p1 is " + p1);        // Prints: p1 is (18, 28)
  }
}

