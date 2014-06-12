// Example 134 from page 105 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;

class Time implements Comparable<Time> {
  public final int hh, mm;           // 24-hour clock
  
  public Time(int hh, int mm) { this.hh = hh; this.mm = mm; }

  // Return neg if this before o; return pos if this after o; return zero if same
  public int compareTo(Time t) {
    return hh != t.hh ? hh - t.hh : mm - t.mm;
  }

  public boolean equals(Object o) {
    if (this == o)                                    // fast, frequent case 
      return true;
    if (o == null || this.getClass() != o.getClass()) // null or not same type
      return false;
    Time t = (Time)o;                                 // here o instanceof Time
    return hh == t.hh && mm == t.mm;
  }

  public int hashCode() { return 60 * hh + mm; }

  public String toString() 
  { return (hh < 10 ? "0"+hh : ""+hh) + ":" + (mm < 10 ? "0"+mm : ""+mm); }
}

class TimeComparator implements Comparator<Time> {
  // Return neg if t1 before t2; return pos if t1 after t2; return zero if same
  public int compare(Time t1, Time t2) {
    return t1.hh != t2.hh ? t1.hh - t2.hh : t1.mm - t2.mm;
  }
}

class Example134 {
  public static void main(String[] args) {
    SortedMap<Time,String> datebook = new TreeMap<Time,String>();
    datebook.put(new Time(12, 30), "Lunch");
    datebook.put(new Time(15, 30), "Afternoon coffee break");
    datebook.put(new Time( 9,  0), "Lecture");
    datebook.put(new Time(13, 15), "Board meeting");
    SortedMap<Time,String> pm = datebook.tailMap(new Time(12, 0));
    for (Map.Entry<Time,String> entry : pm.entrySet()) 
      System.out.println(entry.getKey() + " " + entry.getValue());
  }
}

