// Example 165 from page 141 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import static java.lang.System.*;                  // For field out
import static java.lang.Math.*;                    // For methods log, exp
import java.util.*;                                // For class LinkedList<T>

class Example165 {
  public static void main(String[] args) {
    double giga = exp(30.0 * log(2.0));
    out.println("2^30 = " + giga);                 // 2^30 = 1.073741823999999E9
    out.format("2^30 = %12.2f\n", giga);           // 2^30 = 1073741824.00
    String res = concat("Cop", "en", "hagen");  
    out.format("result = %s\n", res);              // result = Copenhagen
    Direction dir = Direction.North;
    dir = dir.turnLeft();
    out.println(dir);                              // West
    List<Double> list = new ArrayList<Double>();   // Generic collection type
    list.add(7.2); list.add(22.4); list.add(-9.2); // Boxing to Double
    for (double d : list)                          // Unboxing to double
      out.format("%+7.1f\n", d);                   // Fixed-width formatting
  }

  static String concat(String... ss) {             // Variable-arity method
    StringBuilder sb = new StringBuilder();        // StringBuilder
    for (String s : ss)                            // ss is a String array
      sb.append(s);
    return sb.toString();
  }

  enum Direction {
    East, North, West, South;                      // Enum values

    public Direction turnLeft() {                  // Member method
      switch (this) {                              // Switch on enum value
      case East:  return Direction.North;
      case North: return Direction.West;
      case West:  return Direction.South;
      case South: return Direction.East;
      default: throw new RuntimeException("Impossible");
      }
    }
  }
}
