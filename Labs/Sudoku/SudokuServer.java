import java.io.*;
import java.net.*;
import java.util.Random;

class SudokuServer {
  final static int PORT = 16789;

  public static void main(String[] args) throws IOException {
    boolean server = args.length == 1 && args[0].equals("server");
    boolean client = args.length == 2 && args[0].equals("client");
    if (server) {               // Server: accept questions about solvability
      ServerSocket serversock = new ServerSocket(PORT);
      for (;;) { // forever, accept connections
        Socket sock = serversock.accept();
        BufferedReader dis  = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
        PrintWriter dos = new PrintWriter(sock.getOutputStream());
        String query = dis.readLine();
        String solverOutput = SudokuSolver.solve(query);
        dos.println(solverOutput);
        dos.flush();
        dis.close(); 
        dos.close();
      }
    } else if (client) {        // Client: ask questions about solvability
      Random r = new Random();
      for (int i=1; i<4; i++) {
        Socket sock = new Socket(args[1], SudokuServer.PORT);
        PrintWriter dos = new PrintWriter(sock.getOutputStream());
        BufferedReader dis = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
        String testcase = "" + Math.abs(r.nextInt()%9) + Math.abs(r.nextInt())%9 + Math.abs(r.nextInt()%9);
        System.out.println("testcase = " + testcase );
        dos.println(testcase);
        dos.flush();
        String response = dis.readLine(); 
        System.out.println("response = " + response );
        dos.close(); 
        dis.close();
      }
    } else {                    // Neither server not client
      System.out.println("Start two copies of this program, possibly on different machines:");
      System.out.println("   java SudokuServer server");
      System.out.println("   java SudokuServer client <serverhostname>");
      System.out.println("Use `java SudokuServer client localhost' if the"); 
      System.out.println("client and server run on the same machine.");
      System.out.println("You may start several clients all talking to the same server.");
    } 
  }
}

