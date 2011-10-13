// Example 97 from page 75 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example97 {
  public static void main(String[] args) {
    for (int i=0; i<=100; i++) 
      System.out.println(i + "! = " + fact(i));
  }

  static double fact(int n) {
    double res = 0.0;
    for (int i=1; i<=n; i++) 
      res += Math.log(i);
    return Math.exp(res);
  }
}

