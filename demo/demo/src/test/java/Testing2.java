import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.ExtentReportManager;
public class Testing2{
    WebDriver driver;
    ExtentReports report;
    ExtentTest test;
    @BeforeMethod
    public void setup(){
     report= ExtentReportManager.getReportInstance();
        ChromeOptions option=new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        driver=new ChromeDriver(option);
        driver.get("https://automationintesting.online/");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test //TC08
    public void confirmation(){
        test=report.createTest("confirmation");
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
    public void overlapping(){
        try {
            WebElement checkin=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[1]/div/input"));
            checkin.clear();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[3]/div[7]")).click();
            WebElement checkout=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[1]/div/input"));
            checkout.clear();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[2]/div[2]/div/div/div/div/div[2]/div[4]/div[5]")).click();
            WebElement check=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[4]/button"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            check.click();
            WebElement booknow=driver.findElement(By.xpath("//*[@id=\"rooms\"]/div/div[2]/div[1]/div/div[3]/a"));
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
            driver.get("https://automationintesting.online/");
            try {
            Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[1]/div/input")).clear();
            try {
            Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[4]/div[2]")).click();
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[1]/div/input")).clear();
            try {
            Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[2]/div[2]/div/div/div/div/div[2]/div[4]/div[5]")).click();
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[4]/button")).click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"rooms\"]/div/div[2]/div[1]/div/div[3]/a")).click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"doReservation\"]")).click();
            driver.findElement(By.name("firstname")).sendKeys("mohamed");
            driver.findElement(By.name("lastname")).sendKeys("ali");
            driver.findElement(By.name("email")).sendKeys("mohamed@gmail.com");
            driver.findElement(By.name("phone")).sendKeys("012345678990");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/button[1]")).click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement error = driver.findElement(By.xpath("//*[@id=\"__next_error__\"]/body/div/div/h2"));
            Assert.assertTrue(error.isDisplayed(), "error message is not displayed");
            Assert.assertEquals(error.getText(), "Application error: a client-side exception has occurred while loading automationintesting.online (see the browser console for more information).");
        } catch (NoSuchElementException e) {
                System.out.println("Element is not found" + e.getMessage());
                throw e;
            
            }
    }
    @Test //TC10
    public void invaliddata(){
       try {
            WebElement checkin=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[1]/div/input"));
            checkin.clear();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[2]/div[7]")).click();
            WebElement checkout=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[1]/div/input"));
            checkout.clear();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[2]/div[2]/div/div/div/div/div[2]/div[3]/div[5]")).click();
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
            fname.sendKeys("");
            WebElement lname=driver.findElement(By.name("lastname"));
            lname.sendKeys("");
            WebElement email=driver.findElement(By.name("email"));
            email.sendKeys("");
            WebElement phone=driver.findElement(By.name("phone"));
            phone.sendKeys("");
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
            WebElement errors=driver.findElement(By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/div[5]"));
            Assert.assertTrue(errors.isDisplayed(),"fileds error not displayed");
            /*  Assert.assertEquals(errors.getText(), "Firstname should not be blank");
            WebElement lnameerror=driver.findElement(By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/div[5]/ul/li[2]/text()"));
            Assert.assertTrue(lnameerror.isDisplayed(),"lname error not displayed");
            Assert.assertEquals(lnameerror.getText(), "Lastname should not be blank");
            WebElement emailerror=driver.findElement(By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/div[5]/ul/li[5]/text()"));
            Assert.assertTrue(emailerror.isDisplayed(),"email error not displayed");
            Assert.assertEquals(emailerror.getText(), "must not be empty");
            WebElement phoneerror=driver.findElement(By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/div[5]/ul/li[5]/text()"));
            Assert.assertTrue(phoneerror.isDisplayed(),"phone error not displayed");
            Assert.assertEquals(phoneerror.getText(), "must not be empty");
            */
        } catch (NoSuchElementException e) {
            System.out.println("Element is not found"+e.getMessage());
            throw e;
            }
    }
    @Test //TC11
    public void invaliddaterange(){
        try {
            WebElement checkin=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[1]/div/input"));
            checkin.clear();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/div[2]/div[2]/div/div/div/div/div[2]/div[3]/div[5]")).click();
            WebElement checkout=driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[1]/div/input"));
            checkout.clear();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[2]/div[2]/div[2]/div/div/div/div/div[2]/div[2]/div[7]")).click();
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
            fname.sendKeys("ahmed");
            WebElement lname=driver.findElement(By.name("lastname"));
            lname.sendKeys("hassan");
            WebElement email=driver.findElement(By.name("email"));
            email.sendKeys("ali22@gmail.com");
            WebElement phone=driver.findElement(By.name("phone"));
            phone.sendKeys("012345674600");
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
            WebElement error = driver.findElement(By.xpath("//*[@id=\"__next_error__\"]/body/div/div/h2"));
            Assert.assertTrue(error.isDisplayed(), "error message is not displayed");
            Assert.assertEquals(error.getText(), "Application error: a client-side exception has occurred while loading automationintesting.online (see the browser console for more information).");
            } catch (NoSuchElementException e) {
                System.out.println("Element is not found "+e.getMessage());
                throw e;
            }
    }
     @AfterMethod
    public void after(){
        System.out.println("title is "+driver.getTitle());
        System.out.println("current url: "+driver.getCurrentUrl());
        driver.quit();
    }  
    @AfterClass
    public void closereport(){
        report.flush();
    }
}    