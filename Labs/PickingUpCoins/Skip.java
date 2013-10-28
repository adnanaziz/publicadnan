import java.io.*;
import java.util.*;

public class Skip {
  public static void main(String[] args) throws Exception {
    BufferedReader reader = new BufferedReader( new InputStreamReader (System.in) );
    String line = null;
    while( ( line = reader.readLine() ) != null ) {
      if (line.contains("skip-begin") ) {
        while( !( line = reader.readLine() ).contains("skip-end"));
        line = reader.readLine(); // munch the skip-end line
        if ( line == null ) {
          break;
        } 
      }
      System.out.println(line);
    }
  }
}
