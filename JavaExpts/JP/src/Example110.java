// Example 110 from page 83 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

// A constraint may involve type parameters 
// A type may have multiple constraints 

class ComparablePair<T extends Comparable<T>, U extends Comparable<U>>
  implements Comparable<ComparablePair<T,U>>
{
  public final T fst;
  public final U snd;
  
  public ComparablePair(T fst, U snd) {
    this.fst = fst; this.snd = snd;
  }
  
  // Lexicographic ordering
  public int compareTo(ComparablePair<T,U> that) {
    int firstCmp = this.fst.compareTo(that.fst);
    return firstCmp != 0 ? firstCmp : this.snd.compareTo(that.snd);
  }

  public String toString() {
    return "(" + fst + ", " + snd + ")";
  }
}

// Sorting soccer world champions by country and year

class Example110 {
  public static void main(String[] args) {
    ArrayList<ComparablePair<String,Integer>> lst 
      = new ArrayList<ComparablePair<String,Integer>>();
    lst.add(new ComparablePair<String,Integer>("Brazil", 2002));
    lst.add(new ComparablePair<String,Integer>("Italy", 1982));
    lst.add(new ComparablePair<String,Integer>("Argentina", 1978 ));
    lst.add(new ComparablePair<String,Integer>("Argentina", 1986 ));
    lst.add(new ComparablePair<String,Integer>("Germany", 1990));
    lst.add(new ComparablePair<String,Integer>("Brazil", 1994));
    lst.add(new ComparablePair<String,Integer>("France", 1998));
    Collections.sort(lst);
    for (ComparablePair<String,Integer> pair : lst) 
      System.out.println(pair);
  }
}

