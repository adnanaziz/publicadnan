// Example 75 from page 53 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example75 {
  public static void main(String[] args) {
    System.out.println("Counting sum of eyes until 5 or 6 comes up (10000 dice).");
    int[] wait = new int[1000];
    for (int i=0; i<10000; i++)
      wait[waitsum()]++;
    System.out.println("sum: frequency");
    for (int w=5; w<20; w++)
      System.out.println(w + ": " + wait[w]);
  }
  
  // Roll a die and compute sum until five or six comes up
  static int waitsum() {
    int sum = 0, eyes;
    do {
      eyes = (int)(1 + 6 * Math.random());
      sum += eyes;
    } while (eyes < 5);
    return sum;
  }
}

