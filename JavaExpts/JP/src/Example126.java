// Example 126 from page 99 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

class Example126 {
  private static final HashMap<String,Integer> wdayNumber = new HashMap<String,Integer>();

  static { // Static initializer block, executed once
    int wdayno = 0;
    String[] wdays = 
      { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
    for (String wday : wdays) 
      wdayNumber.put(wday, wdayno++);
  }

  public static int wdayno5(String wday) {
    Integer res = wdayNumber.get(wday);
    return res == null ? -1 : res;
  }

  public static void main(String[] args) {
    System.out.println("Thursday is " + wdayno5("Thursday"));
  }
}


