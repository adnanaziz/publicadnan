// Example 67 from page 49 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

class Example67 {
  private static final HashMap<String,Integer> wdayNumber = new HashMap<String,Integer>();

  static { 
    int wdayno = 0;
    String[] wdays = 
      { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
    for (String wday : wdays) 
      wdayNumber.put(wday, wdayno++);
  }

  public static void main(String[] args) {
    if (args.length != 2) 
      System.out.println("Usage: Example67 <weekday> <hours>\n");
    else {
      double hours = Double.parseDouble(args[1]);
      Integer index = wdayNumber.get(args[0]);
      switch (index != null ? index : -1) {
      case 0: 
        System.out.format("Monday: pay is %.2f%n", 10+7.42*hours);
        break;
      case 1: case 2: case 3: case 4:
        System.out.format("Workday: pay is %.2f%n", 7.42*hours);
        break;
      case 5: case 6:
        System.out.format("Weekend: pay is %.2f%n", 20+1.25*7.42*hours);
        break;
      default:
        System.out.format("Unknown weekday: %s%n", args[0]);
      }
    }
  }
}

