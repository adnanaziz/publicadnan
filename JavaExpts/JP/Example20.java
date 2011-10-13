// Example 20 from page 17 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example20 {
  public static void main(String[] args) {
    System.out.println("August 31 is legal: " + checkdate(8, 31));
    System.out.println("April 31 is legal:  " + checkdate(4, 31));
  }

  static int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
 
  static boolean checkdate(int mth, int day) 
  { return (mth >= 1) && (mth <= 12) && (day >= 1) && (day <= days[mth-1]); }
}

