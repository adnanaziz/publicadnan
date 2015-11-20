package com.epi;

/*
   @slug
    parity

    @title
    Parity

    @context
    The parity of a binary word is 1 if the number of 1s in the word is odd;
   otherwise, it is 0.
    For example, the parity of 1011 is 1, and the parity of 10001000 is 0.
    <p>
    Parity checks are used to detect single bit errors in data storage and
   communication.
    It is fairly straightforward to write code that computes the parity of a
   single 64-bit word.
    <p>

    @summary
    Compute the parity of a long.

    @problem
    How would you compute the parity of a very large number of 64-bit words?

    @hint
    Be prepared to mask and shift.

    @hint2
    Think about the case of many checks.


 */

public class Parity1 {
  // @include
  // @judge-include-display
  public static short parity(long x) {
    // @judge-exclude-display
    short result = 0;
    while (x != 0) {
      result ^= (x & 1);
      // clang-format off
      x >>>= 1;
      // clang-format on
    }
    return result;
    // @judge-include-display
  }
  // @judge-exclude-display
  // @exclude
}
