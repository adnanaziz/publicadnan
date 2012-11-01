import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

/**
 *  largely based on the Sudoku server.
 *  You need to write the method 'msgProcessor' that processes messages from clients
 */
public class MicroBlogServer{

  static int PORT = 0;
  final static int MAXPARALLELTHREADS = 3;

  List<Posting> postingList = new ArrayList<Posting>();

  ThreadedMicroBlogServer serverThread = null;

  public void start(int portNumber ) throws IOException {
    PORT = portNumber;
    serverThread = new ThreadedMicroBlogServer( this );
    Thread t = new Thread( serverThread );
    t.start();
  }

  public void stop() {
    serverThread.shutdownRequested = true;
  }

}

class ThreadedMicroBlogServer implements Runnable {

  MicroBlogServer ms;
  boolean shutdownRequested = false;

  ThreadedMicroBlogServer( MicroBlogServer ms ) {
    this.ms = ms;
  }

  public static ExecutorService threadpool = Executors.newFixedThreadPool(MicroBlogServer.MAXPARALLELTHREADS);

  public void run() {
    ServerSocket serversock = null;
    try {
      serversock = new ServerSocket(MicroBlogServer.PORT);
      while (!shutdownRequested) {
        Socket sock = serversock.accept();
        ThreadedServer ts = new ThreadedServer(ms, sock);
        synchronized (threadpool) {
          threadpool.execute( ts );
        }
      }
      serversock.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class ThreadedServer implements Runnable {

  private Socket sock;
  private MicroBlogServer ms;

  public ThreadedServer( MicroBlogServer ms, Socket sock ) {
    this.ms = ms;
    this.sock = sock;
  }
  
  /**
   * Some useful hints for msgProcessor.
   * This is NOT a solution
   * @param cm
   * 	ClinetMessage to be processed
   * @return
   * 	ServerMessage to be sent to the client
   */
  private ServerMessage msgProcessorSample (ClientMessage cm) {
	  ServerMessage sm = null;

      if ( cm.getType() == ClientMessage.Type.CREATE ) {
        long id = ms.postingList.size();
        Posting p = new Posting().setAuthor(cm.getAuthor()).setId( id );
        ms.postingList.add( p );
        sm = new ServerMessage().setId(id);
      } else if ( cm.getType() == ClientMessage.Type.QUERY ) {
        sm = new ServerMessage().setPostings( new ArrayList<Posting>() );
        String [] authors = cm.getAuthor().split("\\s+");
        for ( Posting p : ms.postingList ) {
          for ( String queryAuthor : authors ) {
            if ( queryAuthor.equals( p.getAuthor() ) ) {
             sm.postings.add( p );
            }
          }
        }
      }
      
	  return sm;
  }
  
  /**
   * Processes a message from client and returns the proper server message
   * @param cm
   * 	ClinetMessage to be processed
   * @return
   * 	ServerMessage to be sent to the client
   */
  private ServerMessage msgProcessor (ClientMessage cm) {
	  ServerMessage sm = null;
	  //TODO: complete this method
	  
	  return sm;
  }
  
  public void run() {
    try {
      BufferedReader dis  = new BufferedReader( 
                                new InputStreamReader(sock.getInputStream()) );
      PrintWriter dos = new PrintWriter(sock.getOutputStream());

      String cmJsonString = dis.readLine();
      ClientMessage cm = ClientMessage.fromJson( cmJsonString );
      ServerMessage sm = msgProcessor(cm);
      String smJsonString = sm.toJson();
      dos.println(smJsonString);
      
      dos.flush();
      dis.close(); 
      dos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

