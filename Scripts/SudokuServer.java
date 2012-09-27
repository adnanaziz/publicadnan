import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;

public class SudokuServer {
  static int PORT = -1;
  // EE422C: no matter how many concurrent requests you get,
  // do not have more than three solvers running concurrently
  final static int MAXPARALLELTHREADS = 3;

  public static void start(int portNumber ) throws IOException {
    PORT = portNumber;
    Runnable serverThread = new ThreadedSudokuServer();
    Thread t = new Thread( serverThread );
    t.start();
  }
}


//@exclude
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

  public static ExecutorService threadpool = Executors.newFixedThreadPool(SudokuServer.MAXPARALLELTHREADS);

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
