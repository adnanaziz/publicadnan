// Example 150 from page 127 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

class Timer {
  private long starttime;

  public Timer() { starttime = System.currentTimeMillis(); }

  double check() {
    return (System.currentTimeMillis()-starttime)/1000.0;
  }
}

class Example150 {
  public static void main(String[] args) throws IOException {
    OutputStream os1 = new FileOutputStream("tmp1.dat");
    writeints("Unbuffered FileOutputStream: ", 1000000, os1);
    OutputStream os2 = new BufferedOutputStream(new FileOutputStream("tmp2.dat"));
    writeints("Buffered FileOutputStream:   ", 1000000, os2);
    Writer wr1 = new FileWriter("tmp1.dat");
    writeints("Unbuffered FileWriter: ", 1000000, wr1);
    Writer wr2 = new BufferedWriter(new FileWriter("tmp2.dat"));
    writeints("Buffered FileWriter:   ", 1000000, wr2);
  }

  static void writeints(String msg, int count, OutputStream os) 
    throws IOException {
    Timer t = new Timer();
    for (int i=0; i<count; i++)
      os.write(i & 255);
    os.close();
    System.out.println(msg + t.check());
  }

  static void writeints(String msg, int count, Writer os) 
    throws IOException {
    Timer t = new Timer();
    for (int i=0; i<count; i++)
      os.write(i & 255);
    os.close();
    System.out.println(msg + t.check());
  }
}

