// Example 123 from page 97 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

// The method unique takes a collection of file names and returns the
// list without duplicates, but retaining the order of the given ones.

class Example123 {
  public static void main(String[] args) {
    String[] filenames = 
      { "Lib.java", "Foo.java", "Bar.java", "Lib.java", "Foo.java", "Goo.java" };
    for (String filename : unique(filenames)) 
      System.out.println(filename);
  }

  public static String[] unique(String[] filenames) { 
    LinkedHashSet<String> uniqueFiles = new LinkedHashSet<String>();
    for (String filename : filenames) 
      uniqueFiles.add(filename);
    return uniqueFiles.toArray(new String[0]);
  }
}

