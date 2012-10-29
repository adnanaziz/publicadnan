import java.io.*;
import java.net.*;
import java.util.concurrent.*;

}

public class MicroblogServer{
  static int PORT = -1;
  final static int MAXPARALLELTHREADS = 3;

  public static void start(int portNumber ) throws IOException {
    PORT = portNumber;
    Runnable serverThread = new ThreadedMicroblogServer();
    Thread t = new Thread( serverThread );
    t.start();
  }
  static List<Postings> postingList = new ArrayList<Postings>();
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
      String cmJsonString = dis.readLine();
      ClientMessage cm = ClientMessage.fromJson( cmJsonString );
      Postings p = new Postings().setAuthor(cm.getAuthor());

      ServerMessage sm = new ServerMessage().setId(postingList.size());    
      String smJsonString = sm.toString();
      dos.println(smJsonString);
      dos.flush();
      dis.close(); 
      dos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

class ThreadedMicroblogServer implements Runnable {

  public static ExecutorService threadpool = Executors.newFixedThreadPool(MicroblogServer.MAXPARALLELTHREADS);

  public void run() {
    try {
      ServerSocket serversock = new ServerSocket(MicroblogServer.PORT);
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
