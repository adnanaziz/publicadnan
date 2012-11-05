import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

// largely based on the Sudoku server. you do not need 
// to change anything here
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

  public static void main(String[] args) {
    try {
      new MicroBlogServer().start(16667);
    } catch (Exception e) {
      e.printStackTrace();
    }
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
  
  public void run() {
    try {
      BufferedReader dis  = new BufferedReader( 
                                new InputStreamReader(sock.getInputStream()) );
      PrintWriter dos = new PrintWriter(sock.getOutputStream());

      String cmJsonStringQuery = dis.readLine();
      // String cmJsonString = "{\"type\":\"CREATE\",\"author\":\"AdnanAziz1968\",\"id\":0,\"time\":0}";
      System.out.println("Server received: " + cmJsonStringQuery );
      String cmJsonStringTmp = cmJsonStringQuery.replaceFirst(".*=", "");
      int lastIndex = cmJsonStringTmp.indexOf(" HTTP");
      String cmJsonString = cmJsonStringTmp.substring(0, lastIndex );
      String cmJsonStringNoEscape = cmJsonString.replaceAll("%22", "\"");
      System.out.println("cmJsonStringNoEscape = " + cmJsonStringNoEscape);

      ClientMessage jsonreq = ClientMessage.fromJson(cmJsonStringNoEscape);
      // String cmJsonReqSer = jsonreq.toJson();
      String cmJsonReqSer = jsonreq.toJson();
      System.out.println("cmJsonReqSer = " + cmJsonReqSer);
      /*
      ClientMessage cm = ClientMessage.fromJson( cmJsonString );
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

      String smJsonString = sm.toJson();
      dos.println(smJsonString);
      */

String body = "<h1>Happy New Millennium!</h1>";
StringBuffer sb = new StringBuffer();
sb.append("HTTP/1.0 200 OK\n");
sb.append("Date: Fri, 31 Dec 1999 23:59:59 GMT\n");
sb.append("Content-Type: text/html\n");
sb.append("Content-Length: " +  body.length() +"\n\n" );
sb.append(body);
String [] lines = sb.toString().split("\n");
for ( String line : lines ) {
  dos.println(line);
  System.out.println("Printing line:" + line);
}

      //ServerMessage sm = new ServerMessage().setPostings( new ArrayList<Posting>() );
      //sm.postings.add( new Posting(123L, "Adnan Aziz", "HW!", "Body text", 1000L, 10L) );
      //String smJsonString = sm.toJson();
      //dos.println(smJsonString);

      dos.flush();
      dis.close(); 
      dos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

