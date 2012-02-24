import java.util.Random;
import java.util.Collections;
import java.util.Date;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.common.io.*;
import com.google.common.base.Charsets;


public class OptionAnalyzer {

  static String[] symbols = 
    {
        // "http://finance.yahoo.com/q/op?s=spy&m=2012-03",
        // "http://finance.yahoo.com/q/op?s=qqq&m=2012-03",
        "http://finance.yahoo.com/q/op?s=aapl&m=2012-03",
    };

  static List<String> symbolsList = Arrays.asList(symbols);

  public static void main(String [] args) {
    try {
      while ( true ) {
        for ( String s : symbols ) { 
          List<String> lines = OptionScraper.scrapeFromUrl( s );
          StringBuffer sb = new StringBuffer();
          for ( String tmp : lines ) {
            sb.append(tmp + "\n");
          }
          List<Option> options = OptionParse.parse(sb.toString());
          options = randomize( options );
          analyze( options );
        }
	System.out.println(new Date().toString());
        Thread.sleep(1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void analyze( List<Option> options) {
    List<OptionValue> l = new ArrayList<OptionValue>();
    for ( Option opt : options ) {
      if ( opt.isCall ) {
        l.add( new OptionValue( opt, ( opt.strike - opt.stockPrice ) ) );
      }
    }
    Collections.sort( l );
    render( l );
  }

  public static void render( List<OptionValue> l ) {
    int i = 0;
    String prolog = "<html> <head> <title> Option Analyzer"
                    + "results </title> <meta http-equiv=\"refresh\" content=\"2\">" 
                    + "</head>";

    StringBuffer sb = new StringBuffer(prolog);
    sb.append("<body>\n");
    for ( OptionValue ov :  l ) {
      sb.append("<li>" +  ov.toString() );
      if ( i > 10 ) {
        break;
      }
    }
    sb.append("</body>\n");
    try {
      File resultFile = new File("analyzer.html");
      Files.write(sb.toString(), resultFile, Charsets.UTF_8 );
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(sb.toString());
  }

  public static double update(Random r) { return 1.0 + r.nextDouble()/10.0; }

  public static List<Option> randomize( List<Option> l ) {
    List<Option> randomizedOptions = new ArrayList<Option>();
    Random r = new Random();
    for ( Option o : l ) {
      Option ro = new Option.Builder( o.quoteString, o.stockPrice * update(r) )
                        .strike( o.strike * update(r) )
                        .last( o.last * update(r) )
                        .change( o.change * update(r) )
                        .bid( o.bid * update(r) )
                        .ask( o.ask * update(r) )
                        .vol( (int) ( o.vol * update(r) ) )
                        .open( (int) ( o.open * update(r) ) )
                        .build();
      
      randomizedOptions.add( ro );
    }
    return randomizedOptions;
  }

}
