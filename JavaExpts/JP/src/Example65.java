// Example 65 from page 49 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example65 {
  public static void main(String[] args) {
    System.out.println("44 is " + findCountry(44));
  }

  static String findCountry(int prefix) {
    switch (prefix) {
    case 1:   return "North America";
    case 44:  return "Great Britain";
    case 45:  return "Denmark";
    case 299: return "Greenland";
    case 46:  return "Sweden";
    case 7:   return "Russia";
    case 972: return "Israel";
    default:  return "Unknown";
    }
  }
}

