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
        "http://finance.yahoo.com/q/op?s=spy&m=2012-03",
        "http://finance.yahoo.com/q/op?s=qqq&m=2012-03",
        "http://finance.yahoo.com/q/op?s=aapl&m=2012-03",
    };

  static List<String> symbolsList = Arrays.asList(symbols);

  public static void main(String [] args) {
    OptionScraper.setReal();
    try {
      while ( true ) {
        for ( String s : symbols ) { 
          List<String> lines = OptionScraper.scrapeFromUrl( s );
          StringBuffer sb = new StringBuffer();
          for ( String tmp : lines ) {
            sb.append(tmp + "\n");
          }
          List<Option> options = OptionParse.parse(sb.toString());
          // options = randomize( options );
          analyzePairs( options );
        }
	System.out.println(new Date().toString());
        Thread.sleep(60000);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void analyzePairs( List<Option> options) {
    List<OptionPair> l = new ArrayList<OptionPair>();
    for ( Option opt1 : options ) {
      for ( Option opt2 : options ) {
        if ( opt1.isCall && opt2.isCall ) {
           l.add( new OptionPair( opt1, opt2) );
        }
      }
    }
    Collections.sort( l );
    renderPairs( l );
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

  public static void renderPairs( List<OptionPair> l ) {
    int i = 0;
    String cssString = null;
    try {
      cssString = Files.toString(new File("source/style.css"), Charsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String prolog = "<html> <head> <title> Option Analyzer"
                    + "</title> <meta http-equiv=\"refresh\" content=\"2\">" 
                    // + "\n<!-- @import \"style.css\"; -->\n"
                    + "<style type=\"text/css\">" +  cssString + "</style>"
                    + "</head>";

    StringBuffer sb = new StringBuffer(prolog);
    sb.append("<body>\n");
    sb.append("<table id=\"newspaper-c\">\n");
    sb.append("<thead><tr>\n");
    String header = Option.fields().replace(",", "</td><td>");
    sb.append("<td>Value</td><td>" + header + "</td>");
    sb.append("</tr></thead>\n");
    for ( OptionPair op :  l ) {
      System.out.println("Pair=" + op.toString() );
      sb.append("<tr><td>" + op.value + "</td><td> " +  op.o1.toString().replace(",", " </td><td> ").replaceAll("[a-z]*:", "") + "</td></tr>\n" );
      sb.append("<tr><td>" + op.value + "</td><td> " +  op.o2.toString().replace(",", " </td><td> ").replaceAll("[a-z]*:", "") + "</td></tr>\n" );
      if ( i++ > 16 ) {
        break;
      }
    }
    sb.append("</table>\n</body>\n");
    try {
      File resultFile = new File("analyzer.html");
      Files.write(sb.toString(), resultFile, Charsets.UTF_8 );
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(sb.toString());
  }

  public static double update(Random r) { return 1.0 + r.nextDouble()/10.0; }


  public static void render( List<OptionValue> l ) {
    int i = 0;
    String cssString = null;
    try {
      cssString = Files.toString(new File("source/style.css"), Charsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String prolog = "<html> <head> <title> Option Analyzer"
                    + "</title> <meta http-equiv=\"refresh\" content=\"2\">" 
                    // + "\n<!-- @import \"style.css\"; -->\n"
                    + "<style type=\"text/css\">" +  cssString + "</style>"
                    + "</head>";

    StringBuffer sb = new StringBuffer(prolog);
    sb.append("<body>\n");
    sb.append("<table id=\"newspaper-c\">\n");
    sb.append("<thead><tr>\n");
    String header = Option.fields().replace(",", "</td><td>");
    sb.append("<td>Value</td><td>" + header + "</td>");
    sb.append("</tr></thead>\n");
    for ( OptionValue ov :  l ) {
      sb.append("<tr><td>" +  ov.toString().replace(",", " </td><td> ").replaceAll("[a-z]*:", "") + "</td></tr>\n" );
      if ( i > 10 ) {
        break;
      }
    }
    sb.append("</table>\n</body>\n");
    try {
      File resultFile = new File("analyzer.html");
      Files.write(sb.toString(), resultFile, Charsets.UTF_8 );
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(sb.toString());
  }


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
