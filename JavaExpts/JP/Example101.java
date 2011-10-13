// Example 101 from page 77 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.Random;

class Example101 {
  public static void main(String[] args) {
    if (args.length != 1) 
      System.out.println("Usage: java Example101 <length>\n");
    else {
      System.out.println("Timing character replacement in a string:");
      Random rnd = new Random();
      int length = Integer.parseInt(args[0]);
      char[] cbuf = new char[length];
      for (int i=0; i<length; i++) 
        cbuf[i] = (char)(65 + rnd.nextInt(26));
      String s = new String(cbuf);
      for (int i=0; i<10; i++) {
        Timer t = new Timer();
        String res = replaceCharString(s, 'A', "HA");
        System.out.print(t.check() + " ");
      }
      System.out.println();
    } 
  }
  
  static String replaceCharString(String s, char c1, String s2) {
    StringBuilder res = new StringBuilder();
    for (int i=0; i<s.length(); i++)
      if (s.charAt(i) == c1) 
        res.append(s2);
      else
        res.append(s.charAt(i));
    return res.toString();
  }

  private final static class Timer {
    private long starttime;
    
    public Timer() { starttime = System.currentTimeMillis(); }
    
    double check() {
      return (System.currentTimeMillis()-starttime)/1000.0;
    }
  }
}

