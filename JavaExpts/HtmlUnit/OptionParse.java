import com.google.common.io.*;

public class OptionParse {
  public static void parse( String dataString ) {
    String lines = String.split( dataString, "\\n");
    double currentPrice = new Double( lines[0] );
    int i = 1;
    while ( i < lines.length ) {
      Option = new Option.Builder( currentPrice, lines[i+1] )
                    .strike( new Double( lines[i+2] ) )
                    .bid( new Double( lines[i+3] ) )
                    .ask( new Double( lines[i+4] ) )
                    .last( new Double( lines[i+5] ) )
                    .vol( new Double( lines[i+6] ) )
                    .open( new Double( lines[i+7] ) )
                    .build();
                    i += 7;
    }
  }
  private static String testFileString = Files.toString(new File("test.txt"), Charsets.UTF_8);

  public static void main(String [] args) {
    parse( testFileString );
  }
}
