package org.springframework.samples.petclinic.user_interface.guide;

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
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class GuideUITests {
	
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
	@Transactional
	public void testGuidePets() throws Exception {
		as("guide1").whenIamLoggedIntheSystem().thenISeeMyUsernameInTheMenuBar();
	}

	private void thenISeeMyUsernameInTheMenuBar() {
		assertEquals(username.toUpperCase(),
				driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
	}

	private GuideUITests whenIamLoggedIntheSystem() {		
		driver.get("http://localhost:"+port);
	    driver.findElement(By.xpath("//a[@id='guidebar']/strong")).click();
	    driver.findElement(By.xpath("//a[@id='profile']")).click();
	    driver.findElement(By.linkText("My pets")).click();
	    driver.findElement(By.xpath("//a[contains(@href, '/guides/1/pets/1')]")).click();
	    new Select(driver.findElement(By.id("pet"))).selectByVisibleText("Lucky");
	    driver.findElement(By.xpath("//option[@value='Lucky']")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	


	private GuideUITests  as(String username)throws Exception {
		this.username = username;
		driver.get("http://localhost:"+port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(passwordOf(username));
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();	    
	    return this;		
	}
	
	private CharSequence passwordOf(String username) {
		return "guid3";
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
