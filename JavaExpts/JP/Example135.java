// Example 135 from page 105 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.util.*;
import java.io.*;

// Strings that differ only in case should be next to each other, with
// upper case before lower case.  

class IgnoreCaseComparator implements Comparator<String> {
  public int compare(String s1, String s2) {
    int res = s1.compareToIgnoreCase(s2);
    return res != 0 ? res : s1.compareTo(s2);
  }
}

// A concordance is a TreeMap mapping a word (a String) to a TreeSet
// of linenumbers (Integer objects)

class Example135 {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) 
      System.out.println("Usage: java Example135 <filename>");
    else {
      SortedMap<String,SortedSet<Integer>> index = buildIndex(args[0]);
      printIndex(index);
    }  
  }

  // Parse alphanumeric words from the given file, and record them in
  // the index.  The resulting index is a SortedMap from String to
  // SortedSet of Integer (the line numbers).

  static SortedMap<String,SortedSet<Integer>> buildIndex(String filename) 
    throws IOException 
  {
    Reader r = new BufferedReader(new FileReader(filename));
    StreamTokenizer stok = new StreamTokenizer(r);
    stok.quoteChar('"'); stok.ordinaryChars('!', '/');
    stok.nextToken();
    SortedMap<String,SortedSet<Integer>> index 
      = new TreeMap<String,SortedSet<Integer>>(new IgnoreCaseComparator());
    while (stok.ttype != StreamTokenizer.TT_EOF) {
      if (stok.ttype == StreamTokenizer.TT_WORD) {
        SortedSet<Integer> ts;
        if (index.containsKey(stok.sval))       // If word has a set, get it
          ts = index.get(stok.sval);
        else {
          ts = new TreeSet<Integer>();          // Otherwise create one
          index.put(stok.sval, ts);
        }
        ts.add(stok.lineno());
      }
      stok.nextToken();                              
    }
    return index;
  }

  // Print the concordance index by iterating over its entries, and
  // for each entry, iterate over the value which is a set.

  static void printIndex(SortedMap<String,SortedSet<Integer>> index) {
    for (Map.Entry<String,SortedSet<Integer>> entry : index.entrySet()) {
      System.out.print(entry.getKey() + ": ");
      SortedSet<Integer> lineNoSet = entry.getValue();
      for (int lineno : lineNoSet) 
        System.out.print(lineno + " ");
      System.out.println();
    }
  }
}

