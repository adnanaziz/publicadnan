import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class OptionAnalyzer {

  static String[] symbols = 
    {
        "http://finance.yahoo.com/q/op?s=spy&m=2012-03",
        "http://finance.yahoo.com/q/op?s=qqq&m=2012-03",
        "http://finance.yahoo.com/q/op?s=aapl&m=2012-03",
    };

  static List<String> symbolsList = Arrays.asList(symbols);
  public static void main(String [] args) {
    for ( String s : symbols ) { 
      List<String> lines = OptionScraper.scrapeFromUrl( s );
      StringBuffer sb = new StringBuffer();
      for ( String tmp : lines ) {
        sb.append(tmp);
      }
      List<Option> options = OptionParse.parse(sb.toString());
      analyze( options );
    }
  }

  public static void analyze( List<Option> options) {
    for ( Option opt : options ) {
      double ask = opt.ask;
      if ( opt.isCall && ( opt.strike - opt.stockPrice ) <= 0.5 * ask ) {
        System.out.println("Winner: " + opt.toString() );
      }
    }
  }
}
