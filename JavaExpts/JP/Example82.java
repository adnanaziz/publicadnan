// Example 82 from page 57 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example82 {
  public static void main(String[] args) throws IOException { 
    double[] xs = readRecord("foo");
    for (int i=0; i<xs.length; i++)
      System.out.println(xs[i]);
  }

  static double[] readRecord(String filename) throws IOException {
    Reader freader         = new FileReader(filename); 
    BufferedReader breader = new BufferedReader(freader);
    double[] res = new double[3];
    try {
      res[0] = new Double(breader.readLine()).doubleValue();
      res[1] = new Double(breader.readLine()).doubleValue();
      res[2] = new Double(breader.readLine()).doubleValue();
    } finally {
      breader.close();
    }
    return res;
  }
}

