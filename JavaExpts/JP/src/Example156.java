// Example 156 from page 133 of Java Precisely second edition (The MIT Press 2005)
// Author: Peter Sestoft (sestoft@itu.dk)

import java.io.*;
import java.net.*;

class Example156 {
  final static int PORT = 2357;

  public static void main(String[] args) throws IOException {
    boolean server = args.length == 1 && args[0].equals("server");
    boolean client = args.length == 2 && args[0].equals("client");
    if (server) {               // Server: accept questions about primality
      ServerSocket serversock = new ServerSocket(PORT);
      for (;;) { // forever, accept connections
        Socket sock = serversock.accept();
        DataInputStream dis  = new DataInputStream(sock.getInputStream());
        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        int query = dis.readInt();
        System.out.print("[" + query + "]");
        dos.writeBoolean(isPrime(query));
        dis.close(); dos.close();
      }
    } else if (client) {        // Client: ask questions about primality
      for (int i=1; i<1000; i++) {
        Socket sock = new Socket(args[1], PORT);
        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        DataInputStream dis = new DataInputStream(sock.getInputStream());
        dos.writeInt(i);
        if (dis.readBoolean()) 
          System.out.print(i + " ");
        dos.close(); dis.close();
      }
    } else {                    // Neither server not client
      System.out.println("Start two copies of this program, possibly on different machines:");
      System.out.println("   java Example156 server");
      System.out.println("   java Example156 client <serverhostname>");
      System.out.println("Use `java Example156 client localhost' if the"); 
      System.out.println("client and server run on the same machine.");
      System.out.println("You may start several clients all talking to the same server.");
    } 
  }

  static boolean isPrime(int p) {
    if (p == 2)
      return true;
    if (p == 1 || p % 2 == 0) 
      return false;
    for (int q=3; q*q<=p; q+=2)
      if (p % q == 0)
        return false;
    return true;
  }
}

