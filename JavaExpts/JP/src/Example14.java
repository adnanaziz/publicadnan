// Example 14 from page 13 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.Random;

class Example14 {
  public static void main(String[] args) {
    Random rnd = new Random();                       // Random number generator
    for (int i=0; i<3; i++) {
      for (int j=0; j<5; j++)
        System.out.format("%4d", rnd.nextInt(1000)); // Random integer 0..999
      System.out.format("%n");                       // Newline
    }
  }
}

