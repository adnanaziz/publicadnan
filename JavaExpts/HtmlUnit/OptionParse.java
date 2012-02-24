import java.io.File;
import java.util.List;
import java.util.ArrayList;
import com.google.common.io.*;
import com.google.common.base.Charsets;

public class OptionParse {

  public static void printLines( String [] lines ) {
    for (int i = 0 ; i < lines.length; i++ ) {
      System.out.println("Line " + i + ": " + lines[i] );
    }
  }

  public static List<Option> parse( String dataString ) {
    String lines[] = dataString.split( "\n");
    double currentPrice = new Double( lines[0] );
    int i = 0;
    int N = ((lines.length )/ 9) * 9; // use this to skip last blank line
    List<Option> result = new ArrayList<Option>();
    while ( i < N) {
      Option.Builder optBuilder = new Option.Builder( lines[i+2], currentPrice );
      Option opt = new Option.Builder( lines[i+2], currentPrice ).strike( lines[i+1] )
                          .last( lines[i+3] ).change( lines[i+4] ).bid( lines[i+5] )
                          .ask( lines[i+6] ).vol( lines[i+7] ).open( lines[i+8] ).build();
      result.add( opt );
      i += 8;
    }
    return result;
  }

  public static void main(String [] args) {
    String testFileString = null;
    try {
      testFileString = Files.toString(new File("option.out"), Charsets.UTF_8);
      parse( testFileString );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
