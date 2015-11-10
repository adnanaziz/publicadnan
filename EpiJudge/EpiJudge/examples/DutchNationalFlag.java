// Copyright (c) 2015 Elements of Programming Interviews. All rights reserved.

// package com.epi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.lang.reflect.*;

/*

 @slug DNF

 @Summary Reorder an array into elements less than, equal to and greater than a key.

 @Tags: Arrays OffByOne InPlace

 @Description  The quicksort algorithm for sorting arrays proceeds recursively—it selects an element
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
<p>

As an example, assuming that black precedes white and white precedes gray,
Figure 2.1(b) is a valid partitioning for Figure 2.1(a).
If gray precedes black and black precedes white, Figure 2.1(c)
is a valid partitioning for Figure 2.1(a).
<br>
<img src="/dnf.png"></img>
<br>

Write a program that takes an array A and an index i into A, and rearranges the
elements such that all elements less than A[i] (the "pivot") appear first, followed by
elements equal to the pivot, followed by elements greater than the pivot.
  
 */

//@skeleton-begin
class Solution {
  // This is what is presented to the user on EPIJudge. It must be compilable, so if, 
  // for example, the function returns a List, we must return a list. This is
  // not a major overhead, since null is fine to return.

  //TODO(A): how can we share types, e.g., DutchNationalFlag.Color, between this class
  // and our model solution? Maybe bring out the Color class as a top-level type?
  public static void dutchFlagPartition(int pivotIndex, List<DutchNationalFlag.Color> A) {
    // Write me.
  }

}
//@skeleton-end

//@judge-begin

// This is the class that implements the directed tests used by the judge. These tests 
// are a subset of the tests for the model solution we've written, since the complete
// suite often takes a long time to run, and uses random test cases which are not
// super helpful.
//
// We'd like to use the testing framework here in the tests for the model solution.
// This is tricky, because it means we need to be able to switch between the Solution class,
// and the DutchNationalFlag class. The best way to do this is to have them implement
// a common interface (or use inheritance), and pass the corresponding object to the JudgeTest constructor, 
// defer calls to dutchFlagPartition() to that object. However, this seems unnatural to me.
// I've illustrated a different solution, see below for details.
class JudgeTest {

  static boolean checker(List<DutchNationalFlag.Color> A) {
    DutchNationalFlag.Color constraint = DutchNationalFlag.Color.RED;
    for (int i = 0; i < A.size(); i++) {
        DutchNationalFlag.Color current = A.get(i);
        if (current.compareTo(constraint) < 0) {
            return false;
        } else if (current.compareTo(constraint) > 0) {
            constraint = current;
        }
    }
    return true;
  }

  void singleTest(int index, List<DutchNationalFlag.Color> tc) {
    ArrayList<DutchNationalFlag.Color> tcMutable = new ArrayList<DutchNationalFlag.Color>(tc);
    dutchFlagPartition(index, tcMutable);
    if (!checker(tcMutable)) {
        System.err.println("Failed test case:" + tc + ", pivot = " + index);
        System.err.println("\tYour partitioning yielded: " + tcMutable);
        System.exit(-1);
    }
  } 

  // This will be of type Solution (when using EPI Judge) or DutchNationalFlag (for the 
  // code we provide in the book).
  Object candidate;

  // We defer dutchFlagPartition() to candidate. Since the type of candidate is Object,
  // we need to reflexively invoke this method.
  void dutchFlagPartition(int index, List<DutchNationalFlag.Color> A) {
    try {
        System.out.println(candidate.getClass());
        Method[] list = candidate.getClass().getDeclaredMethods();
        for (Method m : list) {
            System.out.println(m.getName());
            System.out.println(Arrays.toString(m.getParameterTypes()));
        }
        // Use Integer.TYPE for primitive types
        Method method = candidate.getClass().getMethod("dutchFlagPartition", Integer.TYPE, List.class);
        // static method, so put null first
        method.invoke(null, index, A);
    } catch (Exception e) {
        e.printStackTrace();
        System.exit(-1);
    }
  }

  JudgeTest(Object obj) {
    this.candidate = obj;
  }

  public void testSuite() {
    List<DutchNationalFlag.Color> tc1 = Arrays.asList(DutchNationalFlag.Color.RED, DutchNationalFlag.Color.BLUE, DutchNationalFlag.Color.RED, DutchNationalFlag.Color.WHITE, DutchNationalFlag.Color.BLUE);
    singleTest(2, tc1);
    List<DutchNationalFlag.Color> tc2 = Arrays.asList(DutchNationalFlag.Color.RED);
    singleTest(0,tc2);
    System.out.println("All DNF tests passed");
  }

  // EPI Judge will run this. Try javac DutchNationalFlag; java JudgeTest and you will run the solution written in by the user in the EPI Judge.
  // Try javac DutchNationalFlag; java DutchNationalFlag and you will run our solution (and include the tests in JudgeTest
  public static void main(String[] args) {
    new JudgeTest(new Solution()).testSuite();
  }

}
//@skeleton-end

//@judge-begin

public class DutchNationalFlag {
  // @include
  public static enum Color { RED, WHITE, BLUE }

  public static void dutchFlagPartition(int pivotIndex, List<Color> A) {
    Color pivot = A.get(pivotIndex);

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
  }
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
    new JudgeTest(new DutchNationalFlag()).testSuite();
    Random gen = new Random();

    for (int times = 0; times < 1000; ++times) {
      int n;
      if (args.length == 1) {
        n = Integer.parseInt(args[0]);
      } else {
        n = gen.nextInt(100) + 1;
      }

      List<Color> A = randArray(n);

      int pivotIndex = gen.nextInt(n);
      Color pivot = A.get(pivotIndex);

      dutchFlagPartition(pivotIndex, A);

      int i = 0;
      while (i < n && A.get(i).ordinal() < pivot.ordinal()) {
        System.out.print(A.get(i) + " ");
        ++i;
      }
      while (i < n && A.get(i) == pivot) {
        System.out.print(A.get(i) + " ");
        ++i;
      }
      while (i < n && A.get(i).ordinal() > pivot.ordinal()) {
        System.out.print(A.get(i) + " ");
        ++i;
      }
      System.out.println();

      assert(i == n);
    }
  }
}
