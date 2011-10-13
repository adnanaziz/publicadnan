// Example 2 from page 3 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example2 {                          // Class declaration
  int x;

  Example2(int x) { 
    this.x = x;                         // One-line body 
  } 

  int sum(int y) {                      // Multi-line body
    if (x > 0) {                        // If statement
      return x + y;                     // Single statement 
    } else if (x < 0) {                 // Nested if-else, block statement
      int res = -x + y;
      return res * 117;
    } else { // x == 0                  // Terminal else, block statement 
      int sum = 0;                      
      for (int i=0; i<10; i++) {        // For loop
        sum += (y - i) * (y - i);
      }
      return sum;
    }
  }

  static boolean checkdate(int mth, int day) {
    int length;
    switch (mth) {                      // Switch statement
    case 2:                             // Single case
      length = 28; break;
    case 4: case 6: case 9: case 11:    // Multiple case
      length = 30; break;
    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
      length = 31; break;
    default:
      return false;
    }
    return (day >= 1) && (day <= length);
  }
}

