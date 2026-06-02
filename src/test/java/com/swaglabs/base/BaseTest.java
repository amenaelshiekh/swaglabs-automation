package com.swaglabs.base;

import com.swaglabs.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestResult;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // disable password manager + leak/breach detection (prefs)
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        // command-line flags - applied at launch, before any profile race
        options.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding,AutofillServerCommunication");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--guest");  // guest profile has no password-manager state at all

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("base.url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // capture a screenshot only if the test failed, while the browser is still open
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment(
                    "Screenshot on failure", "image/png", "png", png);
        }
        if (driver != null) {
            driver.quit();
        }
    }
}