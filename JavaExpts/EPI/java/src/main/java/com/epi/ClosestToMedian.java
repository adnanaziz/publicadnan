package com.epi;

import java.util.*;

public class ClosestToMedian {

  // @include
  public static int[] findKClosestToMedian(int[] A, int k) {
    final double median = findMedian(A);

    // We will use this custom comparator to reorder the array
    // so that elements close to the median come to the front
    // of the array.
    Comparator<Integer> cmp = new Comparator<Integer>() {
        // Compare a and b based on how close they are to median.
        public int compare(Integer a, Integer b) {
            // Note the use of closure to access the variable median, which
            // is in the outer scope --- closure greatly simplifies the code.
            // return Double.compare(Math.abs(a - median), Math.abs(b - median));
            int result = Double.compare(Math.abs(a - median), Math.abs(b - median));
            return result;
        }
    };

    OrderStatistic.findKth(A, k, cmp);

    // Since findKth reordered A so that elements closest in absolute value
    // to median have been moved to the front of A, the first k entries 
    // are the result.
    return Arrays.copyOfRange(A, 0, k);
  }

  // Promote the return value to double to prevent precision error.
  public static double findMedian(int[] A) {
    int half = A.length / 2 + 1;
    OrderStatistic.findKthSmallest(A, half);

    if ((A.length % 2) != 0) { // A has an odd number elements.
      return A[half-1];
    } else { // A has an even number elements.
      int x = A[half-1];
      OrderStatistic.findKthSmallest(A, half - 1);
      return 0.5 * (x + A[half - 2]);
    }
  }
  // @exclude

  private static void checkAns(int[] A, int[] res, int k) {
    Arrays.sort(A);
    double median = ((A.length & 1) != 0)
                        ? A[(A.length / 2)]
                        : 0.5 * (A[((A.length / 2) - 1)] +
                                 A[(A.length / 2)]);
    List<Double> temp = new ArrayList<>();
    for (int a : A) {
      temp.add(Math.abs(median - a));
    }
    Collections.sort(temp);
    for (int r : res) {
      assert(Math.abs(r - median) <= temp.get(k - 1));
    }

    List<Integer> Alist = new ArrayList<>();
    for (int a : A) {
        Alist.add(a);
    }
    Set<Integer> AAsSet = new TreeSet<>(Alist);
    for (int a : res) {
        assert(AAsSet.contains(a));
    }
  }

  private static void simpleTest() {
    int[] d = new int[]{3, 2, 3, 5, 7, 3, 1};
    int[] dRes = findKClosestToMedian(d, 3);
    checkAns(d, dRes, 3);
    d = new int[]{0, 9, 2, 9, 8};
    dRes = findKClosestToMedian(d, 2);
    System.out.print(Arrays.toString(dRes));
    checkAns(d, dRes, 2);
  }

  static int[] toIntArray(List<Integer> list){
    int[] result = new int[list.size()];
    for(int i = 0; i < result.length; i++) {
      result[i] = list.get(i);
    }
    return result;
  }

  private static void RandomTestFixedN(int N) {
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

    int[] A = new int[N];

    Random r = new Random();
    for (int i = 0; i < N; ++i) {
        A[i] = r.nextInt(10000000);
    }
    testMultipleK(A, order);
    for (int i = 0; i < N; ++i) {
        A[i] = r.nextInt(N);
    }
    testMultipleK(A, order);
    for (int i = 0; i < N; ++i) {
        A[i] = r.nextInt(2*N);
    }
    testMultipleK(A, order);
    for (int i = 0; i < N; ++i) {
        A[i] = r.nextInt(Math.max(N/2,1));
    }
    testMultipleK(A, order);
  }

  private static void testMultipleK(int[] A, List<Integer> order) {
    for (int k : order) {
        test(A, k);
    }
  }  

  private static void test(int[] A, int k) {
      int[] res = findKClosestToMedian(A, k);
      assert(res.length == k);
      checkAns(A, res, k);
  }


private static void ComplexRandomTest() {
    int[] N = new int[]{1,2,3,4,5,6,7,8,9,10,15,20,50,100};
    for (int i = 0; i < N.length; i++) {
      for (int j = 0; j < 100; j++) {
        RandomTestFixedN(N[i]);
      }
    }
  }

  public static void main(String[] args) {
    simpleTest();
    ComplexRandomTest();
    Random r = new Random();
    for (int times = 0; times < 10000; ++times) {
      int n, k;
      if (args.length == 1) {
        n = Integer.parseInt(args[0]);
        k = r.nextInt(n) + 1;
      } else if (args.length == 2) {
        n = Integer.parseInt(args[0]);
        k = Integer.parseInt(args[1]);
      } else {
        n = r.nextInt(10000) + 1;
        k = r.nextInt(n) + 1;
      }
      n = r.nextInt(6) + 1;
      k = r.nextInt(n) + 1;
      List<Integer> a = new ArrayList<>();
      for (int i = 0; i < n; ++i) {
        a.add(r.nextInt(n * 2));
      }
      int[] res = findKClosestToMedian(toIntArray(a), k);
      assert(res.length == k);
      System.out.println("n = " + n + ", k = " + k);
      System.out.println("res = " + Arrays.toString(res));
      System.out.println("checking:" + a + " " + k);
      checkAns(toIntArray(a), res, k);
    }
  }
}
