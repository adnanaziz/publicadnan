// Example 100 from page 77 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example100 {
  public static void main(String[] args) {
    StringBuilder res = new StringBuilder();
    for (int i=0; i<args.length; i++)
      res.append(args[i]);
    System.out.println(res.toString());
  }
}

