package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Testing2 {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://automationintesting.online/";
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, TIMEOUT);
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

   @Test //TC08
    public void confirmation(){
        try {
            WebElement checkin=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[1]/div/input"));
            checkin.clear();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[5]/div[1]")).click();
            WebElement checkout=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[1]/div/input"));
            checkout.clear();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[2]/div[2]/div/div/div/div/div[2]/div[5]/div[3]")).click();
            WebElement check=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[4]/button"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            check.click();
            WebElement booknow=driver.findElement(By.xpath("//*[@id=\"rooms\"]/div/div[2]/div[2]/div/div[3]/a"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            booknow.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement reserve=driver.findElement(By.xpath("//*[@id=\"doReservation\"]"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reserve.click();
            WebElement fname=driver.findElement(By.name("firstname"));
            fname.sendKeys("ali");
            WebElement lname=driver.findElement(By.name("lastname"));
            lname.sendKeys("mohamed");
            WebElement email=driver.findElement(By.name("email"));
            email.sendKeys("ali@gmail.com");
            WebElement phone=driver.findElement(By.name("phone"));
            phone.sendKeys("012345678900");
            WebElement reservenow=driver.findElement(By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/button[1]"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reservenow.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement con=driver.findElement(By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div"));
            Assert.assertTrue(con.isDisplayed(),"confirmation message is not displayed");
            // Assert.assertEquals(con.getText().contains("Booking Confirmed"), "Booking Confirmed");
            } catch (NoSuchElementException e) {
                System.out.println("Element is not found "+e.getMessage());
                throw e;
            }
    }

    @Test //TC09
    public void overlapping() {
        try {
            // First booking
            selectDates("7", "5", "4");
            clickBookNow();
            submitBookingForm("ali", "mohamed", "ali@gmail.com", "012345678900");
            
            // Verify first booking success
            WebElement con = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'confirmation-modal')]")));
            Assert.assertTrue(con.isDisplayed(), "First booking confirmation not displayed");
            
            // Attempt overlapping booking
            driver.get(BASE_URL);
            selectDates("2", "5", "4");
            clickBookNow();
            submitBookingForm("mohamed", "ali", "mohamed@gmail.com", "012345678990");
            
            // Verify error for overlapping dates
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'alert-danger')]")));
            Assert.assertTrue(error.isDisplayed(), "Error message for overlapping dates not displayed");
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Test //TC10
    public void invaliddata() {
        try {
            selectDates("7", "5", "2");
            clickBookNow();
            
            // Submit empty form
            submitBookingForm("", "", "", "");
            
            WebElement errors = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'alert-danger')]")));
            Assert.assertTrue(errors.isDisplayed(), "Validation errors not displayed");
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Test //TC11
    public void invaliddaterange() {
        try {
            // Select invalid date range (checkout before checkin)
            selectDates("5", "7", "2");
            clickBookNow();
            
            submitBookingForm("ahmed", "hassan", "ali22@gmail.com", "012345674600");
            
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'alert-danger')]")));
            Assert.assertTrue(error.isDisplayed(), "Error message for invalid date range not displayed");
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void selectDates(String checkinDay, String checkoutDay, String weekOffset) {
        // Clear and select checkin date
        WebElement checkinInput = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[contains(@id, 'checkin')]")));
        checkinInput.clear();
        clickDate("checkin", checkinDay, weekOffset);
        
        // Clear and select checkout date
        WebElement checkoutInput = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[contains(@id, 'checkout')]")));
        checkoutInput.clear();
        clickDate("checkout", checkoutDay, weekOffset);
        
        // Click check availability button
        clickCheckAvailability();
    }

    private void clickDate(String type, String day, String weekOffset) {
        String xpath = String.format("//div[contains(@class, 'datepicker-dropdown')]//div[contains(@class, 'datepicker-days')]//td[contains(@class, 'day') and text()='%s']", day);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    private void clickCheckAvailability() {
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Check')]"))).click();
    }

    private void clickBookNow() {
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), 'Book')]"))).click();
    }

    private void submitBookingForm(String firstName, String lastName, String email, String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("doReservation"))).click();
        
        fillInputField(By.name("firstname"), firstName);
        fillInputField(By.name("lastname"), lastName);
        fillInputField(By.name("email"), email);
        fillInputField(By.name("phone"), phone);
        
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Reserve')]"))).click();
    }

    private void fillInputField(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        if (!value.isEmpty()) {
            element.sendKeys(value);
        }
    }

    private void handleException(Exception e) {
        System.err.println("Test failed due to: " + e.getMessage());
        if (driver != null) {
            System.err.println("Current URL: " + driver.getCurrentUrl());
            System.err.println("Page source: " + driver.getPageSource());
        }
        throw new RuntimeException(e);
    }
}