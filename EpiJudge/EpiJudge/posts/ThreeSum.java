/*
    @slug
    3sum

    @title
    The three sum problem.

    @problem
    Design an algorithm that takes as input an array and a number, and determines if
    there are three entries in A (not necessarily distinct) which add up to the specified
    number. <p>
    
    For example, if the array is <2, 3, 5, 7, 11> then there are three entries in the
    56 array which add up to 21 (3, 7, 11 and 5, 5, 11). (Note that we can use 5 twice, since
    the problem statement said we can use the same entry more than once.) However, no
    three entries add up to 9.

    @hint
    How would you check if A[i] is part of a triple that 3-creates T?

 */
package com.epi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ThreeSum {
  // @include
  // @judge-include-display
  public static boolean hasThreeSum(List<Integer> A, int t) {
  // @judge-exclude-display
    Collections.sort(A);
    for (Integer a : A) {
      // Finds if the sum of two numbers in A equals to t - a.
      if (TwoSum.hasTwoSum(A, t - a)) {
        return true;
      }
    }
    return false;
  // @judge-include-display
  }
  // @judge-exclude-display
  // @exclude

  // n^3 solution.
  public static boolean checkAns(List<Integer> A, int t) {
    for (int i = 0; i < A.size(); ++i) {
      for (int j = 0; j < A.size(); ++j) {
        for (Integer aA : A) {
          if (A.get(i) + A.get(j) + aA == t) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Random gen = new Random();
    for (int times = 0; times < 1000; ++times) {
      int n, T;
      if (args.length == 1) {
        n = Integer.parseInt(args[0]);
        T = gen.nextInt(n - 1);
      } else {
        n = gen.nextInt(10000) + 1;
        T = gen.nextInt(n - 1);
      }

      List<Integer> A = new ArrayList<>();
      for (int i = 0; i < n; ++i) {
        A.add(gen.nextInt(200000) - 100000);
      }
      System.out.println(hasThreeSum(A, T) ? "true" : "false");
      assert(checkAns(A, T) == hasThreeSum(A, T));
    }
  }
}
