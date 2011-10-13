// Example 152 from page 129 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;
import java.util.*;

class Example152 {
  public static void main(String[] args) throws IOException {
    final int count = 40000;
    System.out.println("Writing " + count + " strings to random access string array file");
    Iterable<String> strGenerator =
      new Iterable<String>() { 
        public Iterator<String> iterator() { 
          return new Iterator<String>() {
            int i = 0;
            public boolean hasNext() { return i < count; }
            
            public String next() { return "string" + i++; }
            
            public void remove() { }
          };
        }
      };          
    writeStrings("saf.dat", strGenerator);
  }

  static void writeStrings(String filename, Iterable<String> strGenerator) 
    throws IOException {
    RandomAccessFile raf = new RandomAccessFile(filename, "rw");
    raf.setLength(0);                                   // truncate the file
    ArrayList<Long> offsettable = new ArrayList<Long>();
    for (String s : strGenerator) {
      offsettable.add(raf.getFilePointer());            // store string offset
      raf.writeUTF(s);                                  // write string
    }
    for (long offset : offsettable) 
      raf.writeLong(offset);
    raf.writeInt(offsettable.size());                   // write string count
    raf.close();
  }
}


