// Example 145 from page 119 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example145 {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) 
      System.out.println("Usage: java Example145 <filename>");
    else
      sumfile(args[0]);
  }

  static void sumfile(String filename) throws IOException {
    Reader r = new BufferedReader(new FileReader(filename));
    StreamTokenizer stok = new StreamTokenizer(r);
    double sum = 0;                                       
    stok.nextToken();                                  
    while (stok.ttype != StreamTokenizer.TT_EOF) {
      if (stok.ttype == StreamTokenizer.TT_NUMBER)
        sum += stok.nval;
      else
        System.out.println("Non-number: " + stok.sval);
      stok.nextToken();                              
    }
    System.out.println("The file sum is " + sum);
  }                                     
}

