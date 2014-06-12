// Example 102 from page 77 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.Random;

class Example102 {
  public static void main(String[] args) {
    if (args.length != 1) 
      System.out.println("Usage: java Example102 <length>\n");
    else {
      System.out.println("Timing character replacement, abusing a string buffer:");
      Random rnd = new Random();
      int length = Integer.parseInt(args[0]);
      char[] cbuf = new char[length];
      for (int i=0; i<length; i++) 
        cbuf[i] = (char)(65 + rnd.nextInt(26));
      String s = new String(cbuf);
      for (int i=0; i<10; i++) {
        StringBuilder sb = new StringBuilder(s);
        Timer t = new Timer();
        replaceCharString(sb, 'A', "HA");
        System.out.print(t.check() + " ");
      }
      System.out.println();
    } 
  }

  // In-place replacement in a StringBuilder; very inefficient and strange

  static void replaceCharString(StringBuilder sb, char c1, String s2) {
    int i = 0;                                  // Inefficient
    while (i < sb.length()) {                   // Inefficient
      if (sb.charAt(i) == c1) {                 // Inefficient
        sb.replace(i, i+1, s2);                 // Inefficient
        i += s2.length();                       // Inefficient
      } else                                    // Inefficient
        i += 1;                                 // Inefficient
    }                                           // Inefficient
  }

  private final static class Timer {
    private long starttime;
    
    public Timer() { starttime = System.currentTimeMillis(); }
    
    double check() {
      return (System.currentTimeMillis()-starttime)/1000.0;
    }
  }
}

