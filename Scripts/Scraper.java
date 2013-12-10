import java.net.*;
import com.google.common.io.Resources;
import com.google.common.base.Charsets;

class Scraper {
  public static String scrape(String urlString) throws Exception {
    String raw = null;
    try {
      String serverString = "http://ec2-54-200-126-181.us-west-2.compute.amazonaws.com:16791";
      URL serviceUrl = new URL(serverString + "/?url=" + urlString);
      raw = Resources.toString( serviceUrl, Charsets.UTF_8);
      System.out.println(raw);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return raw;
  }
  public static void main(String[] args) throws Exception {
    String result = scrape("http://austin.craigslist.org/pts/4125554685.html"); 
    result = scrape("http://austin.craigslist.org/for/4231561592.html");
    System.out.println(result);
  }
}

