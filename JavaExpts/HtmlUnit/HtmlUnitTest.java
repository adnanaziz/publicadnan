import java.util.List;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
// TODO(AA): not sure why nonstatic import doesn't work
import static org.junit.Assert.*;


public class HtmlUnitTest {

  // adapted from examples in http://htmlunit.sourceforge.net/gettingStarted.html
  public static void xpath() throws Exception {
      final WebClient webClient = new WebClient();
      // Note: does not always retrieve correctly
      final HtmlPage page = webClient.getPage("http://www.kayak.com/#flights/SAT-IST/2012-03-10/2012-03-17");
      long sleepTime = 30000;

      // this page works fine, shows that javascript can be exected locally in jvm
      // final HtmlPage page = webClient.getPage("http://users.ece.utexas.edu/%7Eadnan/servletexample/factorial_js.html");
      // long sleepTime = 1000;
      Thread.sleep(sleepTime);

      // printing the entire page is useful for debugging
      // System.out.println("Page = \n" + page.asText());
  
      // get list of all divs
      final List<?> divs = page.getByXPath("//div");
      System.out.println("LIST OF DIVS:\n" + divs.toString());
  
      // get div which has a 'name' attribute of 'John'
      // final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@name='John']").get(0);
  
      webClient.closeAllWindows();
  }

  // adapted from http://htmlunit.sourceforge.net/javascript-howto.html
  public static void homePage() throws Exception {
      final WebClient webClient = new WebClient();
      final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
      assertEquals("Welcome to HtmlUnit", page.getTitleText());
  
      final String pageAsXml = page.asXml();
      assertTrue(pageAsXml.contains("<body class=\"composite\">"));
  
      final String pageAsText = page.asText();
      assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
  
      webClient.closeAllWindows();
  }

  public static void main(String[] args) {
    try {
      homePage();
      xpath();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
