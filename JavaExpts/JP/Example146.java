// Example 146 from page 119 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example146 {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) 
      System.out.println("Usage: java Example146 <filename>");
    else
      sumlines(args[0]);
  }

  static void sumlines(String filename) throws IOException {
    LineNumberReader lnr = new LineNumberReader(new FileReader(filename));
    lnr.setLineNumber(1);
    StreamTokenizer stok = new StreamTokenizer(lnr);
    stok.eolIsSignificant(true);
    stok.nextToken();
    while (stok.ttype != StreamTokenizer.TT_EOF) {
      int lineno = lnr.getLineNumber();
      //      System.out.println(stok.lineno() + " " + lineno);
      double sum = 0;
      while (stok.ttype != StreamTokenizer.TT_EOL) {
        if (stok.ttype == StreamTokenizer.TT_NUMBER)
          sum += stok.nval;
        stok.nextToken();
      }
      System.out.println("Sum of line " + lineno + " is " + sum);
      stok.nextToken();                                
    }
  }
}

