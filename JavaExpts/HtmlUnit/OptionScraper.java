
import java.util.List;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class OptionScraper  {

  public static List<String> scrapeFromUrl( String url ) {
    // Create a new instance of the html unit driver
    // Notice that the remainder of the code relies on the interface, 
    // not the implementation.
    WebDriver driver = new HtmlUnitDriver();

    // And now use this to visit yahoo finance
    driver.get(url);

    List<String> result = new ArrayList<String>();

    // Find the current price
    WebElement stockQuote = driver.findElement(By.className("time_rtq_ticker"));
    System.out.println("--- " + stockQuote.getText());
    result.add( stockQuote.getText() );


    // Find the quotes for options
    List<WebElement> optionQuotes = driver.findElements(By.className("yfnc_h"));
    for (WebElement optionQuote : optionQuotes) {
      System.out.println("--- " + optionQuote.getText());
      result.add( optionQuote.getText() );
    }

    return result;
  }

  public static void main( String [] args ) {
    List<String> result = scrapeFromUrl( "http://finance.yahoo.com/q/op?s=qqq&m=2012-03" );
    for ( String s : result ) {
      System.out.println("line:" + s);
    }
  }
}
