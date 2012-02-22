
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Example  {
    public static void main(String[] args) {
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        // And now use this to visit Google
        driver.get("http://finance.yahoo.com/q/op?s=qqq&m=2012-03");
        System.out.println(driver.toString());

        // Find the text input element by its name
        List<WebElement> elements = driver.findElements(By.xpath("//*"));
        for (WebElement we : elements) {
          System.out.println("--- " + we.getText());
        }

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
    }
}
