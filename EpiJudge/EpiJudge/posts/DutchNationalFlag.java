// Copyright (c) 2015 Elements of Programming Interviews. All rights reserved.

/*
    @slug 
    dutch-national-flag

    @title 
    Dutch National Flag Partitioning

    @context
    The quicksort algorithm for sorting arrays proceeds recursively-it selects an element
    (the "pivot"), reorders the array to make all the elements less than or equal to the pivot
    appear first, followed by all the elements greater than the pivot. The two subarrays
    are then sorted recursively.
    <p>

    Implemented naively, quicksort has large run times and deep function call stacks
    on arrays with many duplicates because the subarrays may differ greatly in size. One
    solution is to reorder the array so that all elements less than the pivot appear first,
    followed by elements equal to the pivot, followed by elements greater than the pivot.
    This is known as Dutch national flag partitioning, because the Dutch national flag
    consists of three horizontal bands, each in a different color.
    As an example, assuming that black precedes white and white precedes gray,
    Figure 2.1(b) is a valid partitioning for Figure 2.1(a).
    If gray precedes black and black precedes white, Figure 2.1(c)
    is a valid partitioning for Figure 2.1(a).
    <br>
    <img src="/dnf.png"></img>
    <br>

    @summary 
    Reorder an array into elements less than, equal to and greater than a key.  <b>#Arrays #OffByOne #InPlace</b>

    @problem
    Write a program that takes an array A and an index i into A, and rearranges the
    elements such that all elements less than A[i] (the "pivot") appear first, followed by
    elements equal to the pivot, followed by elements greater than the pivot.
    <p>

    @hint 
    It's possible to do this in O(n) time and O(1) space.

 */

// epi py script to remove @judge directives
// 
// judge py script reads whole file. main will run entirely (?).
// we will import java.util.*, strip leading package and import statements
// questions: 
//   how is the user supplied part executed?
//   what if user adds classes? they will be inner classes here, is that a problem? 
//      (i think we will have to make methods nonstatic to get away with this)
// package com.epi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class DutchNationalFlag {
  // @include
  // @judge-include-display
  public static enum Color { RED, WHITE, BLUE }

  public void dutchFlagPartition(int pivotIndex, List<Color> A) {
  // @judge-exclude-display

    /**
     * Keep the following invariants during partitioning:
     * bottom group: A.subList(0, smaller).
     * middle group: A.subList(smaller, equal).
     * unclassified group: A.subList(equal, larger).
     * top group: A.subList(larger, A.size()).
     */
    int smaller = 0, equal = 0, larger = A.size();
    // Keep iterating as long as there is an unclassified element.
    while (equal < larger) {
      // A.get(equal) is the incoming unclassified element.
      if (A.get(equal).ordinal() < pivot.ordinal()) {
        Collections.swap(A, smaller++, equal++);
      } else if (A.get(equal).ordinal() == pivot.ordinal()) {
        ++equal;
      } else { // A.get(equal) > pivot.
        Collections.swap(A, equal, --larger);
      }
    }
    // @judge-include-display
  }
    // @judge-exclude-display

    // @exclude

  private static List<Color> randArray(int len) {
    Random r = new Random();
    List<Color> ret = new ArrayList<>(len);
    for (int i = 0; i < len; ++i) {
      ret.add(Color.values()[r.nextInt(3)]);
    }
    return ret;
  }

  public static void main(String[] args) {
    Random gen = new Random();

    for (int times = 0; times < 10; ++times) {
      int n = 10;
        
      try {
        if (args.length == 1) {
          n = Integer.parseInt(args[0]);
        } else {
          n = gen.nextInt(100) + 1;
        }
      } catch (Exception e) {
        // user provided bad input (or compilebox gave us a -) so we skip
      }

      List<Color> A = randArray(n);

      int pivotIndex = gen.nextInt(n);
      Color pivot = A.get(pivotIndex);

      new DutchNationalFlag().dutchFlagPartition(pivotIndex, A);

      check(pivot, A);


    }
  }

  public static void check(Color pivot, List<Color> A) {
      int n = A.size();
      int i = 0;
      while (i < n && A.get(i).ordinal() < pivot.ordinal()) {
        //System.out.print(A.get(i) + " ");
        ++i;
      }
      while (i < n && A.get(i) == pivot) {
        //System.out.print(A.get(i) + " ");
        ++i;
      }
      while (i < n && A.get(i).ordinal() > pivot.ordinal()) {
        //System.out.print(A.get(i) + " ");
        ++i;
      }
      System.out.println();

      if (i != n) {
        System.err.println("Failed test, saw " + A);
        System.exit(-1);
      }
    }
}
