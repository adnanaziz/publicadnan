import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;

class ThreadedMicroBlogClient implements Runnable {

  String hostname = "localhost";
  String threadname = "X";
  ClientMessage testcase;
  MicroBlogClient sc;

  public ThreadedMicroBlogClient( MicroBlogClient sc, ClientMessage testcase, 
                               String hostname, String threadname ) {
    this.sc = sc;
    this.testcase = testcase;
    this.hostname = hostname;
    this.threadname = threadname;
  }

  public void run() {
    System.out.flush();
    try {
      Socket sock = new Socket(hostname, MicroBlogServer.PORT);
      PrintWriter dos = new PrintWriter(sock.getOutputStream());
      BufferedReader dis = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
      dos.println(testcase);
      dos.flush();
      String response = dis.readLine(); 
      // System.out.println("Client " + threadname + " sent: " + testcase + " received response:" + response);
      dos.close(); 
      dis.close();
      synchronized (sc) {
        sc.result = response;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // System.out.println("finishing " + threadname + " run method");
  }
}


public class MicroBlogClient {
  ThreadedMicroBlogClient tc; 
  String result = "dummy";
  public boolean success;

  MicroBlogClient( ClientMessage testcase, String hostname, String threadname ) {
    tc = new ThreadedMicroBlogClient( this, testcase, hostname, threadname );
  }

  MicroBlogClient( ClientMessage testcase, String name ) {
    this( testcase, "localhost", name );
  }

  MicroBlogClient( ClientMessage testcase ) {
    this( testcase, "localhost", "unnamed client" );
  }
  
  void solve() {
    Thread tcThread = new Thread( tc );
    // System.out.println("starting client thread");
    tcThread.start();
    try {
      tcThread.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // System.out.println("joined client thread");
  }
}
