// Example 69 from page 51 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example69 {
  public static void main(String[] args) {
    int[] iarr = { 2, 3, 5, 7, 11 };
    int sum = 0;
    for (int i : iarr) 
      sum += i;
    System.out.println(sum);
  }
}

