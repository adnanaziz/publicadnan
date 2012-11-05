import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
// extra m below...
//import com.google.commmon.cache.*;
import com.google.common.io.*;
import com.google.common.base.*;

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

  static boolean firstTime = true;
  
  public void run() {
    try {
      BufferedReader dis  = new BufferedReader( 
                                new InputStreamReader(sock.getInputStream()) );
      PrintWriter dos = new PrintWriter(sock.getOutputStream());

      /*
      String cmJsonStringQuery = dis.readLine();
      // String cmJsonString = "{\"type\":\"CREATE\",\"author\":\"AdnanAziz1968\",\"id\":0,\"time\":0}";
      System.out.println("Server received: " + cmJsonStringQuery );
      String cmJsonStringTmp = cmJsonStringQuery.replaceFirst(".*=", "");
      int lastIndex = cmJsonStringTmp.indexOf(" HTTP");
      String cmJsonString = cmJsonStringTmp.substring(0, lastIndex );
      String cmJsonStringNoEscape = cmJsonString.replaceAll("%22", "\"").replaceAll("%20", " ");
      System.out.println("cmJsonStringNoEscape = " + cmJsonStringNoEscape);

      ClientMessage jsonreq = ClientMessage.fromJson(cmJsonStringNoEscape);
      // String cmJsonReqSer = jsonreq.toJson();
      String cmJsonReqSer = jsonreq.toJson();
      System.out.println("cmJsonReqSer = " + cmJsonReqSer);
      */

      String body;
      if ( firstTime ) {
         body = Files.toString( new File("home.html"), Charsets.UTF_8 );
         firstTime = false;
      } else {
         body = "<h1>Happy New Millennium!</h1>";
      }

ServerMessage sm = new ServerMessage().setPostings( new ArrayList<Posting>() );
sm.postings.add( new Posting(123L, "Adnan Aziz", "HW!", "Adnan Body text", 2000L, 20L) );
sm.postings.add( new Posting(456L, "Don Bradman", "29", "Don Body text", 2000L, 20L) );
// String smJsonString = sm.toJson();
String smJsonString = body;
System.out.println("smJsonString:" + smJsonString);


StringBuffer sb = new StringBuffer();
sb.append("HTTP/1.0 200 OK\n");
sb.append("Date: Fri, 31 Dec 1999 23:59:59 GMT\n");
//sb.append("Access-Control-Allow-Origin: *\n");
sb.append("Content-Type: text/html\n");
//sb.append("Content-Type: application/json\n");
sb.append("Content-Length: " +  smJsonString.length() +"\n\n" );
sb.append(smJsonString);
String [] lines = sb.toString().split("\n");
for ( String line : lines ) {
  // dos.println(line);
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

