import java.io.*;
import java.net.*;
import com.google.gson.*;

// largely based on the Sudokuk client, nothing to change in here
class ThreadedMicroBlogClient implements Runnable {

  String hostname = "localhost";
  String threadname = "X";
  ClientMessage cm;
  MicroBlogClient sc;

  public ThreadedMicroBlogClient( MicroBlogClient sc, ClientMessage cm, 
                               String hostname, String threadname ) {
    this.sc = sc;
    this.cm = cm;
    this.hostname = hostname;
    this.threadname = threadname;
  }

  public void run() {
    System.out.flush();
    try {
      Socket sock = new Socket(hostname, MicroBlogServer.PORT);
      PrintWriter dos = new PrintWriter(sock.getOutputStream());
      BufferedReader dis = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
      dos.println(cm.toJson());
      dos.flush();
      // System.out.println("Client " + threadname + " sent: " + cm );
      String response = dis.readLine(); 
      // System.out.println("Client " + threadname + " sent: " + cm.toJson() + " received response:" + response);
      dos.close(); 
      dis.close();
      synchronized (sc) {
        ServerMessage serverResponse = new Gson().fromJson(response, ServerMessage.class);
        sc.result = serverResponse;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // System.out.println("finishing " + threadname + " run method");
  }
}


public class MicroBlogClient {
  ThreadedMicroBlogClient tc; 
  ServerMessage result = null;
  public boolean success;

  MicroBlogClient( ClientMessage cm, String hostname, String threadname ) {
    tc = new ThreadedMicroBlogClient( this, cm, hostname, threadname );
  }

  MicroBlogClient( ClientMessage cm, String name ) {
    this( cm, "localhost", name );
  }

  MicroBlogClient( ClientMessage cm ) {
    this( cm, "localhost", "unnamed client" );
  }
  
  void transmit() {
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
