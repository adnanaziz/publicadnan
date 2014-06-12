// Example 151 from page 127 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

// Compile with 
//    javac -encoding Latin1 Examples.java

// On a Unix system, pipe the result through od (octal dump) to study
// the details of the encoding:
//    java Example151 | od -c | less

class Example151 {
  public static void main(String[] args) 
    throws UnsupportedEncodingException 
  {
    System.out.format("*** %-12s %-12s%n", "Encoding", "Canonical name");
    String s = "El Ni�o, s��, �r�sk�bing �, �clair, �2";
    writeIt(s, "US-ASCII");        //  7-bit US ASCII
    writeIt(s, "ISO-8859-1");      //  8-bit ISO Latin 1
    writeIt(s, "UTF-8");           //  8-bit Unicode encoding
    writeIt(s, "UTF-16");          // 16-bit Unicode encoding
    writeIt(s, "UTF-16BE");        // 16-bit Unicode encoding, big-endian
    writeIt(s, "UTF-16LE");        // 16-bit Unicode encoding, little-endian
    writeIt(s, "Cp1252");          // MS Windows codepage 1252
  }

  static void writeIt(String s, String enc) 
    throws UnsupportedEncodingException 
  {
    OutputStreamWriter osw = new OutputStreamWriter(System.out, enc);
    PrintWriter pw = new PrintWriter(osw);
    System.out.format("*** %-12s %-12s%n", enc, osw.getEncoding());
    pw.println(s); pw.println();
    pw.flush();
  }
}

