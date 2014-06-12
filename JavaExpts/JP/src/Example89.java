// Example 89 from page 65 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class WeekdayException extends Exception {
  static final long serialVersionUID = 50L;
  public WeekdayException(String wday) { 
    super("Illegal weekday: " + wday); 
  }
}

