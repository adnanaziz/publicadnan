// Example 76 from page 55 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example76 {
  public static void main(String[] args) {
    System.out.println("Thursday is " + wdayno3("Thursday"));
  }

  static int wdayno3(String wday) {
    for (int i=0; i < wdays.length; i++)
      if (wday.equals(wdays[i]))
        return i+1;
    return -1;                                  // Here used to mean `not found'
  }

  static final String[] wdays = 
  { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
}

