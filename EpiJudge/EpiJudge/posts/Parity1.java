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
    Think about the case where there are many checks.

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

  static String longToBinaryString(long L) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < Long.numberOfLeadingZeros(L); i++) {
      sb.append('0');
    }
    sb.append(Long.toBinaryString(L));
    return sb.toString();
  }

  static void unitTest(long L, short expectedParity) {
    short got = parity(L);
    if (got != expectedParity) {
      System.err.println("Wrong result for " + longToBinaryString(L));
      System.err.println("Expected " + expectedParity);
      System.err.println("Got " + got);
      System.exit(-1);
    }
  }

  // slowest implementation, bit by bit
  private static short parityBitByBit(long x) {
    short result = 0;
    for (int i = 0; i < 64; i++) {
        result ^= 1 & (x >> i);
    }
    return result;
  }

  // very fast implementation, uses associativity
  private static short parityAssoc(long x) {
    x ^= x >>> 32;
    x ^= x >>> 16;
    x ^= x >>> 8;
    x ^= x >>> 4;
    x ^= x >>> 2;
    x ^= x >>> 1;
    return (short)(x & 0x1);
  }

  // another very fast implementation, uses lookuptable
  private static short[] precomputedParity;

  static {
    precomputedParity = new short[1 << 16];
    for (int i = 0; i < (1 << 16); ++i) {
      precomputedParity[i] = Parity1.parity(i);
    }
  }

  public static short parityTable(long x) {
    final int WORD_SIZE = 16;
    final int BIT_MASK = 0xFFFF;
    // clang-format off
    return (short) (
        precomputedParity[(int)((x >>> (3 * WORD_SIZE)) & BIT_MASK)]
        ^ precomputedParity[(int)((x >>> (2 * WORD_SIZE)) & BIT_MASK)]
        ^ precomputedParity[(int)((x >>> WORD_SIZE) & BIT_MASK)]
        ^ precomputedParity[(int)(x & BIT_MASK)]);
    // clang-format on
  }

  private static long stressTestSolution(int N) {
    long startTime = System.nanoTime();
    long totalXor = 0;
    for (long i = 0; i < N; i++) {
        long testcase = i | i << 16 | ~(i << 32) | i << 48;
        totalXor ^= parity(testcase);
    }
    long finishTime = System.nanoTime();
    return finishTime - startTime;
  }

  private static long stressTestBitByBit(int N) {
    long startTime = System.nanoTime();
    long totalXor = 0;
    for (long i = 0; i < N; i++) {
        long testcase = i | i << 16 | ~(i << 32) | i << 48;
        totalXor ^= parityBitByBit(testcase);
    }
    long finishTime = System.nanoTime();
    return finishTime - startTime;
  }

  private static long stressTestTable(int N) {
    long startTime = System.nanoTime();
    long totalXor = 0;
    for (long i = 0; i < N; i++) {
        long testcase = i | i << 16 | ~(i << 32) | i << 48;
        totalXor ^= parityTable(testcase);
    }
    long finishTime = System.nanoTime();
    return finishTime - startTime;
  }

  private static long stressTestAssoc(int N) {
    long startTime = System.nanoTime();
    long totalXor = 0;
    for (long i = 0; i < N; i++) {
        long testcase = i | i << 16 | ~(i << 32) | i << 48;
        totalXor ^= parityAssoc(testcase);
    }
    long finishTime = System.nanoTime();
    return finishTime - startTime;
  }

  public static void stressTest(int N) {
    long userSolutionTime = stressTestSolution(N);
    long tableSolutionTime = stressTestSolution(N);
    long bitByBitSolutionTime = stressTestBitByBit(N);
    long assocSolutionTime = stressTestAssoc(N);
    long SCALE = 1000000L;
    //System.out.println("user, table, bit-by-bit, assoc = " 
    //                        + userSolutionTime/SCALE + ", " + tableSolutionTime/SCALE + ", " 
    //                        + bitByBitSolutionTime/SCALE + ", " + assocSolutionTime/SCALE);
    if (userSolutionTime >  0.5 * bitByBitSolutionTime) {
        System.err.println("Your program fails because it's too slow.");
        System.err.println("Your program took " + userSolutionTime/SCALE + " milliseconds for " + N + " inputs.");
        System.err.println("Your target time should be less than " + tableSolutionTime/SCALE + " milliseconds.");
        System.exit(-1);
    }
  }

  public static void main(String[] args) {
    unitTest(
        0b1000000000000000000000000000000000000000000000000000000000000000L,
        (short)1);
    unitTest(
        0b1000000000000000000000000000000000000000000000000000000000000001L,
        (short)0);
    unitTest(
        0b0000000000000000000000000000000000000000000000000000000000000001L,
        (short)1);
    unitTest(
        0b1111111111111111111111111111111111111111111111111111111111111111L,
        (short)0);
    unitTest(
        0b0111111111111111111111111111111111111111111111111111111111111111L,
        (short)1);
    unitTest(
        0b1010000101000101101000010100010110100001010001011010000101000101L,
        (short)0);
    int N = 10000000;
    stressTest(N);
    System.out.println("You passed all tests!");
  }
}
