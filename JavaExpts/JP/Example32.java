// Example 32 from page 25 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Access {
  private static int x; 

  static class SI {
    private static int y = x;       // access private x from enclosing class
  }

  static void m() { 
    int z = SI.y;                   // access private y from nested class
  }
}

