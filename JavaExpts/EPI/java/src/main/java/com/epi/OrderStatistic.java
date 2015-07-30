package com.epi;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public class OrderStatistic {
  // @include
  private static class Compare {
    private static class GreaterThan implements Comparator<Integer> {
      public int compare(Integer a, Integer b) {
        return (a > b) ? -1 : (a == b) ? 0 : 1;
      }
    }
    public static final GreaterThan GREATER_THAN = new GreaterThan();
    // @exclude

    private static class LessThan implements Comparator<Integer> {
      public int compare(Integer a, Integer b) {
        return (a < b) ? -1 : (a == b) ? 0 : 1;
      }
    }
    public static final LessThan LESS_THAN = new LessThan();
  }

  // @include
  // The numbering starts from one, i.e., if A = [3,1,-1,2] then 
  // findKthLargest(A, 1) returns 3, findKthLargest(A, 2) returns 2,
  // findKthLargest(A, 3) returns 1, and findKthLargest(A, 4) returns -1.
  public static int findKthLargest(ArrayList<Integer> A, int k ) {
    return findKth(A, k, Compare.GREATER_THAN);
  }

  // @exclude

  // The numbering starts from one, i.e., if A = [3,1,-1,2] then 
  // findKthSmallest(A, 1) returns -1, findKthSmallest(A, 2) returns 1,
  // findKthSmallest(A, 3) returns 2, and findKthSmallest(A, 4) returns 3.
  public static int findKthSmallest(ArrayList<Integer> A, int k ) {
    return findKth(A, k, Compare.LESS_THAN);
  }

  // @include
  public static int findKth(ArrayList<Integer> A, int k, Comparator<Integer> cmp) {
    int left = 0, right = A.size() - 1;
    Random r = new Random(0);
    while (left <= right) {
      // Generates a random integer in [left, right].
      int pivotIdx = r.nextInt(right - left + 1) + left;
      int newPivotIdx = partitionAroundPivot(left, right, pivotIdx, A, cmp);
      if (newPivotIdx == k - 1) {
        return A.get(newPivotIdx);
      } else if (newPivotIdx > k - 1) {
        right = newPivotIdx - 1;
      } else { // newPivotIdx < k - 1.
        left = newPivotIdx + 1;
      }
    }
    // @exclude
    throw new NoSuchElementException("no k-th node in array A");
    // @include
  }

  /**
   * Partitions {@code A[left : right]} around {@code pivotIdx}, returns the new index of the
   * pivot, {@code newPivotIdx}, after partition. After partitioning,
   * {@code A[left : newPivotIdx - 1]} contains elements that are less than the
   * pivot, and {@code A[newPivotIdx + 1 : right]} contains elements that are greater
   * than the pivot.
   * 
   * <b>Note:</b> "Greater than" is defined by the comparator object.
   * @return new index of the pivot after partition
   */
  private static int partitionAroundPivot(int left, int right, int pivotIdx,
                                          ArrayList<Integer> A, Comparator<Integer> cmp) {
    int pivotValue = A.get(pivotIdx);
    int newPivotIdx = left;

    swap(A, pivotIdx, right);
    for (int i = left; i < right; ++i) {
      if (cmp.compare(A.get(i), pivotValue) < 0) {
        swap(A, i, newPivotIdx++);
      }
    }
    swap(A, right, newPivotIdx);
    return newPivotIdx;
  }

  private static void swap(ArrayList<Integer> A, int a, int b) {
    int temp = A.get(a);
    A.set(a, A.get(b));
    A.set(b, temp);
  }
  // @exclude

  private static void PivotTest() {
    ArrayList<Integer> A = Lists.newArrayList(3, 3, 3, 2, 5, 1, 7);
    int newIdx = partitionAroundPivot(1, 3, 2, A, Compare.LESS_THAN);
  }

  private static void SimpleTestKthSmallest() {
    ArrayList<Integer> A = Lists.newArrayList(3, 1, 2, 0, 4, 6, 5);
    assert(0 == findKthSmallest(A, 1));
    assert(1 == findKthSmallest(A, 2));
    assert(2 == findKthSmallest(A, 3));
    assert(3 == findKthSmallest(A, 4));
    assert(4 == findKthSmallest(A, 5));
    assert(5 == findKthSmallest(A, 6));
    assert(6 == findKthSmallest(A, 7));
    A.set(2, 6);
    assert(6 == findKthSmallest(A, 6));
    assert(6 == findKthSmallest(A, 7));
    assert(5 == findKthSmallest(A,5));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    // -7 0 0 3 4 4 6 10 12
    assert(-7 == findKthSmallest(A, 1));
    
    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(0 == findKthSmallest(A, 2));
    
    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(0 == findKthSmallest(A, 3));
    
    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(3 == findKthSmallest(A, 4));
    
    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(4 == findKthSmallest(A, 5));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(4 == findKthSmallest(A, 6));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(6 == findKthSmallest(A, 7));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(10 == findKthSmallest(A, 8));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(12 == findKthSmallest(A, 9));

    assert(4 == findKthSmallest(A, 6));
    for(int i = 0; i < A.size(); i++) {
        if (i < 4) {
            assert(A.get(i) < 4);
        } else if (i > 5) {
            assert(A.get(i) > 4);
        }
    }
  }


  private static void simpleTestKthLargest() {
    ArrayList<Integer> A = Lists.newArrayList(3, 1, 2, 0, 4, 6, 5);
    assert(6 == findKthLargest(A, 1));
    assert(5 == findKthLargest(A, 2));
    assert(4 == findKthLargest(A, 3));
    assert(3 == findKthLargest(A, 4));
    assert(2 == findKthLargest(A, 5));
    assert(1 == findKthLargest(A, 6));
    assert(0 == findKthLargest(A, 7));
    A.set(2, 6);
    assert(6 == findKthLargest(A, 1));
    assert(6 == findKthLargest(A, 2));
    assert(5 == findKthLargest(A, 3));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    // 12 10 6 4 4 3 0 0 -7
    assert(12 == findKthLargest(A, 1));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(10 == findKthLargest(A, 2));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(6 == findKthLargest(A, 3));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(4 == findKthLargest(A, 4));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(4 == findKthLargest(A, 5));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(3 == findKthLargest(A, 6));

    A = Lists.newArrayList(0,-7,3,4,4,12,6,10,0);
    assert(4 == findKthLargest(A, 5));
    for(int i = 0; i < A.size(); i++) {
        if (i<3) {
            assert(A.get(i) > 4);
        } else if (i > 4) {
            assert(A.get(i) < 4);
        }
    }
  }

  private static void SimpleTest() {
    ArrayList<Integer> C = Lists.newArrayList(9, 5);
    assert(9 == findKthLargest(C, 1));
    assert(5 == findKthSmallest(C, 1));

    ArrayList<Integer> B = Lists.newArrayList(3, 2, 3, 5, 7, 3, 1);
    int c = findKthSmallest(B, 4);

    ArrayList<Integer> A = Lists.newArrayList(123);
    assert(123 == findKthLargest(A, 1));
  }

  private static void RandomTextFixedN(int N) {
    List<Integer> order = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
        order.add(Math.min(N,i+1));
    }
    order.add(Math.min(N,7));
    order.add(Math.min(N,9));
    order.add(Math.min(N,12));
    order.add(Math.min(N,Math.max(N/2 -1,1)));
    order.add(Math.min(N,Math.max(N/2,1)));
    order.add(Math.min(N,N/2+1));
    order.add(Math.max(1,N-1));
    order.add(N);
    
    ArrayList<Integer> A = new ArrayList<>(N);
    Random r = new Random(0);
    for (int i = 0; i < N; ++i) {
        A.add(r.nextInt(10000000));
    }
    testAllOrders(A, order);
    
    A.clear();
    for (int i = 0; i < N; ++i) {
        A.add(r.nextInt(N));
    }
    testAllOrders(A, order);
    
    A.clear();
    for (int i = 0; i < N; ++i) {
        A.add(r.nextInt(2*N));
    }
    testAllOrders(A, order);
    
    A.clear();
    for (int i = 0; i < N; ++i) {
        A.add(r.nextInt(Math.max(N/2, 1)));
    }
    testAllOrders(A, order);
  }

  private static void testAllOrders(ArrayList<Integer> A, List<Integer> order) {
    for (int K : order) {
        checkOrderStatistic(A, K, true);
        checkOrderStatistic(A, K, false);
    }
  }

  private static void reverse(int[] data) {
    for (int left = 0, right = data.length - 1; left < right; left++, right--) {
        // swap the values at the left and right indices
        int temp = data[left];
        data[left]  = data[right];
        data[right] = temp;
    }
  }

  private static void checkOrderStatistic(ArrayList<Integer> A, int K, boolean increasingOrder) {
    ArrayList<Integer> B = new ArrayList<>(A);
    if (increasingOrder) {
      findKthSmallest(A, K);
    } else {
      findKthLargest(A, K);
    }

    ArrayList<Integer> Bsort = new ArrayList(B);
    Collections.sort(Bsort);
    if (!increasingOrder) {
        Collections.reverse(Bsort);
    }

    ArrayList<Integer> Asort = new ArrayList<>(A);
    Collections.sort(Asort);
    if (!increasingOrder) {
        Collections.reverse(Asort);
    }

    for (int i = 0; i < Asort.size(); i++) {
        assert(Asort.get(i).equals(Bsort.get(i)));
    }

    Set<Integer> Asmall = new TreeSet<Integer>();
    Set<Integer> Bsmall = new TreeSet<Integer>();
    for (int i = 0; i < K; i++) {
        Asmall.add(A.get(i));
        Bsmall.add(Bsort.get(i));
    }
    assert(Asmall.equals(Bsmall));

    Set<Integer> Abig = new TreeSet<Integer>();
    Set<Integer> Bbig = new TreeSet<Integer>();
    for (int i = K; i < A.size(); i++) {
        Abig.add(A.get(i));
        Bbig.add(Bsort.get(i));
    }
    assert(Abig.equals(Bbig));

  }

  private static void ComplexRandomTest() {
    int[] N = new int[]{1,2,3,4,5,6,7,8,9,10,15,20,50,100};
    for (int i = 0; i < N.length; i++) {
      for (int j = 0; j < 100; j++) {
        RandomTextFixedN(N[i]);
      }
    }
  }

  public static void main(String[] args) {
    Integer.compare(1, 2);
    
    ArrayList<Integer> list = Lists.newArrayList(9, 1, 3, 4, 5, 7, 2, 8, 6);
    partitionAroundPivot(0, 8, 0, list, Compare.LESS_THAN);
    System.out.println(list);
    
    Collections.sort(list, Compare.LESS_THAN);
    System.out.println(list);
    /*
    SimpleTest();
    PivotTest();
    SimpleTestKthSmallest();
    ComplexRandomTest();
    System.out.println("Finished ComplexRandomTest()");
    Random r = new Random();
    for (int times = 0; times < 1000; ++times) {
      int n, k;
      if (args.length == 1) {
        n = Integer.parseInt(args[0]);
        k = r.nextInt(n) + 1;
      } else if (args.length == 2) {
        n = Integer.parseInt(args[0]);
        k = Integer.parseInt(args[1]);
      } else {
        n = r.nextInt(100000) + 1;
        k = r.nextInt(n - 1) + 1;
      }
      ArrayList<Integer> A = new ArrayList<>(n);
      for (int i = 0; i < n; ++i) {
        A.add(r.nextInt(10000000));
      }
      int result = findKthLargest(A, k);
      Collections.sort(A);
      assert(result == A.get(A.size() - k));
    }
    */
  }
}
