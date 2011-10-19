// import java.util.concurrent.atomic;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class Cpu<T extends Serializable, R extends Serializable> {

  String host; // name of machine
  int port; // port CPU is listening on
  Socket socket;
  
  public Cpu( String host, int port ) {
    this.host = host;
    this.port = port;
  }

  public R execute( T task ) throws Exception {
    try {
      this.socket = new Socket( host, port );
    } catch (Exception e) { e.printStackTrace(); }
    Controller.getLogger().note("execute call in Cpu starting" );
    MsgToClient omsg = new MsgToClient( Tasks.generateSerialNumber(),
    				Tasks.getTimeOut( this ),
				task );
    PrintWriter out =  new PrintWriter(socket.getOutputStream(), true);
    Controller.getLogger().note("got out PrintWriter");

    String sendString = null;
    try {
      sendString = Base64.encodeObject( (java.io.Serializable) omsg );
    } catch (Exception e) { e.printStackTrace(); }
    Controller.getLogger().note("sendString: " + sendString );
    out.println( sendString );
    Controller.getInstance().getLogger().note("Sent " + sendString );
    
    DataInputStream in = new DataInputStream( socket.getInputStream() );
    String msgString = in.readLine();
    Controller.getLogger().note("msgString = " + msgString );

    MsgToServer msg = (MsgToServer) Base64.decodeToObject( (String) msgString );
    R r = (R) Base64.decodeToObject( msg.e64 );
    Controller.getLogger().warn("id of returned message is " + msg.id );
    Controller.getLogger().warn("Value of the returned Integer is " + (Integer) r );

    // TODO: figur out why the socket has to be closed, cannot
    // be reused
    socket.close();

    return r;
  }
}
