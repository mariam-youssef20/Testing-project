package com.example;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.concurrent.TimeUnit;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


/**
 * Tests for the contact form functionality (TC05-TC07)
 * TC05: Submit valid contact form → verify success message
 * TC06: Submit form with missing fields → verify validation errors
 * TC07: Submit invalid email format → verify error
 */
public class ContactFormTest extends TestBase {
    private final String CONTACT_URL = BASE_URL + "/#contact";
     ExtentReports report;
    ExtentTest test;

    
    @DataProvider(name = "contactFormData")
    public Object[][] contactFormData() {
        return new Object[][] {
            // TC05: Valid form submissions
            {"Ali mohamed", "ali@gmail.com", "01234567890", "Room Inquiry", "Test message for testing contact form page", true, "TC05 - Valid form"},
            
            
            // TC06: Missing required fields
            {"", "ali@gmail.com", "01234567890", "Room Inquiry", "Test message for testing contact form page", false, "TC06 - Missing name"},
            {"Ali mohamed", "", "01234567890", "Room Inquiry", "Test message for testing contact form page", false, "TC06 - Missing email"},
            {"Ali mohamed", "ali@gmail.com", "", "Room Inquiry", "Test message for testing contact form page", false, "TC06 - Missing phone"},
            
            // TC07: Invalid email formats
            {"Ali mohamed", "ali@domain", "01234567890", "Room Inquiry", "Test message for testing contact form page", false, "TC07 - Invalid email (no @)"},
           
            
            // Additional field validation tests
            {"Ali123", "ali@gmail.com", "1234567890", "Room Inquiry", "Test message for testing contact form page", false, "Field Validation - Name with numbers"},
            {"Ali@Doe", "ali@gmail.com", "1234567890", "Room Inquiry", "Test message for testing contact form page", false, "Field Validation - Name with special chars"},
            {"Ali mohamed", "ali@gmail.com", "123abc456", "Room Inquiry", "Test message for testing contact form page", false, "Field Validation - Phone with letters"},
            {"Ali mohamed", "ali@gmail.com", "12345", "Room Inquiry", "Test message for testing contact form page", false, "Field Validation - Phone too short"},
            {"Ali mohamed", "ali@gmail.com", "1234567890123456", "Room Inquiry", "Test message for testing contact form page", false, "Field Validation - Phone too long"}
        };
    }
    
    @Test(dataProvider = "contactFormData")
    public void testContactForm(String name, String email, String phone, String subject, 
                              String message, boolean shouldSucceed, String testDescription) {
        System.out.println("\n=== Running Test: " + testDescription + " ===");
        
        try {
            // 1. Navigate to contact page
            navigateToContactPage();
            
            // 2. Fill and submit form
            fillAndSubmitForm(name, email, phone, subject, message);
            
            // 3. Verify results
            verifyFormSubmission(shouldSucceed, testDescription);
            
            System.out.println("PASS: " + testDescription);
        } catch (AssertionError e) {
            System.err.println("FAIL: " + testDescription + " - " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR: " + testDescription + " - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    private void navigateToContactPage() {
        System.out.println("Navigating to contact page...");
        driver.get(CONTACT_URL);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        
        // Ensure we're on the contact section
        if (!driver.getCurrentUrl().contains("#contact")) {
            WebElement contactLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Contact')]")));
            contactLink.click();
        }
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("section#contact form")));
    }
    
    private void fillAndSubmitForm(String name, String email, String phone, 
                                 String subject, String message) {
        System.out.println("Filling form with: " + 
                         "\nName: " + name + 
                         "\nEmail: " + email + 
                         "\nPhone: " + phone);
        
        // Fill form fields
        if (name != null && !name.isEmpty()) {
            setInputValue(By.cssSelector("input[data-testid='ContactName']"), name);
        }
        
        if (email != null && !email.isEmpty()) {
            setInputValue(By.cssSelector("input[data-testid='ContactEmail']"), email);
        }
        
        if (phone != null && !phone.isEmpty()) {
            setInputValue(By.cssSelector("input[data-testid='ContactPhone']"), phone);
        }
        
        if (subject != null && !subject.isEmpty()) {
            setInputValue(By.cssSelector("input[data-testid='ContactSubject']"), subject);
        }
        
        if (message != null && !message.isEmpty()) {
            setInputValue(By.cssSelector("textarea[data-testid='ContactDescription']"), message);
        }
        
        // Submit form
        System.out.println("Submitting form...");
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@class, 'btn-primary') and contains(text(), 'Submit')]")));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", submitButton);
    }
    
    private void setInputValue(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }
    
    private void verifyFormSubmission(boolean shouldSucceed, String testDescription) {
        if (shouldSucceed) {
            // Verify success message
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Thank') or contains(text(), 'Success')]")));
            Assert.assertTrue(successMessage.isDisplayed(), 
                "Success message not displayed after valid form submission");
            
            System.out.println("Success message displayed: " + successMessage.getText());
        } else {
            // Verify error indication
            boolean hasFieldErrors = driver.findElements(By.cssSelector(".is-invalid, .error")).size() > 0;
            boolean hasGeneralError = driver.findElements(By.cssSelector(".alert-danger")).size() > 0;
            
            Assert.assertTrue(hasFieldErrors || hasGeneralError, 
                "Expected validation errors not displayed for invalid submission");
            
            if (hasFieldErrors) {
                System.out.println("Field validation errors displayed");
            }
            if (hasGeneralError) {
                System.out.println("General error message displayed: " + 
                    driver.findElement(By.cssSelector(".alert-danger")).getText());
            }
            
            // Verify form wasn't submitted
            WebElement form = driver.findElement(By.cssSelector("section#contact form"));
            Assert.assertTrue(form.isDisplayed(), "Form should still be visible after failed submission");
        }
    }
    @AfterClass
    public void closeReport() {
        report.flush();
    }
}