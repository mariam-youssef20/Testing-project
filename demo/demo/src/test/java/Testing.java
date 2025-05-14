import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class Testing{
    WebDriver driver;
    @BeforeMethod
    public void setup(){
        System.setProperty("webdriver.chrome.driver","D:\\Ali\\DEPI\\testing\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions option=new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        driver=new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.get("https://automationintesting.online/#rooms");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } 
    @Test
    public void validcontact(){
        try {
            WebElement name=driver.findElement(By.id("name"));
            name.sendKeys("ali");
            WebElement email=driver.findElement(By.id("email"));
            email.sendKeys("ali@gmail.com");
            WebElement phone=driver.findElement(By.id("phone"));
            phone.sendKeys("01234567890");
            WebElement subject=driver.findElement(By.id("subject"));
            subject.sendKeys("alimahomed12315");
            WebElement message=driver.findElement(By.id("description"));
            message.sendKeys("ahjkghfajhgjhfdgdhfehrugisiafghfj");
            WebElement submit=driver.findElement(By.xpath("//*[@id=\"contact\"]/div/div/div/div/div/form/div[6]/button"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            submit.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement sucess=driver.findElement(By.xpath("//*[@id=\"contact\"]/div/div/div/div/div"));
            Assert.assertTrue(sucess.isDisplayed(), "success message not displayed");
            //  Assert.assertEquals(sucess.getText(),"Thanks for getting in touch"+name+"!"+"We'll get backto you"+subject+"as soon as possible");
        }   catch (NoSuchElementException e) {
                System.out.println("Element is not found"+e.getMessage());
                throw e;
            }
    }
    @Test
    public void missingfileds(){
        try {
                WebElement name=driver.findElement(By.id("name"));
                name.sendKeys("ali");
                WebElement email=driver.findElement(By.id("email"));
                email.sendKeys("");
                WebElement phone=driver.findElement(By.id("phone"));
                phone.sendKeys("01234567890");
                WebElement subject=driver.findElement(By.id("subject"));
                subject.sendKeys("alimahomed12315");
                WebElement message=driver.findElement(By.id("description"));
                message.sendKeys("ahjkghfajhgjhfdgdhfehrugisiafghfj");
                WebElement submit=driver.findElement(By.xpath("//*[@id=\"contact\"]/div/div/div/div/div/form/div[6]/button"));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                submit.click();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WebElement emailerror=driver.findElement(By.xpath("//*[@id='contact']/div/div/div/div/div/div/p"));
                Assert.assertTrue(emailerror.isDisplayed(), "email error message not displayed");
                Assert.assertEquals(emailerror.getText(),"Email may not be blank");
            }   catch (NoSuchElementException e) {
                System.out.println("Element is not found"+e.getMessage());
                throw e;
        }

    }
    @Test
    public void invalidformat(){
       try {
            WebElement name=driver.findElement(By.id("name"));
            name.sendKeys("ali");
            WebElement email=driver.findElement(By.id("email"));
            email.sendKeys("aliss");
            WebElement phone=driver.findElement(By.id("phone"));
            phone.sendKeys("01234567890");
            WebElement subject=driver.findElement(By.id("subject"));
            subject.sendKeys("alimahomed12315");
            WebElement message=driver.findElement(By.id("description"));
            message.sendKeys("ahjkghfajhgjhfdgdhfehrugisiafghfjadjkfdsghjlaksfhkjvgnflkjabghkjdfhkjbghfjahugiaerulhlfjkghafjkdhvgjkhaerkjghjkdfhgjk");
        
            WebElement submit=driver.findElement(By.xpath("//*[@id=\"contact\"]/div/div/div/div/div/form/div[6]/button"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            submit.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement emailformaterror=driver.findElement(By.xpath("//*[@id=\"contact\"]/div/div/div/div/div/div/p"));
            Assert.assertTrue(emailformaterror.isDisplayed(), "email format error message not displayed");
            Assert.assertEquals(emailformaterror.getText(),"must be a well-formed email address");
        }   catch (NoSuchElementException e) {
                System.out.println("Element is not found"+e.getMessage());
                throw e;

            }
    }
    @AfterMethod
    public void after(){
        System.out.println("title is "+driver.getTitle());
        driver.quit();
    }  



}