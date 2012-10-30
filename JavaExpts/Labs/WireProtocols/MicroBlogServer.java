import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class MicroBlogServer{
  static int PORT = -1;
  final static int MAXPARALLELTHREADS = 3;

  public static void start(int portNumber ) throws IOException {
    PORT = portNumber;
    Runnable serverThread = new ThreadedMicroBlogServer();
    Thread t = new Thread( serverThread );
    t.start();
  }

  static List<Posting> postingList = new ArrayList<Posting>();

}

class ThreadedMicroBlogServer implements Runnable {

  public static ExecutorService threadpool = Executors.newFixedThreadPool(MicroBlogServer.MAXPARALLELTHREADS);

  public void run() {
    try {
      ServerSocket serversock = new ServerSocket(MicroBlogServer.PORT);
      while (true) {
        Socket sock = serversock.accept();
        ThreadedServer ts = new ThreadedServer(sock);
        synchronized (threadpool) {
          threadpool.execute( ts );
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class ThreadedServer implements Runnable {

  private Socket sock;
  public ThreadedServer( Socket sock ) {
    this.sock = sock;
  }
  
  public void run() {
    try {
      BufferedReader dis  = new BufferedReader( 
                                new InputStreamReader(sock.getInputStream()) );
      PrintWriter dos = new PrintWriter(sock.getOutputStream());

      String cmJsonString = dis.readLine();
      System.out.println("cmJsonString:" + cmJsonString);
      ClientMessage cm = ClientMessage.fromJson( cmJsonString );
      Posting p = new Posting().setAuthor(cm.getAuthor());

      ServerMessage sm = new ServerMessage().setId(MicroBlogServer.postingList.size());    
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

