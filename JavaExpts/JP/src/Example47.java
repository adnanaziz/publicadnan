// Example 47 from page 37 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example47 {
  public static void main(String[] args) throws Exception {  
    int a = 0x3;                        // Bit pattern   0011
    int b = 0x5;                        // Bit pattern   0101
    println4(a);                        // Prints        0011
    println4(b);                        // Prints        0101
    println4(~a);                       // Prints        1100
    println4(~b);                       // Prints        1010
    println4(a & b);                    // Prints        0001
    println4(a ^ b);                    // Prints        0110
    println4(a | b);                    // Prints        0111
  }

  static void println4(int n) {
    for (int i=3; i>=0; i--)
      System.out.print(n >> i & 1);
    System.out.println();
  }
}

