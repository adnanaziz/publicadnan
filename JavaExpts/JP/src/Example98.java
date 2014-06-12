// Example 98 from page 75 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example98 {
  public static void main(String[] args) {
    printGaussians(100);
  }

  // From http://www.taygeta.com/random/gaussian.html 2001-09-21:
  // The most basic form of the transformation looks like: 
  //               y1 = sqrt( - 2 ln(x1) ) cos( 2 pi x2 )
  //               y2 = sqrt( - 2 ln(x1) ) sin( 2 pi x2 )

  // We start with two independent random numbers, x1 and x2, which
  // come from a uniform distribution (in the range from 0 to 1). Then
  // apply the above transformations to get two new independent random
  // numbers which have a Gaussian distribution with zero mean and a
  // standard deviation of one.

  static void printGaussians(int n) {
    for (int i=0; i<n; i+=2) {
      double x1 = Math.random(), x2 = Math.random();
      print(Math.sqrt(-2 * Math.log(x1)) * Math.cos(2 * Math.PI * x2));
      print(Math.sqrt(-2 * Math.log(x1)) * Math.sin(2 * Math.PI * x2));
    }
  }

  static void print(double d) {
    System.out.println(d);
  }
}

