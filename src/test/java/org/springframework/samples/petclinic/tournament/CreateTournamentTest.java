// Generated by Selenium IDE

package org.springframework.samples.petclinic.tournament;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreateTournamentTest {

	private WebDriver driver;

	JavascriptExecutor js;

	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "./src/test/java/resources/chromedriver/chromedriver.exe");

		ChromeOptions chromeOptions = new ChromeOptions();
		driver = new ChromeDriver(chromeOptions);
		js = (JavascriptExecutor) driver;

	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void createTournament() {
		driver.get("http://localhost:8080/");
		//driver.manage().window().setSize(new Dimension(826, 494));
		driver.findElement(By.id("login")).click();
		//driver.findElement(By.id("username")).click();
	    //driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin1");
		driver.findElement(By.id("password")).click();
		//driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("4dm1n");
		driver.findElement(By.cssSelector(".btn")).click();
		driver.findElement(By.cssSelector(".dropdown:nth-child(5) > .dropdown-toggle")).click();
		driver.findElement(By.cssSelector(".open li:nth-child(1) > a")).click();
		driver.findElement(By.linkText("Add tournament")).click();
		driver.findElement(By.id("name")).click();
		driver.findElement(By.id("name")).sendKeys("Test5");
		driver.findElement(By.id("location")).click();
		driver.findElement(By.id("location")).sendKeys("Sevilla");
		driver.findElement(By.id("category")).click();
		{
			WebElement dropdown = driver.findElement(By.id("category"));
			dropdown.findElement(By.xpath("//option[. = 'Agility']")).click();
		}
		driver.findElement(By.cssSelector("#category > option:nth-child(1)")).click();
		{
			WebElement element = driver.findElement(By.cssSelector("#petType > option:nth-child(1)"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).clickAndHold().perform();
		}
		{
			WebElement element = driver.findElement(By.cssSelector("#petType > option:nth-child(3)"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).release().perform();
		}
		{
			WebElement dropdown = driver.findElement(By.id("petType"));
			dropdown.findElement(By.xpath("//option[. = 'Bird']")).click();
		}
		driver.findElement(By.id("petType")).click();
		{
			WebElement dropdown = driver.findElement(By.id("field"));
			dropdown.findElement(By.xpath("//option[. = 'Map 1']")).click();
		}
		driver.findElement(By.cssSelector("#field > option:nth-child(1)")).click();
		{
			WebElement dropdown = driver.findElement(By.id("judge"));
			dropdown.findElement(By.xpath("//option[. = 'Dacon']")).click();
		}
		driver.findElement(By.cssSelector("#judge > option:nth-child(1)")).click();
		driver.findElement(By.id("applyDate")).click();
		driver.findElement(By.id("applyDate")).sendKeys("2020/09/09");
		driver.findElement(By.id("startDate")).click();
		driver.findElement(By.id("startDate")).sendKeys("2020/09/10");
		driver.findElement(By.id("endDate")).click();
		driver.findElement(By.id("endDate")).sendKeys("2020/09/11");
		driver.findElement(By.id("prize.amount")).click();
		driver.findElement(By.id("prize.amount")).sendKeys("500.00");
		driver.findElement(By.id("prize.currency")).click();
		driver.findElement(By.id("prize.currency")).sendKeys("EUR");
		driver.findElement(By.cssSelector(".btn-default")).click();
		Assert.assertThat(driver.findElement(By.linkText("Test5")).getText(), is("Test5"));
	}
}