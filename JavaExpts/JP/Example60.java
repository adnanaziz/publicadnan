// Example 60 from page 45 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class D1 { 
  D1() { m2(); }
  void m1() { System.out.println("D1.m1 "); }
  void m2() { System.out.print("D1.m2 "); m1(); }
}
  
class D2 extends D1 { 
  int f;
  D2() { f = 7; }
  void m1() { System.out.println("D2.m1:" + f); }
}
  
class Example60 {
  public static void main(String[] args) {
    System.out.println("Executing: D1 d1 = new D1()");
    D1 d1 = new D1();           // Prints D1.m2 D1.m1
    System.out.println("Executing: D1 d2 = new D2()");
    D1 d2 = new D2();           // Prints D1.m2 D2.m1:0
    System.out.println("Executing: d1.m2()");
    d1.m2();                    // Prints D1.m2 D1.m1
    System.out.println("Executing: d2.m2()");
    d2.m2();                    // Prints D1.m2 D2.m1:7
} }

