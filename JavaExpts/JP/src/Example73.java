// Example 73 from page 53 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example73 {
  public static void main(String[] args) {
    if (args.length != 1) 
      System.out.println("Usage:  Example73 <double>\n");
    else {
      double[] arr = { 2, 3, 5, 5, 7, 11, 13 };
      int k = binarySearch(arr, Double.parseDouble(args[0]));
      if (k >= 0) 
        System.out.format("Found at %d%n", k);
      else
        System.out.format("Not found, belongs at %d%n", ~k);
    }
  }

  // Array arr must be sorted in increasing order and may contain
  // duplicates.

  // If we imagine that arr[-1]==minus infinity and
  // arr[arr.length]==plus infinity, then the following invariant
  // holds at beginning and end of the loop body: 
  //    arr[a-1] < x < arr[b+1]

  // The negation of the loop condition is (a>b); and in fact a==b+1
  // and b==a-1 just after the loop.  It follows that 
  //    arr[b] < x < arr[a]
  // just after the loop.  

  // So in case x is not found in the sorted array, we can insert x at
  // arr[a], moving up the elements arr[a...], and rest assured that
  // the resulting array remains sorted.

  // Note that when x is found, then its index i>=0 is returned.  If x
  // is not found, then the one's complement ~a of a is returned;
  // since a>=0, the one's complement is negative.  Hence we can
  // easily distinguish success (x found) from failure (x not found).

  public static int binarySearch(double[] arr, double x) {
    int a=0, b=arr.length-1;
    while (a<=b) { // Loop invariant: arr[a-1] < x < arr[b+1]
      int i = (a+b)/2;
      if (arr[i] < x)      a = i+1;
      else if (arr[i] > x) b = i-1;
      else                 return i;    // because arr[i] == x
    }
    // Now a>b, in fact a==b+1 and b==a-1, and arr[b] < x < arr[a]
    return ~a;
  }
}

