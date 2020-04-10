package org.springframework.samples.petclinic.interfaceTest;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreateTournament {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = "C:\\Users\\joaki\\Downloads\\chromedriver_win322";
		System.setProperty("webdriver.chrome.driver", pathToGeckoDriver + "\\chromedriver.exe");
		ChromeOptions ChromeOptions = new ChromeOptions();
		ChromeOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
		WebDriver driver = new ChromeDriver(ChromeOptions);
		driver = new ChromeDriver();
		baseUrl = "http://localhost:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUntitledTestCase() throws Exception {
		driver.get("http://localhost:8080");
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("myDynamicElement")));
		driver.findElement(By.linkText("Sign in")).click();
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span")).click();
		driver.findElement(By.linkText("All tournaments")).click();
		driver.findElement(By.linkText("Add tournament")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Joaquin Gonzalez");
		driver.findElement(By.id("location")).clear();
		driver.findElement(By.id("location")).sendKeys("Sevilla");
		new Select(driver.findElement(By.id("category"))).selectByVisibleText("Dog Puller");
		driver.findElement(By.xpath("//option[@value='Dog Puller']")).click();
		new Select(driver.findElement(By.id("petType"))).selectByVisibleText("dog");
		driver.findElement(By.xpath("//option[@value='dog']")).click();
		new Select(driver.findElement(By.id("field"))).selectByVisibleText("Map 1");
		driver.findElement(By.xpath("//option[@value='Map 1']")).click();
		new Select(driver.findElement(By.id("judge"))).selectByVisibleText("Dredd");
		driver.findElement(By.xpath("//option[@value='Dredd']")).click();
		driver.findElement(By.xpath("//form[@id='add-field-form']/div")).click();
		driver.findElement(By.id("applyDate")).click();
		driver.findElement(By.id("applyDate")).click();
		driver.findElement(By.id("applyDate")).clear();
		driver.findElement(By.id("applyDate")).sendKeys("2020/05/01");
		driver.findElement(By.id("startDate")).click();
		driver.findElement(By.id("startDate")).click();
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("2020/05/02");
		driver.findElement(By.id("endDate")).click();
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("2020/05/03");
		driver.findElement(By.id("prize.amount")).click();
		driver.findElement(By.id("prize.amount")).clear();
		driver.findElement(By.id("prize.amount")).sendKeys("500.00");
		driver.findElement(By.id("prize.currency")).click();
		driver.findElement(By.id("prize.currency")).clear();
		driver.findElement(By.id("prize.currency")).sendKeys("EUR");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("Add tournament")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.id("name")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Test");
		driver.findElement(By.id("location")).click();
		driver.findElement(By.id("location")).clear();
		driver.findElement(By.id("location")).sendKeys("test");
		new Select(driver.findElement(By.id("category"))).selectByVisibleText("Dog Puller");
		driver.findElement(By.xpath("//option[@value='Dog Puller']")).click();
		new Select(driver.findElement(By.id("petType"))).selectByVisibleText("bird");
		driver.findElement(By.xpath("//option[@value='bird']")).click();
		new Select(driver.findElement(By.id("field"))).selectByVisibleText("Map 1");
		driver.findElement(By.xpath("//option[@value='Map 1']")).click();
		new Select(driver.findElement(By.id("judge"))).selectByVisibleText("Dacon");
		driver.findElement(By.xpath("//option[@value='Dacon']")).click();
		driver.findElement(By.id("applyDate")).click();
		driver.findElement(By.id("applyDate")).clear();
		driver.findElement(By.id("applyDate")).sendKeys("2020/05/01");
		driver.findElement(By.id("location")).click();
		driver.findElement(By.id("location")).clear();
		driver.findElement(By.id("location")).sendKeys("test2");
		driver.findElement(By.id("startDate")).click();
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("2020/09/10");
		driver.findElement(By.id("endDate")).click();
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("2020/09/11");
		driver.findElement(By.id("prize.amount")).click();
		driver.findElement(By.id("prize.amount")).clear();
		driver.findElement(By.id("prize.amount")).sendKeys("500.00");
		driver.findElement(By.id("prize.currency")).click();
		driver.findElement(By.id("prize.currency")).clear();
		driver.findElement(By.id("prize.currency")).sendKeys("EUR");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//body/div/div")).click();
		driver.findElement(By.id("name")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Test2");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Test2",
				driver.findElement(By.xpath("//table[@id='tournamentesTable']/tbody/tr[7]/td")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
