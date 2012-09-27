import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;

public class SudokuServer {
  static int PORT = -1;
  final static int MAXPARALLELTHREADS = 3;

  public static void start(int portNumber ) throws IOException {
    PORT = portNumber;
    Runnable serverThread = new ThreadedSudokuServer();
    Thread t = new Thread( serverThread );
    t.start();
  }
}


	//TODOBEGIN(EE422C)
