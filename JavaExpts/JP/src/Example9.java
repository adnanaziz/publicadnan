// Example 9 from page 11 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example9 {
  public static void main(String[] args) {
    String res = "";
    for (int i=0; i<args.length; i++)
      res += args[i];
    System.out.println(res);
  }
}

