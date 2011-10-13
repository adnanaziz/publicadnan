// Example 116 from page 87 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


// Generic quicksort in object-oriented style

class Example116 {
  public static void main(String[] args) {
    String[] sa = { "New York", "Rome", "Dublin", "Riyadh", "Tokyo" };
    qsort(sa, 0, sa.length-1);
    for (String s : sa)
      System.out.print(s + "   ");
    System.out.println();
  }

  // Generic object-oriented style quicksort: sorts arr[a..b]
  
  private static <T extends Comparable<T>> void qsort(T[] arr, int a, int b) {
    if (a < b) { 
      int i = a, j = b;
      T x = arr[(i+j) / 2];             
      do {                              
        while (arr[i].compareTo(x) < 0) i++;     
        while (x.compareTo(arr[j]) < 0) j--;     
        if (i <= j) {
          T tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;    
          i++; j--;                     
        }                             
      } while (i <= j);                 
      qsort(arr, a, j);                 
      qsort(arr, i, b);                 
    }                                   
  }
}

