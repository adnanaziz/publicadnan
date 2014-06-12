// Example 91 from page 67 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Incrementer extends Thread {
  public int i;
  public void run() { 
    for (;;) {                                    // Forever 
      i++;                                        // increment i
      yield(); 
    }
} }

class Example91 {
  public static void main(String[] args) throws IOException {
    Incrementer u = new Incrementer();
    u.start();
    System.out.println("Repeatedly press Enter to get the current value of i:");
    for (;;) {
      System.in.read();                         // Wait for keyboard input
      System.out.println(u.i); 
} } }

