// Example 19 from page 17 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example19 {
  public static void main(String[] args) {
    Number[] a = new Integer[10];       // Length 10, element type Integer
    Double d = new Double(3.14);        // Type Double,  class Double
    Integer i = new Integer(117);       // Type Integer, class Integer
    Number n = i;                       // Type Number,  class Integer
    a[0] = i;                           // OK, Integer is subtype of Integer
    a[1] = n;                           // OK, Integer is subtype of Integer
    System.out.println("stored a[0] and a[1]");
    a[2] = d;                           // NO, Double not subtype of Integer
    System.out.println("should never get here");
  }
}

