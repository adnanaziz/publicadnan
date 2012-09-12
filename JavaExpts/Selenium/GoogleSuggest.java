// package org.openqa.selenium.example;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GoogleSuggest {
    public static void main(String[] args) throws Exception {
        // The Firefox driver supports javascript 
        WebDriver driver = new FirefoxDriver();
        
        // Go to the Google Suggest home page
        driver.get("http://www.google.com/webhp?complete=1&hl=en");
        
        // Enter the query string "Cheese"
        Thread.sleep(1000);
        WebElement query = driver.findElement(By.name("q"));
        query.sendKeys("Cheese");
        Thread.sleep(1000);

        // Sleep until the div we want is visible or 5 seconds is over
        long end = System.currentTimeMillis() + 2000;
        while (System.currentTimeMillis() < end) {
        }
        end = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < end) {
            // AA: seems to be deprecated
            // WebElement resultsDiv = driver.findElement(By.className("gac_m"));
            WebElement resultsDiv = null;
            try {
              resultsDiv = driver.findElement(By.className("gsq_a"));
            } catch (Exception e) {
              System.out.println("Error in findElement, " + e.toString());
            }

            // If results have been returned, the results are displayed in a drop down.
            if (resultsDiv.isDisplayed()) {
              break;
            }
        }

        // And now list the suggestions
        // AA: trying different classes
        // List<WebElement> allSuggestions = driver.findElements(By.xpath("//td[@class='gac_c']"));
        // List<WebElement> allSuggestions = driver.findElements(By.xpath("//td[@class='gssb_e']"));
        List<WebElement> allSuggestions = driver.findElements(By.className("gsq_a"));
        
        System.out.println("allSuggestions.size() = " + allSuggestions.size() );
        for (WebElement suggestion : allSuggestions) {
            System.out.println(suggestion.getText());
        }
     }
}
