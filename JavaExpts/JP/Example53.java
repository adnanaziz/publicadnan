// Example 53 from page 41 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example53 {
  public static void main(String[] args) {
    C c1 = new C(100);                  // c1 has type C; object has class C
    B b1 = c1;                          // b1 has type B; object has class C
    print(C.sf,  B.sf);                 // Prints 102 121 
    print(c1.sf, b1.sf);                // Prints 102 121 
    print(c1.vf, b1.vf);                // Prints 100 120
    C c2 = new C(200);                  // c2 has type C; object has class C
    B b2 = c2;                          // b2 has type B; object has class C
    print(c2.sf, b2.sf);                // Prints 202 221 
    print(c2.vf, b2.vf);                // Prints 200 220
    print(c1.sf, b1.sf);                // Prints 202 221 
    print(c1.vf, b1.vf);                // Prints 100 120
    D d3 = new D(300);                  // d3 has type D; object has class D
    C c3 = d3;                          // c3 has type C; object has class D
    B b3 = d3;                          // b3 has type B; object has class D
    print(D.sf,  C.sf,  B.sf);          // Prints 304 304 361
    print(d3.sf, c3.sf, b3.sf);         // Prints 304 304 361
    print(d3.vf, c3.vf, b3.vf);         // Prints 300 340 360
  }

  static void print(int x, int y) { System.out.println(x+" "+y); }
  static void print(int x, int y, int z) { System.out.println(x+" "+y+" "+z); }
}

