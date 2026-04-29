package DParagon;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class DParagonTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Disable unnecessary Selenium manager and driver logs to keep the console clean
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        // Explicitly maximize for macOS stability
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://dparagon.com");
    }

    @Test
    public void testFullNavigationPath() throws InterruptedException {
        // 1. Mapping: Menu Label -> Expected Keyword in URL
        Map<String, String> menuUrls = new LinkedHashMap<>();
        menuUrls.put("Beranda", "dparagon.com");
        menuUrls.put("Kerjasama", "partnership");
        menuUrls.put("Corporate Sales", "corporate-sales");
        menuUrls.put("Cross Branding", "cross-branding");
        menuUrls.put("Trip & Tour", "tour-packages");
        menuUrls.put("Tentang", "tentang-kami");
        menuUrls.put("Promo", "promo");
        menuUrls.put("Galeri", "gallery");
        menuUrls.put("Kontak", "contact");

        for (Map.Entry<String, String> entry : menuUrls.entrySet()) {
            String menuName = entry.getKey();
            String expectedKeyword = entry.getValue();

            // 2. Handle promotional pop-ups
            handlePopup();

            // 3. ACTION: Open the Hamburger Menu
            try {
                WebElement hamburger = driver.findElement(By.xpath("//*[@id='Top_navbar']/button/div/i"));
                hamburger.click();
                System.out.println("Action: Opening hamburger menu for: " + menuName);
                Thread.sleep(1500);
            } catch (Exception e) {
                Assert.fail("Critical Error: Hamburger menu not found.");
            }

            // 4. ACTION: Locate and Click the link directly
            try {
                String targetXpath;
                if (menuName.equals("Trip & Tour")) {
                    targetXpath = "//a[.//span[contains(text(),'Trip')]]";
                } else {
                    targetXpath = "//a[.//span[text()='" + menuName + "']]";
                }

                WebElement menuLink = driver.findElement(By.xpath(targetXpath));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuLink);
                System.out.println("Success: Clicked on menu: " + menuName);
            } catch (Exception e) {
                // Fallback locator using getFirst() to satisfy Java 21+ standards
                List<WebElement> fallbacks = driver.findElements(By.xpath("//a[contains(.,'" + menuName + "')]"));
                if (!fallbacks.isEmpty()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fallbacks.getFirst());
                    System.out.println("Success: Clicked on fallback menu: " + menuName);
                }
            }

            // 5. VALIDATION: Verify URL redirection (Null-safe check)
            Thread.sleep(3000);
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl != null) {
                Assert.assertTrue(currentUrl.toLowerCase().contains(expectedKeyword.toLowerCase()),
                        "Error: Menu '" + menuName + "' navigated to: " + currentUrl);
                System.out.println("Success: URL verified for '" + menuName + "'");
            }

            // 6. RESET: Return to Home Page (except for the last menu)
            if (!menuName.equals("Kontak")) {
                driver.get("https://dparagon.com");
                Thread.sleep(2000);
            }
            System.out.println("----------------------------------------------");
        }
    }

    /**
     * Standardized pop-up handling method using modern Java .getFirst()
     */
    public void handlePopup() {
        try {
            List<WebElement> closeButtons = driver.findElements(By.xpath("//button[@class='close']"));
            if (!closeButtons.isEmpty() && closeButtons.getFirst().isDisplayed()) {
                closeButtons.getFirst().click();
                System.out.println("Success: Intercepted and closed a pop-up.");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // No pop-up present
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
