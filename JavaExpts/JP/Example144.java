// Example 144 from page 117 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Example144 {
  public static void main(String[] args) 
    throws IOException
  {
    Expr expr = mkAddition(4);
    expr.output(System.out); System.out.println();      // To standard output
    StringBuilder sb = new StringBuilder();             // To StringBuilder
    expr.output(sb);
    String s = sb.toString();                           // To String
    System.out.println(s);
    Writer wr = new FileWriter("expr.txt");
    System.out.println("Writing to file expr.txt");
    expr.output(wr); wr.append('\n');                   // To text file
    wr.close();
  }

  public static Expr mkAddition(int n) {
    if (n == 0)
      return new Cst(17);
    else { 
      Expr e = mkAddition(n-1);
      return new Add(new Cst(n), new Add(e, e));
    }
  }
}

abstract class Expr {
  public abstract void output(Appendable sink) throws IOException;
}

class Cst extends Expr {
  private final int i;
  public Cst(int i) {
    this.i = i;
  }
  public void output(Appendable sink) throws IOException {
    sink.append(Integer.toString(i));
  }
}

class Add extends Expr {
  Expr e1, e2;
  public Add(Expr e1, Expr e2) {
    this.e1 = e1; this.e2 = e2;
  }
  public void output(Appendable sink) throws IOException {
    sink.append('('); e1.output(sink); sink.append('+'); 
    e2.output(sink); sink.append(')'); 
  }
}

