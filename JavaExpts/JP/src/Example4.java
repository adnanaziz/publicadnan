// Example 4 from page 7 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example4 {
  public static void main(String[] args) {
    int i1 = 1000111222, i2 = 40000, i3 = -1;
    floatdouble(i1, i1);                           // L W: 1.00011123E9 1.000111222E9
    bytecharshort((byte)i2, (char)i2, (short)i2);  // C C C: 64 40000 -25536
    bytecharshort((byte)i3, (char)i3, (short)i3);  // C C C: -1 65535 -1
    intint((int)1.9, (int)-1.9);                   // C C: 1 -1
    intint((int)1.5, (int)-1.5);                   // C C: 1 -1
    intint((int)2.5, (int)-2.5);                   // C C: 2 -2
  }

  private static void floatdouble(float f, double d) { 
    System.out.println(f + " " + d);
  }

  private static void bytecharshort(byte b, char c, short s) { 
    System.out.println(b + " " + (int)c + " " + s);
  }

  private static void intint(int i1, int i2) { 
    System.out.println(i1 + " " + i2);
  }
}

