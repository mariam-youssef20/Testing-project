package com.example;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;

import java.time.Duration;

public class TestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final String BASE_URL = "https://automationintesting.online";
    
    @BeforeMethod
    public void setup() {
        try {
            // Setup ChromeDriver using WebDriverManager
            WebDriverManager.chromedriver().setup();
            
            // Configure Chrome options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            
            // Initialize WebDriver
            driver = new ChromeDriver(options);
            
            // Set timeouts
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            
            // Initialize WebDriverWait with longer timeout
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            System.out.println("WebDriver initialized successfully");
        } catch (Exception e) {
            System.err.println("Exception in setup: " + e.getMessage());
            e.printStackTrace();
            if (driver != null) {
                driver.quit();
            }
            throw new RuntimeException("Setup failed", e);
        }
    }
    
    @AfterMethod
    public void tearDown() {
        try {
            // Print summary of test results
            System.out.println("Test completed - Current URL: " + (driver != null ? driver.getCurrentUrl() : "N/A"));
            System.out.println("----------------------------------------------------");
            
            // Close the browser
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("Error during teardown: " + e.getMessage());
        }
    }
    
    protected void enterText(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("Failed to enter text: " + e.getMessage());
            throw e;
        }
    }
    
   
    
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    protected void waitForPageToLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            System.err.println("Page didn't load properly: " + e.getMessage());
        }
    }
    
    protected void waitForAjax() {
        try {
            wait.until(webDriver -> (Boolean)((JavascriptExecutor) webDriver)
                .executeScript("return jQuery.active == 0"));
        } catch (Exception e) {
            // jQuery might not be available, continue anyway
        }
        try {
            Thread.sleep(500); // Small additional wait
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}