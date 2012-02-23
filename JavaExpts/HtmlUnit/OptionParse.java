import java.io.File;
import java.util.List;
import java.util.ArrayList;
import com.google.common.io.*;
import com.google.common.base.Charsets;

public class OptionParse {
  public static List<Option> parse( String dataString ) {
    String lines[] = dataString.split( "\\n");
    double currentPrice = new Double( lines[0] );
    int i = 0;
    int N = ((lines.length )/ 9) * 9; // use this to skip last blank line
    List<Option> result = new ArrayList<Option>();
    while ( i < N) {
      System.out.println( lines[i+1] + " , " + lines[i+2] );
      Option.Builder optBuilder = new Option.Builder( lines[i+2], currentPrice );
      optBuilder.strike( lines[i+1] );
      optBuilder.last( lines[i+3] );
      optBuilder.change( lines[i+4] );
      optBuilder.bid( lines[i+5] );
      optBuilder.ask( lines[i+6] );
      optBuilder.vol( lines[i+7] );
      optBuilder.open( lines[i+8] );
      Option opt = optBuilder.build();
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
