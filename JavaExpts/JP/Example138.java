// Example 138 from page 109 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

class Example138 {
  public static void main(String[] args) {
    Set<Set<Integer>> ss = new HashSet<Set<Integer>>();
    ss.add(mkIntegerSet(new int[] { 2, 3 }));
    ss.add(mkIntegerSet(new int[] { 1, 3 }));
    ss.add(mkIntegerSet(new int[] { 1, 2 }));
    System.out.println("ss = " + ss);
    Set<Set<Integer>> tt = intersectionClose(ss);
    System.out.println("tt = " + tt);
  }

  static Set<Integer> mkIntegerSet(int[] a) {
    TreeSet<Integer> ts = new TreeSet<Integer>();
    for (int i=0; i<a.length; i++)
      ts.add(a[i]);
    return ts;
  }

  // Given a set ss of sets of T, compute its intersection
  // closure, that is, the least set tt such that ss is a subset of tt
  // and such that for any two sets t1 and t2 in tt, their
  // intersection is also in tt.  

  // For instance, if ss is {{2,3}, {1,3}, {1,2}}, 
  // then tt is {{2,3}, {1,3}, {1,2}, {3}, {2}, {1}, {}}.

  // Both the argument and the result is a Set of Set of T.

  static <T> Set<Set<T>> intersectionClose(Set<Set<T>> ss) {
    LinkedList<Set<T>> worklist = new LinkedList<Set<T>>(ss);
    Set<Set<T>> tt = new HashSet<Set<T>>();
    while (!worklist.isEmpty()) {
      Set<T> s = worklist.removeLast();
      for (Set<T> t : tt) {
        Set<T> ts = new TreeSet<T>(t);
        ts.retainAll(s);                // ts is the intersection of t and s
        if (!tt.contains(ts)) 
          worklist.add(ts);
      }
      tt.add(s);
    }
    return tt;
  }
}

