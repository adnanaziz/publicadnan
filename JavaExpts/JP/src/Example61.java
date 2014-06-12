// Example 61 from page 47 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)


class Example61 {
  public static void main(String[] args) {
    int offset = 10;
    class Pair { 
      public final int fst, snd;
      public Pair(int fst, int snd) { this.fst = fst; this.snd = snd; }
      public String toString() { return String.format("(%d,%d)", fst, snd); }
    }
    {
      Pair p1 = new Pair(10, 10+offset);
      System.out.println(p1); 
    }
    {
      Pair p1 = new Pair(200, 300);
      System.out.println(p1); 
    }
  }
}

