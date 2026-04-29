package DParagon;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class DParagonBookingTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Professional Setup: Silence unnecessary Selenium and Driver logs
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);

        ChromeOptions options = new ChromeOptions();
        // Use Incognito mode for a clean testing session
        options.addArguments("--incognito");
        // Ensure the browser starts maximized for better element visibility
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        // Explicitly maximize window (Best practice for macOS stability)
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://dparagon.com");
    }

    @Test
    public void testBookingKamarMalang() throws InterruptedException {
        // 1. Initial Pop-up Handling on page load
        handlePopup();

        // 2. Action: Enter Check-in Date
        WebElement inputTanggal = driver.findElement(By.name("check_in_date"));
        inputTanggal.clear();
        inputTanggal.sendKeys("2026-05-30");
        System.out.println("Action: Entered Check-in date: 2026-05-30");

        // 3. Action: Enter Duration
        WebElement inputDurasi = driver.findElement(By.name("duration"));
        inputDurasi.clear();
        inputDurasi.sendKeys("5");

        // 4. Action: Select Duration Type (Days/Hari)
        Select selectHari = new Select(driver.findElement(By.name("duration_type")));
        selectHari.selectByVisibleText("Hari");

        // 5. Action: Select Room Count
        Select selectKamar = new Select(driver.findElement(By.name("room_count")));
        selectKamar.selectByVisibleText("1 KAMAR");

        // 6. Action: Select Target City (Malang)
        Select selectKota = new Select(driver.findElement(By.name("city_id")));
        selectKota.selectByVisibleText("Malang");

        // 7. Action: Click Search Button
        WebElement btnCari = driver.findElement(By.xpath("//span[text()='CARI']"));
        btnCari.click();
        System.out.println("Action: Search form submitted for Malang.");

        // 8. Handle Potential Pop-up after clicking Search
        Thread.sleep(2000);
        handlePopup();

        // 9. Validation: Verify Redirection to Malang Search Results (City ID: 3573)
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl != null) {
            Assert.assertTrue(currentUrl.contains("3573"),
                    "Error: URL validation failed. Expected City ID 3573 not found in: " + currentUrl);
            System.out.println("Success: Successfully navigated to Malang search results!");
        }

        // Keeping browser open for manual inspection as requested
        System.out.println("Scenario Complete! Waiting 45 seconds for manual inspection...");
        Thread.sleep(45000);
    }

    /**
     * Reusable Helper: Detects and closes promotional pop-ups if they appear.
     * Uses .getFirst() for modern Java 21+ standards.
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
            // No pop-up present, continuing test execution
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
