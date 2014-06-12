// Example 140 from page 111 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example140 {
  public static void main(String[] args) throws IOException {
    System.out.println("Type some lines of text, end with an empty line:");
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    int count = 0;
    String s = r.readLine();
    while (s != null && !s.equals("")) {
      count++;
      s = r.readLine();
    }
    System.out.println("You entered " + count + " non-empty lines");
  }
}

