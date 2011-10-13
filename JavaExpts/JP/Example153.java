// Example 153 from page 129 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;
import java.util.Random;

class Example153 {
  public static void main(String[] args) throws IOException {
    final int count = 20;
    System.out.println("Reading " + count + " random strings from string array file");
    Random rnd = new Random();
    for (int i=0; i<count; i++) {
      int j = rnd.nextInt(10000);
      try {
        System.out.println(j + ": " + readOneString("saf.dat", j));
      } catch (IOException e) { throw e; }
    }
  }

  static String readOneString(String filename, int i) throws IOException {
    final int INTSIZE = 4, LONGSIZE = 8;
    RandomAccessFile raf = new RandomAccessFile(filename, "r");
    raf.seek(raf.length() - INTSIZE);
    int N = raf.readInt();
    raf.seek(raf.length() - INTSIZE - LONGSIZE * N + LONGSIZE * i);
    long si = raf.readLong();
    raf.seek(si);
    String s = raf.readUTF();    
    raf.close();
    return s;
  }
}

