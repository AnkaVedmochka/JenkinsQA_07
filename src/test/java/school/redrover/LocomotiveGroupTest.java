package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LocomotiveGroupTest {
    @Test
    public void demoqaTextBoxTest() {
        String fullName = "Tom Jonson";
        String email = "mail@mail.com";

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/text-box");

        String pageTitleText = driver.findElement(By.className("main-header")).getText();
        Assert.assertEquals(pageTitleText, "Text Box");

        WebElement fullNameTextBox = driver.findElement(By.cssSelector("#userName"));
        fullNameTextBox.sendKeys(fullName);

        WebElement emailTextBox = driver.findElement(By.id("userEmail"));
        emailTextBox.sendKeys(email);

        WebElement submitButton = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();

        String actualFullName = driver
                .findElement(By.id("name"))
                .getText();
        Assert.assertEquals(actualFullName, "Name:" + fullName);

        String actualEmail = driver
                .findElement(By.xpath("//*[@id=\"email\"]"))
                .getText();

        Assert.assertEquals(actualEmail, "Email:" + email);

        driver.quit();
    }

    @Test
    public void testLink() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            String linkExpected = "https://demoqa.com/";

            driver.get("https://demoqa.com/links");
            String originalWindow = driver.getWindowHandle();
            WebElement link = driver.findElement(By.xpath("//*[@id=\"simpleLink\"]"));
            String linkActual = link.getAttribute("href");

            Assert.assertEquals(linkActual, linkExpected);

            link.click();

            Thread.sleep(1000);

            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class=\"banner-image\"]")).isDisplayed();

        } finally {
            driver.quit();
        }
    }

    @Test
    public void checkRadioButton() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://demoqa.com/radio-button");


        selectRadioButton(driver, "Yes");
        WebElement textRadioButton = driver.findElement(By.xpath("//p[@class='mt-3']"));
        Assert.assertEquals(textRadioButton.getText(), "You have selected Yes");

        Thread.sleep(3000);

        selectRadioButton(driver, "Impressive");
        Assert.assertEquals(textRadioButton.getText(), "You have selected Impressive");

        Thread.sleep(3000);

        driver.close();
    }
    public static void selectRadioButton(WebDriver driver, String value) {
        WebElement RadioButton = driver.findElement(By.xpath("//label[normalize-space()='" + value + "']"));
        RadioButton.click();
    }
}
