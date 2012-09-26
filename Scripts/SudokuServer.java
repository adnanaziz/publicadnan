// Example 156 from page 133 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;

// @Author(name="Joel Garner", uteid="jg123")
public class SudokuServer {
  final static int PORT = 16789;
  final static int NTHREADS = 1;

  public static void start() throws IOException {
    Runnable serverThread = new ThreadedSudokuServer();
    Thread t = new Thread( serverThread );
    t.start();
  }
}


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
