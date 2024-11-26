import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dev.failsafe.internal.util.Assert;

public class Amazon {
	WebDriver driver = new ChromeDriver();

	String myWebsite = "https://www.amazon.com/ref=nav_logo";
	
    Random random = new Random();

    @BeforeClass
    public void setup() {
        // Setup ChromeDriver and open the Amazon website
    	driver.get(myWebsite);
        driver.manage().window().maximize();
       
    }

    @Test(priority = 1)
    public void testGiftCardSelection() throws InterruptedException {
    	
    	WebElement button = driver.findElement(By.cssSelector(".a-button-input")) ;
    			button.click(); ; 
        // 1. Navigate to the Gift Cards section
       WebElement GiftCards = driver.findElement(By.linkText("Gift Cards"));
       
       GiftCards.click();
        Thread.sleep(5000);

        // 2. Select a random gift card
        List<WebElement> giftCards = driver.findElements(By.cssSelector(".a-link-normal.a-text-normal"));
        WebElement randomGiftCard = giftCards.get(random.nextInt(giftCards.size()));
        randomGiftCard.click();
        Thread.sleep(5000);

        // 3. Select a random amount
        List<WebElement> amounts = driver.findElements(By.cssSelector("button[value]"));
        WebElement randomAmount = amounts.get(random.nextInt(amounts.size()));
        String selectedAmount = randomAmount.getAttribute("value");
        randomAmount.click();

        // Assertion: Verify the amount is selected
        WebElement selectedAmountElement = driver.findElement(By.cssSelector("button.selected"));
        org.testng.Assert.assertEquals(selectedAmountElement.getAttribute("value"), selectedAmount, "Amount selection failed!");

        // 4. Fill in other information
        driver.findElement(By.id("gc-order-form-recipients")).sendKeys("recipient@example.com");
        driver.findElement(By.id("gc-order-form-senderName")).sendKeys("Sender Name");

        // 5. Select a random message
        String[] messages = {"Enjoy your gift", "This is from me to you", "Hope you like it"};
        String randomMessage = messages[random.nextInt(messages.length)];
        driver.findElement(By.id("gc-order-form-message")).sendKeys(randomMessage);

        // 6. Assert message was added correctly
       org.testng.Assert.assertEquals(driver.findElement(By.id("gc-order-form-message")).getAttribute("value"), randomMessage, "Message selection failed!");
    }

    @AfterClass(enabled = false)
    public void teardown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
	
	

