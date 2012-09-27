import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.reflect.*;


@Target(TYPE)
@Retention(RUNTIME)
//TODO(EE422C): update these to your name and eid
@interface Author {
  public String name() default  "John Snow" ;
    public String uteid() default  "js123" ;
  }
}

public class SudokuServer {
  static int PORT = -1;
  // EE422C: no matter how many concurrent requests you get,
  // do not have more than three solvers running concurrently
  final static int MAXPARALLELTHREADS = 3;

  public static void start(int portNumber ) throws IOException {
    PORT = portNumber;
    Runnable serverThread = new ThreadedSudokuServer();
    Thread t = new Thread( serverThread );
    t.start();
  }
}

	//TODOBEGIN(EE422C)
	//TODOEND(EE422C)

