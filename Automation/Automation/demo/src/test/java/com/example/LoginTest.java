
package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class LoginTest extends TestBase {
    
    private final String ADMIN_URL = BASE_URL + "/admin";
     ExtentReports report;
    ExtentTest test;

    
    @DataProvider(name = "loginCredentials")
    public Object[][] loginCredentials() {
        return new Object[][] {
            {"admin", "password", true},      // TC01: Valid credentials
            {"wrongadmin", "password", false}, // TC02: Invalid username
            {"admin", "wrongpassword", false}, // TC02: Invalid password
            {"", "", false}                   // TC03: Blank fields
        };
    }
    
    /**
     * TC01: Verify admin login with valid credentials
     * TC02: Verify login with invalid username/password
     * TC03: Attempt login with blank fields → verify validation message
     * TC04: Validate successful login redirects to admin dashboard
     */
    @Test(dataProvider = "loginCredentials")
    public void testAdminLogin(String username, String password, boolean shouldSucceed) {
        try {
            System.out.println("Starting test with credentials: " + username + " / " + password);
            
            // Navigate directly to the admin login page
            driver.get(ADMIN_URL);
            System.out.println("Navigated directly to admin page");
            
            System.out.println("Waiting for login form...");
            // Wait for login form to be visible and interactable
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            
            // Enter credentials with explicit waits
            WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
            
            System.out.println("Entering credentials...");
            // Clear and enter username and password
            enterText(usernameField, username);
            enterText(passwordField, password);
            
            // Wait for login button and click it
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("doLogin")));
            System.out.println("Clicking login button...");
            
            try {
                loginButton.click();
            } catch (Exception e) {
                System.out.println("Standard click failed, trying JavaScript click...");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", loginButton);
            }
            
            // Add small wait for the action to complete
            Thread.sleep(1000);
            
            if (shouldSucceed) {
                // TC01 & TC04: Valid login should redirect to admin dashboard
                System.out.println("Checking if login successful...");
                
                // Wait for admin dashboard to load - use a more reliable selector
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/admin"),
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='roomlisting']")),
                    ExpectedConditions.visibilityOfElementLocated(By.className("room-details"))
                ));
                
                // Additional verification that we're on the dashboard
                boolean isOnDashboard = driver.findElements(By.cssSelector("[data-testid='roomlisting'], .room-details")).size() > 0;
                Assert.assertTrue(isOnDashboard, "Admin dashboard wasn't displayed after successful login");
                
                System.out.println("TC01/TC04: Login successful and redirected to admin dashboard");
            } else {
                System.out.println("Checking for error message...");
                // TC02 & TC03: Invalid login should show error message or stay on login page
                
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Bad credentials')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.id("username")) // Still on login page
                ));
                
                // Check if error message is present or still on login page
                boolean hasErrorMessage = driver.findElements(By.cssSelector(".alert")).size() > 0 || 
                                          driver.findElements(By.xpath("//div[contains(text(), 'Bad credentials')]")).size() > 0;
                boolean stillOnLoginPage = driver.findElements(By.id("username")).size() > 0;
                
                Assert.assertTrue(hasErrorMessage || stillOnLoginPage, 
                                 "Expected either error message or to remain on login page for invalid credentials");
                
                if (hasErrorMessage) {
                    System.out.println("TC02/TC03: Error message displayed for invalid credentials");
                } else {
                    System.out.println("TC02/TC03: Still on login page after invalid credentials");
                }
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed with exception: " + e.getMessage());
        }
    }
    @AfterClass
    public void closeReport() {
        report.flush();
    }
}