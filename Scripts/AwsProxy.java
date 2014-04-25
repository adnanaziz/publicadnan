import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import com.google.common.io.Resources;
import com.google.common.base.Charsets;

public class AwsProxy {
  static int PORT = 16791;
  final static int MAXPARALLELTHREADS = 3;

  public static void start(int portNumber ) throws IOException {
    PORT = portNumber;
    Runnable serverThread = new ThreadedProxyServer();
    Thread t = new Thread( serverThread );
    t.start();
  }

  public static void main(String[] args) {
     try {
       AwsProxy.start(AwsProxy.PORT);
     } catch (Exception e) {
       e.printStackTrace();
     }
  }
}

class ThreadedProxy implements Runnable {
  private Socket sock;
  public ThreadedProxy( Socket sock ) {
    this.sock = sock;
  }
  public void run() {
    try {
    BufferedReader dis  = new BufferedReader( 
                              new InputStreamReader(sock.getInputStream()) );
    PrintWriter dos = new PrintWriter(sock.getOutputStream());
    String rawquery = dis.readLine();
    System.out.println("raw = " + rawquery);
    int start = rawquery.indexOf("url=") + "url=".length();
    int end = rawquery.lastIndexOf("HTTP");
    String query = rawquery.substring(start, end);
    System.out.println("query = " + query);
    String result = Resources.toString( new URL(query), Charsets.UTF_8);

    // needed for some clients?! aron's experience maybe need for 1.7
    dos.println("HTTP/1.0 200 OK\n"); 

    dos.println(result);
    dos.flush();
    dis.close(); 
    dos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


class ThreadedProxyServer implements Runnable {

  public static ExecutorService threadpool = Executors.newFixedThreadPool(AwsProxy.MAXPARALLELTHREADS);

  public void run() {
    try {
      ServerSocket serversock = new ServerSocket(AwsProxy.PORT);
      while (true) {
        Socket sock = serversock.accept();
        ThreadedProxy ts = new ThreadedProxy(sock);
        synchronized (threadpool) {
          threadpool.execute( ts );
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
