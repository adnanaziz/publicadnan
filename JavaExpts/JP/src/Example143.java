// Example 143 from page 117 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example143 {
  public static void main(String[] args) throws IOException {
    fahrenheitCelsiusHtml();
    fahrenheitCelsiusText();
  }

  public static void fahrenheitCelsiusHtml() throws IOException {
    System.out.println("Printing temperature table to file temperature.html");
    PrintWriter pw = new PrintWriter(new FileWriter("temperature.html"));
    pw.println("<TABLE BORDER><TR><TH>Fahrenheit<TH>Celsius</TR>");
    for (int f=100; f<=400; f+=10) {
      double c = 5 * (f - 32.0) / 9;
      pw.format("<TR ALIGN=RIGHT><TD>%d<TD>%.1f%n", f, c);
    }
    pw.println("</TABLE>");
    pw.close();                 // Without this, the output file may be empty
  }

  public static void fahrenheitCelsiusText() throws IOException {
    System.out.println("Printing temperature table to file temperature.txt");
    PrintWriter pw = new PrintWriter(new FileWriter("temperature.txt"));
    pw.println("Fahrenheit   Celsius");
    for (int f=100; f<=400; f+=10) {
      double c = 5 * (f - 32.0) / 9;
      pw.format("%10d%10.1f%n", f, c);
    }
    pw.close();                 // Without this, the output file may be empty
  }
}

