// Example 17 from page 15 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.Date;
import java.util.Locale;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

class Example17 {
  public static void main(String[] args) 
    throws UnsupportedEncodingException 
  {
    PrintWriter wr =
      new PrintWriter(new OutputStreamWriter(System.out, "ISO-8859-1"));
    double d = 1234567.9;
    wr.format(Locale.US,      "%,.2f%n", d);        // en_US locale
    wr.format(Locale.GERMANY, "%,.2f%n", d);        // de_DE locale
    wr.format(Locale.FRANCE,  "%,.2f%n", d);        // fr_FR locale
    wr.flush();
  }
}

