// Example 7 from page 9 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Scope {
                      // no x in scope
  void m1(int x) {    // declaration of parameter x (#1)
    x = 7;            // x #1 in scope
  }                   //
                      // no x in scope
  void m2(int v2) {   //
    x = 7;            // x #5 in scope
  }                   //
                      // no x in scope
  void m3(int v3) {   //
    x = 7;            // x #5 in scope
    int x;            // declaration of variable x (#2)
    x = 7;            // x #2 in scope
  }                   //
                      // no x in scope
  void m4(int v4) {   // 
    x = 7;            // x #5 in scope
    {                 //
      int x;          // declaration of variable x (#3)
      x = 7;          // x #3 in scope
    }                 // 
    x = 7;            // x #5 in scope
    {                 //
      int x;          // declaration of variable x (#4)
      x = 7;          // x #4 in scope
    }                 // 
    x = 7;            // x #5 in scope
  }                   // 
                      // no x in scope
  int x;              // declaration of field x (#5)
  { x = 7; }          // x #5 in scope
}

