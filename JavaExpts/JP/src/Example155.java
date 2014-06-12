// Example 155 from page 131 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;

// The producer loop terminates by throwing an exception when the
// reader end of the pipe has died

class Example155 {
  public static void main(String[] args) throws IOException {
    PipedOutputStream outpipe = new PipedOutputStream();
    PipedInputStream inpipe = new PipedInputStream(outpipe);
    final DataOutputStream outds = new DataOutputStream(outpipe);
    DataInputStream inds = new DataInputStream(inpipe);

    // This thread outputs primes to outds -> outpipe -> inpipe -> inds
    class Producer extends Thread {
      public void run() {
        try {
          outds.writeInt(2);
          for (int p=3; true; p+=2) {
            int q=3;
            while (q*q <= p && p%q != 0)
              q+=2;
            if (q*q > p) 
              { outds.writeInt(p); System.out.print("."); }
          }
        } catch (IOException e) { System.out.println("<terminated>: " + e); }
      }
    }

    new Producer().start();
    System.out.println("Press Enter for more primes, or ctrl-C to stop");
    System.out.println("Each dot represents the production of one prime");
    for (;;) {                                    // forever
      for (int n=0; n<10; n++)                    //   output 10 primes
        System.out.print(inds.readInt() + " ");   //   and 
      System.in.read();                           //   wait for Enter
    }
  }
}

