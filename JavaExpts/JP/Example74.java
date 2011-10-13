// Example 74 from page 53 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

class Example74 {
  public static void main(String[] args) {
    Iterable<Integer> ible = fromTo(13, 17);
    Iterator<Integer> iter = ible.iterator();
    while (iter.hasNext()) {
      int i = iter.next();
      System.out.println(i);
    }
  }

  public static Iterable<Integer> fromTo(final int m, final int n) {
    class FromToIterator implements Iterator<Integer> {
      private int i = m;
      public boolean hasNext() { return i <= n; }
      public Integer next() {
        if (i <= n) 
          return i++;  
        else 
          throw new NoSuchElementException();
      }
      public void remove() { throw new UnsupportedOperationException(); }
    }
    class FromToIterable implements Iterable<Integer> {
      public Iterator<Integer> iterator() { 
        return new FromToIterator();
      }
    }
    return new FromToIterable();
  }
}

