package org.springframework.samples.petclinic.admin;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class AdminUITests {
	
	private WebDriver driver;


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./src/test/java/resources/chromedriver/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		driver = new ChromeDriver(chromeOptions);

	}

	@Test
	public void testProbandoCosasDelAdmin() throws Exception {
		
	    // Test name: Prueba admin
	    // Step # | name | target | value
	    // 1 | open | / | 
	    driver.get("http://localhost:8080/");
	    // 2 | setWindowSize | 945x1020 | 
	    driver.manage().window().setSize(new Dimension(945, 1020));
	    // 3 | click | id=login | 
	    driver.findElement(By.id("login")).click();
	    // 4 | type | id=username | admin1
	    driver.findElement(By.id("username")).sendKeys("admin1");
	    // 5 | click | id=password | 
	    driver.findElement(By.id("password")).click();
	    // 6 | type | id=password | 4dm1n
	    driver.findElement(By.id("password")).sendKeys("4dm1n");
	    // 7 | sendKeys | id=password | ${KEY_ENTER}
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    // 8 | click | css=#adminbar > strong | 
	    driver.findElement(By.cssSelector("#adminbar > strong")).click();
	    // 9 | click | id=allTournaments | 
	    driver.findElement(By.id("allTournaments")).click();
	    // 10 | click | linkText=Add tournament | 
	    driver.findElement(By.linkText("Add tournament")).click();
	    // 11 | click | id=name | 
	    driver.findElement(By.id("name")).click();
	    // 12 | type | id=name | Torneo de prueba
	    driver.findElement(By.id("name")).sendKeys("Torneo de prueba");
	    // 13 | click | id=location | 
	    driver.findElement(By.id("location")).click();
	    // 14 | click | id=location | 
	    driver.findElement(By.id("location")).click();
	    // 15 | doubleClick | id=location | 
	    {
	      WebElement element = driver.findElement(By.id("location"));
	      Actions builder = new Actions(driver);
	      builder.doubleClick(element).perform();
	    }
	    // 16 | type | id=location | Sevilla
	    driver.findElement(By.id("location")).sendKeys("Sevilla");
	    // 17 | select | id=category | label=Agility
	    {
	      WebElement dropdown = driver.findElement(By.id("category"));
	      dropdown.findElement(By.xpath("//option[. = 'Agility']")).click();
	    }
	    // 18 | click | css=#category > option:nth-child(1) | 
	    driver.findElement(By.cssSelector("#category > option:nth-child(1)")).click();
	    // 19 | select | id=petType | label=Bird
	    {
	      WebElement dropdown = driver.findElement(By.id("petType"));
	      dropdown.findElement(By.xpath("//option[. = 'Bird']")).click();
	    }
	    // 20 | click | css=#petType > option:nth-child(1) | 
	    driver.findElement(By.cssSelector("#petType > option:nth-child(1)")).click();
	    // 21 | select | id=field | label=Map 1
	    {
	      WebElement dropdown = driver.findElement(By.id("field"));
	      dropdown.findElement(By.xpath("//option[. = 'Map 1']")).click();
	    }
	    // 22 | click | css=#field > option:nth-child(1) | 
	    driver.findElement(By.cssSelector("#field > option:nth-child(1)")).click();
	    // 23 | select | id=judge | label=Dacon
	    {
	      WebElement dropdown = driver.findElement(By.id("judge"));
	      dropdown.findElement(By.xpath("//option[. = 'Dacon']")).click();
	    }
	    // 24 | click | css=#judge > option:nth-child(1) | 
	    driver.findElement(By.cssSelector("#judge > option:nth-child(1)")).click();
	    // 25 | click | css=#judge > option:nth-child(1) | 
	    driver.findElement(By.cssSelector("#judge > option:nth-child(1)")).click();
	    // 26 | doubleClick | css=#judge > option:nth-child(1) | 
	    {
	      WebElement element = driver.findElement(By.cssSelector("#judge > option:nth-child(1)"));
	      Actions builder = new Actions(driver);
	      builder.doubleClick(element).perform();
	    }
	    // 27 | click | id=applyDate | 
	    driver.findElement(By.id("applyDate")).click();
	    // 28 | type | id=applyDate | 2020/12/10
	    driver.findElement(By.id("applyDate")).sendKeys("2020/12/10");
	    // 29 | click | id=startDate | 
	    driver.findElement(By.id("startDate")).click();
	    // 30 | type | id=startDate | 2020/12/19
	    driver.findElement(By.id("startDate")).sendKeys("2020/12/19");
	    // 31 | click | id=endDate | 
	    driver.findElement(By.id("endDate")).click();
	    // 32 | type | id=endDate | 2020/12/21
	    driver.findElement(By.id("endDate")).sendKeys("2020/12/21");
	    // 33 | click | id=prize.amount | 
	    driver.findElement(By.id("prize.amount")).click();
	    // 34 | type | id=prize.amount | 10.00
	    driver.findElement(By.id("prize.amount")).sendKeys("10.00");
	    // 35 | click | id=prize.currency | 
	    driver.findElement(By.id("prize.currency")).click();
	    // 36 | type | id=prize.currency | EUR
	    driver.findElement(By.id("prize.currency")).sendKeys("EUR");
	    // 37 | click | css=.btn-default | 
	    driver.findElement(By.cssSelector(".btn-default")).click();
	    // 38 | click | css=tr:nth-child(11) > td:nth-child(1) | 
	    driver.findElement(By.cssSelector("tr:nth-child(11) > td:nth-child(1)")).click();
	    // 39 | click | linkText=Torneo de prueba | 
	    driver.findElement(By.linkText("Torneo de prueba")).click();
	    // 40 | click | id=name | 
	    driver.findElement(By.id("name")).click();
	    // 41 | type | id=name | Torneo de prueba 2
	    driver.findElement(By.id("name")).sendKeys("Torneo de prueba 2");
	    // 42 | click | css=.btn-default | 
	    driver.findElement(By.cssSelector(".btn-default")).click();
	    // 43 | click | id=adminbar | 
	    driver.findElement(By.id("adminbar")).click();
	    // 44 | click | id=allCategories | 
	    driver.findElement(By.id("allCategories")).click();
	    // 45 | click | linkText=Add category | 
	    driver.findElement(By.linkText("Add category")).click();
	    // 46 | click | id=name | 
	    driver.findElement(By.id("name")).click();
	    // 47 | type | id=name | Testing
	    driver.findElement(By.id("name")).sendKeys("Testing");
	    // 48 | click | css=.btn-default | 
	    driver.findElement(By.cssSelector(".btn-default")).click();
	    // 49 | click | css=#adminbar > strong | 
	    driver.findElement(By.cssSelector("#adminbar > strong")).click();
	    // 50 | click | id=allFields | 
	    driver.findElement(By.id("allFields")).click();
	    // 51 | click | linkText=Add Field | 
	    driver.findElement(By.linkText("Add Field")).click();
	    // 52 | click | id=name | 
	    driver.findElement(By.id("name")).click();
	    // 53 | type | id=name | Test map
	    driver.findElement(By.id("name")).sendKeys("Test map");
	    // 54 | click | id=photoURL | 
	    driver.findElement(By.id("photoURL")).click();
	    // 55 | type | id=photoURL | http://localhost:8080/
	    driver.findElement(By.id("photoURL")).sendKeys("http://localhost:8080/");
	    // 56 | mouseDownAt | id=photoURL | 197.171875,19
	    {
	      WebElement element = driver.findElement(By.id("photoURL"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element).clickAndHold().perform();
	    }
	    // 57 | mouseMoveAt | id=photoURL | 197.171875,19
	    {
	      WebElement element = driver.findElement(By.id("photoURL"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element).perform();
	    }
	    // 58 | mouseUpAt | id=photoURL | 197.171875,19
	    {
	      WebElement element = driver.findElement(By.id("photoURL"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element).release().perform();
	    }
	    // 59 | click | id=photoURL | 
	    driver.findElement(By.id("photoURL")).click();
	    // 60 | click | css=.form-group:nth-child(3) | 
	    driver.findElement(By.cssSelector(".form-group:nth-child(3)")).click();
	    // 61 | click | id=lenght | 
	    driver.findElement(By.id("lenght")).click();
	    // 62 | type | id=lenght | 10.00
	    driver.findElement(By.id("lenght")).sendKeys("10.00");
	    // 63 | click | css=.form-group:nth-child(4) | 
	    driver.findElement(By.cssSelector(".form-group:nth-child(4)")).click();
	    // 64 | click | id=breadth | 
	    driver.findElement(By.id("breadth")).click();
	    // 65 | type | id=breadth | 10.00
	    driver.findElement(By.id("breadth")).sendKeys("10.00");
	    // 66 | click | css=.btn-default | 
	    driver.findElement(By.cssSelector(".btn-default")).click();
	    // 67 | click | id=adminbar | 
	    driver.findElement(By.id("adminbar")).click();
	    // 68 | doubleClick | id=adminbar | 
	    {
	      WebElement element = driver.findElement(By.id("adminbar"));
	      Actions builder = new Actions(driver);
	      builder.doubleClick(element).perform();
	    }
	    // 69 | click | css=#adminbar > .glyphicon | 
	    driver.findElement(By.cssSelector("#adminbar > .glyphicon")).click();
	    // 70 | click | id=allApplications | 
	    driver.findElement(By.id("allApplications")).click();
	    // 71 | click | css=tr:nth-child(5) > td:nth-child(1) > a | 
	    driver.findElement(By.cssSelector("tr:nth-child(5) > td:nth-child(1) > a")).click();
	    // 72 | click | css=#adminbar > strong | 
	    driver.findElement(By.cssSelector("#adminbar > strong")).click();
	    // 73 | click | id=allApplications | 
	    driver.findElement(By.id("allApplications")).click();
	    // 74 | click | css=tr:nth-child(5) > td:nth-child(6) > a | 
	    driver.findElement(By.cssSelector("tr:nth-child(5) > td:nth-child(6) > a")).click();
	    // 75 | select | id=status | label=ACCEPTED
	    {
	      WebElement dropdown = driver.findElement(By.id("status"));
	      dropdown.findElement(By.xpath("//option[. = 'ACCEPTED']")).click();
	    }
	    // 76 | click | css=option:nth-child(2) | 
	    driver.findElement(By.cssSelector("option:nth-child(2)")).click();
	    // 77 | click | css=option:nth-child(2) | 
	    driver.findElement(By.cssSelector("option:nth-child(2)")).click();
	    // 78 | doubleClick | css=option:nth-child(2) | 
	    {
	      WebElement element = driver.findElement(By.cssSelector("option:nth-child(2)"));
	      Actions builder = new Actions(driver);
	      builder.doubleClick(element).perform();
	    }
	    // 79 | click | css=.btn-default | 
	    driver.findElement(By.cssSelector(".btn-default")).click();
	    // 80 | click | id=adminbar | 
	    driver.findElement(By.id("adminbar")).click();
	    // 81 | click | id=newJudge | 
	    driver.findElement(By.id("newJudge")).click();
	    // 82 | click | css=.xd-container | 
	    driver.findElement(By.cssSelector(".xd-container")).click();
	    // 83 | click | css=.xd-container | 
	    driver.findElement(By.cssSelector(".xd-container")).click();
	    // 84 | doubleClick | css=.xd-container | 
	    {
	      WebElement element = driver.findElement(By.cssSelector(".xd-container"));
	      Actions builder = new Actions(driver);
	      builder.doubleClick(element).perform();
	    }
	    // 85 | click | id=firstName | 
	    driver.findElement(By.id("firstName")).click();
	    // 86 | type | id=firstName | Shane
	    driver.findElement(By.id("firstName")).sendKeys("Shane");
	    // 87 | click | id=lastName | 
	    driver.findElement(By.id("lastName")).click();
	    // 88 | type | id=lastName | Michel
	    driver.findElement(By.id("lastName")).sendKeys("Michel");
	    // 89 | click | id=address | 
	    driver.findElement(By.id("address")).click();
	    // 90 | type | id=address | Santa Street
	    driver.findElement(By.id("address")).sendKeys("Santa Street");
	    // 91 | click | id=city | 
	    driver.findElement(By.id("city")).click();
	    // 92 | click | id=city | 
	    driver.findElement(By.id("city")).click();
	    // 93 | type | id=city | Boston
	    driver.findElement(By.id("city")).sendKeys("Boston");
	    // 94 | click | id=telephone | 
	    driver.findElement(By.id("telephone")).click();
	    // 95 | type | id=telephone | 999666111
	    driver.findElement(By.id("telephone")).sendKeys("999666111");
	    // 96 | click | id=user.username | 
	    driver.findElement(By.id("user.username")).click();
	    // 97 | type | id=user.username | shane
	    driver.findElement(By.id("user.username")).sendKeys("shane");
	    // 98 | click | id=user.password | 
	    driver.findElement(By.id("user.password")).click();
	    // 99 | type | id=user.password | michael
	    driver.findElement(By.id("user.password")).sendKeys("michael");
	    // 100 | click | css=.btn-default | 
	    driver.findElement(By.cssSelector(".btn-default")).click();
	    // 101 | click | css=.dropdown:nth-child(1) > .dropdown-toggle | 
	    driver.findElement(By.cssSelector(".dropdown:nth-child(1) > .dropdown-toggle")).click();
	    // 102 | click | id=logout | 
	    driver.findElement(By.id("logout")).click();
	    // 103 | click | css=.btn | 
	    driver.findElement(By.cssSelector(".btn")).click();	   


		
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
	}
}
