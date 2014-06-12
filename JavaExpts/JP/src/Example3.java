// Example 3 from page 5 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example3 {
  public static void main(String[] args) {
    if (args.length != 1) 
      System.out.println("Usage: Example3 [1|2|3]\n");
    else {
      Boolean bb1 = false, bb2 = !bb1;    // Example3 to [false] [true]
      Integer bi1 = 117;                  // Example3 to [117]
      Double bd1 = 1.2;                   // Example3 to [1.2]
      boolean b1 = bb1;                   // Unboxing, result false
      if (bb1)                            // Unboxing, result false
        System.out.println("Not true");
      int i1 = bi1 + 2;                   // Unboxing, result 119
      // short s = bi1;                   // Illegal
      long l = bi1;                       // Legal: int is subtype of long
      System.out.println(i1);
      Integer bi2 = bi1 + 2;              // Unboxing, boxing, result [119]
      System.out.println(bi2);
      Integer[] biarr = { 2, 3, 5, 7, 11 };
      int sum = 0;
      for (Integer bi : biarr) 
        sum += bi;                        // Unboxing in loop body
      for (int i : biarr)                 // Unboxing in loop header
        sum += i;    
      System.out.println(sum);            // Result 56
      int i = 1934;
      Integer bi4 = i, bi5 = i;
      // Prints: true true true false
      System.out.format("%b %b %b %b%n", i==i, bi4==i, i==bi5, bi4==bi5);
      if (args[0].equals("1")) {
        Boolean bbn = null;
        boolean b = bbn;                  // Compiles OK, fails at run-time
      } else if (args[0].equals("2")) {
        Boolean bbn = null;
        if (bbn)                          // Compiles OK, fails at run-time
          System.out.println("Not true");
      } else if (args[0].equals("3")) {
        Integer bin = null;
        Integer bi6 = bin + 2;            // Compiles OK, fails at run-time
      }
    }
  }
}

