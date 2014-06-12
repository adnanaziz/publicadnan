// Example 18 from page 17 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example18 {
  public static void main(String[] args) {
    // Roll a die, count frequencies
    int[] freq = new int[6];                    // all initialized to 0
    for (int i=0; i<1000; i++) {
      int die = (int)(1 + 6 * Math.random());
      freq[die-1] += 1;
    }
    for (int c=1; c<=6; c++)
      System.out.println(c + " came up " + freq[c-1] + " times");
    
    // Create an array of the strings "A0", "A1", ..., "A19"
    String[] number = new String[20];           // all initialized to null
    for (int i=0; i<number.length; i++)
      number[i] = "A" + i;
    for (int i=0; i<number.length; i++)
      System.out.println(number[i]);
  }
}

