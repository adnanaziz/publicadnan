// Example 104 from page 79 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

class Example104 {
  public static void main(String[] args) {
    ArrayList cool = new ArrayList();
    cool.add(new Person("Kristen"));
    cool.add(new Person("Bjarne"));
    cool.add(new Exception("Larry"));   // Wrong, but no compiletime check
    cool.add(new Person("Anders"));
    Person p = (Person)(cool.get(2));   // Compiles OK, but fails at runtime
  }

  private static class Person {
    private static int counter = 0;
    private final String name;
    private final int serialNumber;
    
    public Person(String name) {
      this.name = name;
      this.serialNumber = counter++;
    }
  }
}

