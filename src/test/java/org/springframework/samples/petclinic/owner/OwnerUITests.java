package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerUITests {
	
	@LocalServerPort
	private int port;

	private String username;
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "./src/test/java/resources/selenium/geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	
	@Test
	public void testLoginAsAdmin() throws Exception {
		testProbandoCosasDelOwner();
	}





	


	public void testProbandoCosasDelOwner() throws Exception {
		driver.get("http://localhost:"+port);
		driver.findElement(By.xpath("//a[@id='signinbar']/strong")).click();
		driver.findElement(By.xpath("//a[@id='signowner']")).click();
	    driver.findElement(By.id("firstName")).click();
	    driver.findElement(By.id("firstName")).clear();
	    driver.findElement(By.id("firstName")).sendKeys("Shane");
	    driver.findElement(By.id("lastName")).click();
	    driver.findElement(By.id("lastName")).clear();
	    driver.findElement(By.id("lastName")).sendKeys("Battier");
	    driver.findElement(By.id("address")).click();
	    driver.findElement(By.id("address")).click();	  
	    driver.findElement(By.id("address")).clear();
	    driver.findElement(By.id("address")).sendKeys("Loius St");
	    driver.findElement(By.id("city")).click();
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys("St Marie Du Mont");
	    driver.findElement(By.id("telephone")).click();
	    driver.findElement(By.id("telephone")).clear();
	    driver.findElement(By.id("telephone")).sendKeys("942124786");
	    driver.findElement(By.id("user.username")).click();
	    driver.findElement(By.id("user.username")).clear();
	    driver.findElement(By.id("user.username")).sendKeys("shane");
	    driver.findElement(By.id("user.password")).click();
	    driver.findElement(By.id("user.password")).clear();
	    driver.findElement(By.id("user.password")).sendKeys("battier");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.id("login")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("shane");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("battier");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//a[@id='ownerbar']/strong")).click();
	    driver.findElement(By.xpath("//a[@id='profile']")).click();
	    driver.findElement(By.linkText("Add New Pet")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Pat");
	    driver.findElement(By.id("birthDate")).click();
	    driver.findElement(By.id("birthDate")).click();	   
	    driver.findElement(By.id("birthDate")).clear();
	    driver.findElement(By.id("birthDate")).sendKeys("2012/12/20");
	    new Select(driver.findElement(By.id("type"))).selectByVisibleText("Bird");
	    driver.findElement(By.xpath("//option[@value='Bird']")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//a[@id='ownerbar']/strong")).click();
	    driver.findElement(By.xpath("//a[@id='myapplist']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/strong")).click();
	    driver.findElement(By.xpath("//a[contains(text(),'Active\n									tournaments')]")).click();
	    driver.findElement(By.xpath("//a[contains(text(),'Lovebirds speed contest  2020')]")).click();
	    driver.findElement(By.linkText("Apply for this tournament")).click();
	    driver.findElement(By.id("creditCard")).click();
	    driver.findElement(By.id("creditCard")).clear();
	    driver.findElement(By.id("creditCard")).sendKeys("363017956100486");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
		
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}


}
