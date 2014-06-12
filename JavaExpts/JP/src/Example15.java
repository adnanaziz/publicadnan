// Example 15 from page 15 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.GregorianCalendar;

class Example15 {
  public static void main(String[] args) {
    GregorianCalendar date = new GregorianCalendar(2004, 8, 14, 12, 9, 28);
    System.out.format("%1$tF %1$tR%n", date);
  }
}

