// Example 115 from page 87 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

// Generic quicksort in functional style (the most efficient one)

class Example115 {
  public static void main(String[] args) {
    Integer[] ia = { 5, 7, 3, 9, 12, 45, 4, 8 };
    qsort(ia, new IntegerComparator(), 0, ia.length-1);
    Example115.<Integer>qsort(ia, new IntegerComparator(), 0, ia.length-1);
    Example115.qsort(ia, new IntegerComparator(), 0, ia.length-1);
    for (int i : ia)
      System.out.print(i + "   ");
    System.out.println();
    String[] sa = { "New York", "Rome", "Dublin", "Riyadh", "Tokyo" };
    Example115.qsort(sa, new StringReverseComparator(), 0, sa.length-1);
    for (String s : sa)
      System.out.print(s + "   ");
    System.out.println();
  }

  // Generic functional-style quicksort: sorts arr[a..b]
  
  private static <T> void qsort(T[] arr, Comparator<T> cmp, int a, int b) {
    if (a < b) { 
      int i = a, j = b;
      T x = arr[(i+j) / 2];             
      do {                              
        while (cmp.compare(arr[i], x) < 0) i++;     
        while (cmp.compare(x, arr[j]) < 0) j--;     
        if (i <= j) {
          T tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;    
          i++; j--;                     
        }                             
      } while (i <= j);                 
      qsort(arr, cmp, a, j);                 
      qsort(arr, cmp, i, b);                 
    }                                   
  }
}

// Comparators for Integer and String

class IntegerComparator implements Comparator<Integer> {
  public int compare(Integer v1, Integer v2) {
    return v1 < v2 ? -1 : v1 > v2 ? +1 : 0;
  }
}

class StringReverseComparator implements Comparator<String> {
  public int compare(String v1, String v2) {
    return v2.compareTo(v1);
  }
}


