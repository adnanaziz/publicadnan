/*
    @slug
    gcd

    @title
    Compute the GCD

    @problem
    The greatest common divisor (GCD) of positive integers x and y is the largest integer d
    such that d divides x evenly, and d divides y evenly, i.e., x mod d = 0 and y mod d = 0.
    <p>

    Design an efficient algorithm for computing the GCD of two numbers without using
    multiplication, division or the modulus operators.

    @hint
    Use case analysis: both even; both odd; one even and one odd.
 */
package com.epi;

public class GCD1 {
  // @include
  // @judge-include-display
  public static long GCD(long x, long y) {
  // @judge-exclude-display
    if (x == 0) {
      return y;
    } else if (y == 0) {
      return x;
    } else if ((x & 1) == 0 && (y & 1) == 0) { // x and y are even.
      return GCD(x >>> 1, y >>> 1) << 1;
    } else if ((x & 1) == 0 && (y & 1) != 0) { // x is even, y is odd.
      return GCD(x >>> 1, y);
    } else if ((x & 1) != 0 && (y & 1) == 0) { // x is odd, y is even.
      return GCD(x, y >>> 1);
    } else if (x > y) { // Both x and y are odd, and x > y.
      return GCD(x - y, y);
    }
    return GCD(x, y - x); // Both x and y are odd, and x <= y.
  // @judge-include-display
  }
  // @judge-exclude-display
  // @exclude
}
