package org.springframework.samples.petclinic.tournament;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TournamentUITests {

	private WebDriver driver;

	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./src/test/java/resources/chromedriver/chromedriver.exe");

		ChromeOptions chromeOptions = new ChromeOptions();
		driver = new ChromeDriver(chromeOptions);
	}

	@Test
	public void testGooglePage() {

		driver.manage().window().maximize();
		driver.get("http://localhost:8080");
		// 7WebElement myDynamicElement = (new WebDriverWait(driver, 10))
		// .until(ExpectedConditions.presenceOfElementLocated(By.id("myDynamicElement")));
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

		driver.quit();

	}

}
