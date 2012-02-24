
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.google.common.io.*;
import com.google.common.base.Charsets;


public class OptionScraper  {

  private static boolean doMock = true;

  public static void setMock() {
    doMock = true;
  }

  public static void setReal() {
    doMock = false;
  }

  public static List<String> scrapeFromUrl( String url ) {
    return doMock ? scrapeFromUrlMock( url ) : scrapeFromUrlReal( url );
  }

  private static List<String> scrapeFromUrlMock( String url ) {
    String testFileString = null;
    try {
      testFileString = Files.toString(new File("option.out"), Charsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String lines[] = testFileString.split( "\n");
    List<String> result = Arrays.asList( lines );
    return result;
  }

  private static List<String> scrapeFromUrlReal( String url ) {
    // Create a new instance of the html unit driver
    // Notice that the remainder of the code relies on the interface, 
    // not the implementation.
    WebDriver driver = new HtmlUnitDriver();

    // And now use this to visit yahoo finance
    driver.get(url);

    List<String> result = new ArrayList<String>();

    // Find the current price
    WebElement stockQuote = driver.findElement(By.className("time_rtq_ticker"));
    result.add( stockQuote.getText() );


    // Find the quotes for options
    List<WebElement> optionQuotes = driver.findElements(By.className("yfnc_h"));
    for (WebElement optionQuote : optionQuotes) {
      result.add( optionQuote.getText() );
    }

    return result;
  }

  public static void main( String [] args ) {
    // List<String> result = scrapeFromUrl( "http://finance.yahoo.com/q/op?s=qqq&m=2012-03" );
    List<String> result = scrapeFromUrl( "http://finance.yahoo.com/q/op?s=spy&m=2012-03");
    for ( String s : result ) {
      System.out.println("line:" + s);
    }
  }
}
