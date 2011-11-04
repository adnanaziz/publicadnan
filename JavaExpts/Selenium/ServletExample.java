// package org.openqa.selenium.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ServletExample  {
    public static void main(String[] args) {
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        // And now use this to visit the temperature conversion servlet
        driver.get("http://adnan.appspot.com/testing-lab-login.html");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("userId"));

        // Enter user name, must clear first, since it already has some text
        element.clear();
        element.sendKeys("andy");

        element = driver.findElement(By.name("userPassword"));
        element.sendKeys("apple");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
        
        // Print the page
        System.out.println("Page is: " + driver.getPageSource());

        // Now let's do some converting
        element = driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("-40.0");
        element.submit();
        System.out.println("Page is: " + driver.getPageSource());
        driver.navigate().back();
        element = driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("40.0");
        element.submit();
        System.out.println("Page is: " + driver.getPageSource());
        driver.navigate().back();
        driver.navigate().back();
        System.out.println("Page is: " + driver.getPageSource());
        WebElement user = driver.findElement(By.name("userId"));
        user.clear();
        user.sendKeys("bob");
        WebElement pswd = driver.findElement(By.name("userPassword"));
        pswd.sendKeys("bathtub");
        pswd.submit();
        System.out.println("Page is: " + driver.getPageSource());
        element = driver.findElement(By.name("farenheitTemperature"));
        element.clear();
        element.sendKeys("40.0");
        element.submit();
        System.out.println("Page is: " + driver.getPageSource());
        driver.navigate().back();
        driver.navigate().back();
        System.out.println("Page is: " + driver.getPageSource());
        user = driver.findElement(By.name("userId"));
        user.clear();
        user.sendKeys("hacker");
        pswd = driver.findElement(By.name("userPassword"));
        pswd.sendKeys("xxx");
        pswd.submit();
        System.out.println("Page is: " + driver.getPageSource());
        WebElement loginLink = driver.findElement(By.linkText("link"));
        loginLink.click();
        System.out.println("Page is: " + driver.getPageSource());

    }
}
