import java.util.*;
import java.io.*;
import java.net.*;

public class SimpleClient {
  public static void main( String [] args ) {
    Logger logger = new Logger("SimpleClient", Logger.Level.Alert );
    logger.turnOff();
    String serverIp = args[0];
    String serverPort = args[1];
    logger.note("serverIp = " + args[0] + " serverPort = " + args[1] );
    try {
      ServerSocket serversock = new ServerSocket( Integer.parseInt( serverPort ) );
      while ( true ) {
        logger.note("about to call serversock.accept");
        Socket sock = serversock.accept();
        logger.note("created sock");
        // BufferedReader in = new BufferedReader(new InputStreamReader(
  	//                              sock.getInputStream()));
        DataInputStream in = new DataInputStream( sock.getInputStream() );
        String msgString = in.readLine();
        logger.note("msgString = " + msgString );
        MsgToClient imsg = (MsgToClient) Base64.decodeToObject( (String) msgString );
        Integer inputArg = (Integer) Base64.decodeToObject( imsg.e64 );
        logger.alert("input id " + imsg.id );
        logger.note("input data " + inputArg );
        Integer result = 2 * inputArg;
        MsgToServer omsg = new MsgToServer( imsg.readId(), result );
        PrintWriter out =  new PrintWriter(sock.getOutputStream(), true);
  
        String sendString = Base64.encodeObject( (java.io.Serializable) omsg );
  	          out.println( sendString );
	logger.note("Sending out " + sendString );
	out.println(sendString);
	logger.note("Sent out " + sendString );
      }
    } catch (Exception e) { e.printStackTrace(); }
  }
}


