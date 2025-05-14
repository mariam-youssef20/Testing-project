
package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class RoomManagement {

    WebDriver driver;
    WebDriverWait wait;
    String baseUrl = "https://automationintesting.online/admin";
    String roomsUrl = baseUrl + "/rooms";
    ExtentReports report;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void loginAsAdmin() {

        driver.get(baseUrl);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("doLogin")).click();
        wait.until(ExpectedConditions.urlContains("/rooms")); // wait until redirected to rooms page
    }

    @Test(priority = 12)
    public void TC12_addNewRoom() {
        // test = report.createTest("Add New Room");
        // test = extent.createTest("Verify Booking Test");
        loginAsAdmin();
        // test.info("Opening booking to add new room ");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement roomNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("roomName")));
        roomNameInput.sendKeys("920");

        Select typeDropdown = new Select(driver.findElement(By.id("type")));
        typeDropdown.selectByVisibleText("Double");

        Select accessibleDropdown = new Select(driver.findElement(By.id("accessible")));
        accessibleDropdown.selectByVisibleText("true"); // or "false"

        WebElement roomPriceInput = driver.findElement(By.id("roomPrice"));
        roomPriceInput.sendKeys("150");

        driver.findElement(By.id("createRoom")).click();

        // verify the room is listed
        WebElement addedRoomName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[text()='920']")));
        Assert.assertTrue(addedRoomName.isDisplayed(), "Room 920 should appear in theroom list.");
        // test.pass("Room has been added ");
    }

    @Test(priority = 13)
    public void TC13_editRoom() {

        // 1️⃣ Click on the room name to go to room details page
        WebElement roomNameLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[text()='920']"))); // click room 101 for example
        roomNameLink.click();

        // 2️⃣ Click the Edit button on the room details page using the class
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".btn.btn-outline-primary.float-sm-end"))); // using class asselector
        editButton.click();

        // 3️⃣ Update fields
        WebElement roomNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("roomName")));
        roomNameInput.clear();
        roomNameInput.sendKeys("920");

        // Use Select to choose a room type from the dropdown
        Select typeDropdown = new Select(driver.findElement(By.id("type")));
        typeDropdown.selectByVisibleText("Single"); // select "Suite" from thedropdown

        Select accessibleDropdown = new Select(driver.findElement(By.id("accessible")));
        accessibleDropdown.selectByVisibleText("false");

        WebElement roomPriceInput = driver.findElement(By.id("roomPrice"));
        roomPriceInput.clear();
        roomPriceInput.sendKeys("200");

        // 4️⃣ Click the Update button to save changes
        driver.findElement(By.id("update")).click();

        // 5️⃣ Verify updated values in the room list
        driver.get(roomsUrl); // return to the rooms list page
        WebElement updatedRoomName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[text()='Single']")));
        Assert.assertTrue(updatedRoomName.isDisplayed(), "Updated room name should appear in the room list.");
    }

    @Test(priority = 14)
    public void TC14_deleteRoomByName() {
        // loginAsAdmin();

        // Find the room name element (e.g., roomName101) using XPath
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement roomName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[text()='920']"))); // Locate room by its name (e.g., 101)

        // Find the delete button associated with this room using XPath
        WebElement deleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//p[text()='920']/ancestor::div[contains(@class,'detail')]//span[contains(@class, 'roomDelete')]")));

        // Click the delete button
        deleteButton.click();

        // Wait for the room to be deleted by checking if it no longer appears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//p[text()='920']"))); // Wait until the room is no longer visible

        // Verify that the room no longer appears in the room list
        try {
            // Try to find the room name again
            WebElement deletedRoom = driver.findElement(By.xpath("//p[text()='920']"));
            Assert.fail("The room was not deleted successfully.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // This is expected if the room has been deleted successfully
            System.out.println("Room 920 successfully deleted.");
        }
    }

    @Test(priority = 15)
    public void TC15_addRoomWithMissingFields() {
        // loginAsAdmin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Leave roomName empty
        WebElement roomNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("roomName")));
        roomNameInput.sendKeys("");

        // Fill other fields
        Select typeDropdown = new Select(driver.findElement(By.id("type")));
        typeDropdown.selectByVisibleText("Double");

        Select accessibleDropdown = new Select(driver.findElement(By.id("accessible")));
        accessibleDropdown.selectByVisibleText("true");

        WebElement roomPriceInput = driver.findElement(By.id("roomPrice"));
        roomPriceInput.sendKeys("150");

        // Click Create Room
        driver.findElement(By.id("createRoom")).click();

        // Check for validation message
        try {
            WebElement validationMessage = driver.findElement(By.cssSelector("#roomName:invalid"));
            Assert.assertTrue(validationMessage != null, "Validation error should appear for missing room name.");
            System.out.println("Validation error appeared as expected.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("No HTML5 validation message found → checking room not added.");

            // Ensure the room was not created (e.g., no roomName='(empty)' or
            // similar)
            boolean roomExists;
            try {
                driver.findElement(By.xpath("//p[text()='']")); // empty roomName unlikely
                roomExists = true;
            } catch (org.openqa.selenium.NoSuchElementException ex) {
                roomExists = false;
            }

            Assert.assertFalse(roomExists, "Room with missing fields should not be added.");
        }
    }

    @AfterClass
    public void closeReport() {
        report.flush();
    }

}
