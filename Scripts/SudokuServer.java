// Example 156 from page 133 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;
import java.net.*;

class SudokuServer {
  final static int PORT = 16789;

  final static String testcase = "123 456 789\n";

  public static void main(String[] args) throws IOException {
    boolean server = args.length == 1 && args[0].equals("server");
    boolean client = args.length == 2 && args[0].equals("client");
    if (server) {               // Server: accept questions about solvability
      ServerSocket serversock = new ServerSocket(PORT);
      for (;;) { // forever, accept connections
        Socket sock = serversock.accept();
        System.out.println("Server receiving request");
        BufferedReader dis  = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
        System.out.println("Server created dis");
        PrintWriter dos = new PrintWriter(sock.getOutputStream());
        System.out.println("Server created dos");
        System.out.println("Server calling dis.readLine()");
        String query = dis.readLine();
        // String query;
        // StringBuffer queryBuffer = new StringBuffer();
        // while (true) {
        //   int ch = dis.read();
        //   System.out.println("just read " + (char) ch );
        //   if ((ch < 0) || (ch == '\n'))  break;
        //   queryBuffer.append((char) ch);
        // }
        // query = queryBuffer.toString();
        System.out.print("[" + query + "]");
        String solverOutput = SudokuSolver.solve(query);
        System.out.print("writing following to dos: " + solverOutput );
        dos.println(solverOutput);
        dos.flush();
        dis.close(); dos.close();
      }
    } else if (client) {        // Client: ask questions about solvability
      for (int i=1; i<4; i++) {
        Socket sock = new Socket(args[1], PORT);
        PrintWriter dos = new PrintWriter(sock.getOutputStream());
        BufferedReader dis = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
        System.out.println("Client sending request");
        dos.println(testcase);
        dos.flush();
        String response = dis.readLine(); 
        System.out.println("Client received response:" + response);
        dos.close(); dis.close();
      }
    } else {                    // Neither server not client
      System.out.println("Start two copies of this program, possibly on different machines:");
      System.out.println("   java SudokuServer server");
      System.out.println("   java SudokuServer client <serverhostname>");
      System.out.println("Use `java Example156 client localhost' if the"); 
      System.out.println("client and server run on the same machine.");
      System.out.println("You may start several clients all talking to the same server.");
    } 
  }
}

