// Example 121 from page 93 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

class Example121 {
  public static void main(String[] args) {
    List<String> list1 = new LinkedList<String>();
    list1.add("list"); list1.add("dup"); list1.add("x"); list1.add("dup"); 
    traverse(list1);                    // Must print: list dup x dup 

    List<String> list2 = new ArrayList<String>();
    list2.add("list"); list2.add("dup"); list2.add("x"); list2.add("dup"); 
    traverse(list2);                    // Must print: list dup x dup 

    Set<String> set1 = new HashSet<String>();
    set1.add("set"); set1.add("dup"); set1.add("x"); set1.add("dup"); 
    traverse(set1);                     // May print:  x dup set 

    SortedSet<String> set2 = new TreeSet<String>();
    set2.add("set"); set2.add("dup"); set2.add("x"); set2.add("dup"); 
    traverse(set2);                     // Must print: dup set x

    LinkedHashSet<String> set3 = new LinkedHashSet<String>();
    set3.add("set"); set3.add("dup"); set3.add("x"); set3.add("dup"); 
    traverse(set3);                     // Must print: set dup x 

    Map<String,String> m1 = new HashMap<String,String>();
    m1.put("map", "J"); m1.put("dup", "K"); m1.put("x", "M"); m1.put("dup", "L"); 
    traverse(m1.keySet());              // May print:  x dup map 
    traverse(m1.values());              // May print:  M L J  

    SortedMap<String,String> m2 = new TreeMap<String,String>();
    m2.put("map", "J"); m2.put("dup", "K"); m2.put("x", "M"); m2.put("dup", "L"); 
    traverse(m2.keySet());              // Must print: dup map x
    traverse(m2.values());              // Must print: L J M

    LinkedHashMap<String,String> m3 = new LinkedHashMap<String,String>();
    m3.put("map", "J"); m3.put("dup", "K"); m3.put("x", "M"); m3.put("dup", "L"); 
    traverse(m3.keySet());              // Must print: map dup x 
    traverse(m3.values());              // Must print: J L M  
  }

  static void traverse(Collection<String> coll) {
    for (String elem : coll) 
      System.out.print(elem + " ");
    System.out.println();
  }
}

