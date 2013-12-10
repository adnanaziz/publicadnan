import java.net.*;
import com.google.common.io.Resources;
import com.google.common.base.Charsets;

class Scraper {
  public static String scrape(String urlString) throws Exception {
    String serverString = "http://ec2-54-200-126-181.us-west-2.compute.amazonaws.com:16791";
    String raw = Resources.toString( new URL(serverString + "/?url=" + urlString), Charsets.UTF_8);
    System.out.println(raw);
    return raw;
  }
  public static void main(String[] args) throws Exception {
    String result = scrape("http://austin.craigslist.org/pts/4125554685.html"); 
    result = scrape("http://austin.craigslist.org/for/4231561592.html");
    System.out.println(result);
  }
}

