// Example 68 from page 51 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example68 {
  public static void main(String[] args) {
    for (int i=1; i<=4; i++) {
      for (int j=1; j<=i; j++)
        System.out.print("*");
      System.out.println();
    }
  }
}

