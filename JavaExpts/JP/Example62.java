// Example 62 from page 47 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example62 {
  public static void main(String[] args) {
    System.out.println("Infinite loop!  Stop it by pressing ctrl-C\n\n");
    int i=0;
    while (i<10);
      i++;
  }
}

