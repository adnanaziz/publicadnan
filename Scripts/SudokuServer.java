// Example 156 from page 133 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class ThreadedSolver implements Runnable {
  private Socket sock;
  public ThreadedSolver( Socket sock ) {
    this.sock = sock;
  }
  public void run() {
    try {
    BufferedReader dis  = new BufferedReader( 
                              new InputStreamReader(sock.getInputStream()) );
    PrintWriter dos = new PrintWriter(sock.getOutputStream());
    String query = dis.readLine();
    String solverOutput = SudokuSolver.solve(query);
    dos.println(solverOutput);
    dos.flush();
    dis.close(); 
    dos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


class ThreadedSudokuServer implements Runnable {

  public static ExecutorService threadpool = Executors.newFixedThreadPool(SudokuServer.NTHREADS);

  public void run() {
    try {
    ServerSocket serversock = new ServerSocket(SudokuServer.PORT);
    while (true) {
      Socket sock = serversock.accept();
      ThreadedSolver ts = new ThreadedSolver(sock);
      synchronized (threadpool) {
        threadpool.execute( ts );
      }
   }
   } catch (Exception e) {
     e.printStackTrace();
   }
  }
}

class ThreadedSudokuClient implements Runnable {

  String hostname = "localhost";
  String threadname = "X";

  public ThreadedSudokuClient( String hostname, String threadname ) {
    this.hostname = hostname;
    this.threadname = threadname;
  }

  public void run() {
    int count=0;
    for (int i=1; i<3; i++) {
      try {
        Socket sock = new Socket(hostname, SudokuServer.PORT);
        PrintWriter dos = new PrintWriter(sock.getOutputStream());
        BufferedReader dis = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
        String test = SudokuSolver.testCaseString;
        System.out.println("test = " + test );
        dos.println(test);
        dos.flush();
        String response = dis.readLine(); 
        System.out.println("Client " + threadname + " sent: " + test + " received response:" + response);
        dos.close(); dis.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}


@Author(name="Adnan Aziz", uteid="aa375")
public class SudokuServer {
  final static int PORT = 16789;
  final static int NTHREADS = 1;
  final static String testcase = "123 456 789\n";

  public static void main(String[] args) throws IOException {
    boolean server = args.length == 1 && args[0].equals("server");
    boolean client = args.length == 2 && args[0].equals("client");

    if (server) {               // Server: accept questions about solvability
      Runnable serverThread = new ThreadedSudokuServer();
      Thread t = new Thread( serverThread );
      t.start();
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } 
    else if (client) {        // Client: ask questions about solvability
      Runnable clientThreadA = new ThreadedSudokuClient(args[1], "a");
      Runnable clientThreadB = new ThreadedSudokuClient(args[1], "b");
      Runnable clientThreadC = new ThreadedSudokuClient(args[1], "c");
      Runnable clientThreadD = new ThreadedSudokuClient(args[1], "d");
      Thread a = new Thread( clientThreadA ); 
      Thread b = new Thread( clientThreadB ); 
      Thread c = new Thread( clientThreadC ); 
      Thread d = new Thread( clientThreadD ); 
      a.start();
      b.start();
      c.start();
      d.start();
      try {
        a.join();
        b.join();
        c.join();
        d.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {                    // Neither server not client
      System.out.println("Start two copies of this program, possibly on different machines:");
      System.out.println("   java SudokuServer server");
      System.out.println("   java SudokuServer client <serverhostname>");
      System.out.println("Use `java Example156 client localhost' if the"); 
      System.out.println("client and server run on the same machine.");
      System.out.println("You may start several clients all talking to the same server.");
    } 
  }
}


