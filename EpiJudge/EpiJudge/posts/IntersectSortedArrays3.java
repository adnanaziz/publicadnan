// Copyright (c) 2015 Elements of Programming Interviews. All rights reserved.

/*
    @slug 
    intersect-two-sorted-arrays

    @title 
    Intersect two sorted arrays.

    @problem
    Write a program which takes as input two sorted arrays, and returns a new array
    containing elements that are present in both of the input arrays. The input arrays
    may have duplicate entries, but the returned array should be free of duplicates. For
    example, the input is <2, 3, 3, 5, 5, 6, 7, 7, 8, 12> and <5, 5, 6, 8, 8, 9, 10, 10>, your output
    should be <5, 6, 8>.

    @hint
    Solve the problem if the input array lengths differ by orders of magnitude. What if they
    are approximately equal?

 */

package com.epi;

import java.util.ArrayList;
import java.util.List;

public class IntersectSortedArrays3 {
  // @include
  // @judge-include-display
  public static List<Integer> intersectTwoSortedArrays(List<Integer> A,
                                                       List<Integer> B) {
  // @judge-exclude-display
    List<Integer> intersectionAB = new ArrayList<>();
    int i = 0, j = 0;
    while (i < A.size() && j < B.size()) {
      if (A.get(i) == B.get(j) && (i == 0 || A.get(i) != A.get(i - 1))) {
        intersectionAB.add(A.get(i));
        ++i;
        ++j;
      } else if (A.get(i) < B.get(j)) {
        ++i;
      } else { // A.get(i) > B.get(j).
        ++j;
      }
    }
    return intersectionAB;
  // @judge-include-display
  }
  // @judge-exclude-display
  // @exclude
}
