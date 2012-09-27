// Example 156 from page 133 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;

class ThreadedSudokuClient implements Runnable {

  String hostname = "localhost";
  String threadname = "X";
  String testcase;
  SudokuClient sc;

  public ThreadedSudokuClient( SudokuClient sc, String testcase, 
                               String hostname, String threadname ) {
    this.sc = sc;
    this.testcase = testcase;
    this.hostname = hostname;
    this.threadname = threadname;
  }

  public void run() {
    System.out.println("starting " + threadname + " run method");
    System.out.flush();
    try {
      Socket sock = new Socket(hostname, SudokuServer.PORT);
      PrintWriter dos = new PrintWriter(sock.getOutputStream());
      BufferedReader dis = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
      dos.println(testcase);
      dos.flush();
      String response = dis.readLine(); 
      // System.out.println("Client " + threadname + " sent: " + testcase + " received response:" + response);
      dos.close(); dis.close();
      synchronized (sc) {
        sc.result = response;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("finishing " + threadname + " run method");
  }
}


public class SudokuClient {
  ThreadedSudokuClient tc; 
  String result = "dummy";
  public boolean success;

  SudokuClient( String testcase, String hostname, String threadname ) {
    tc = new ThreadedSudokuClient( this, testcase, hostname, threadname );
  }

  SudokuClient( String testcase, String name ) {
    this( testcase, "localhost", name );
  }

  SudokuClient( String testcase ) {
    this( testcase, "localhost", "unnamed client" );
  }
  
  void solve() {
    Thread tcThread = new Thread( tc );
    System.out.println("starting client thread");
    tcThread.start();
    try {
      tcThread.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("joined client thread");
    success = SudokuSolver.isLegalSolution( result, tc.testcase );
  }
}


