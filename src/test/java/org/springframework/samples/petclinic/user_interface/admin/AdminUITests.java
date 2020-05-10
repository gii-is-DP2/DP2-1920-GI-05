package org.springframework.samples.petclinic.user_interface.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

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
@Transactional
public class AdminUITests {
	
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
	
	// Creating a tournament
	@Test
	public void testCreateTournament() throws Exception {
		as("admin1").testProbandoCreateTournament().thenISeeMyUsernameInTheMenuBar();
	}
	
	//Creating a Judge User
	@Test
	@Transactional
	public void testCreateJudge() throws Exception {		
		as("admin1").testProbandoCreateJudge().thenISeeMyUsernameInTheMenuBar();;
	}
	
	//Creating a category
	@Test
	public void testCreateCategory() throws Exception {		
		as("admin1").testProbandoCreateCategory().thenISeeMyUsernameInTheMenuBar();;
	}
	
	//Creating a field
	@Test
	public void testCreateField() throws Exception {		
		as("admin1").testProbandoCreateField().thenISeeMyUsernameInTheMenuBar();;
	}
	

	private AdminUITests as(String username) {		
		this.username = username;
		driver.get("http://localhost:"+port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
	    driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(passwordOf(username));
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}


	public AdminUITests testProbandoCreateTournament() throws Exception {				   
	    driver.findElement(By.xpath("//a[@id='adminbar']/strong")).click();
	    driver.findElement(By.id("allTournaments")).click();
	    driver.findElement(By.linkText("Add tournament")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Cats tournament 2020");
	    driver.findElement(By.id("location")).click();
	    driver.findElement(By.id("location")).clear();
	    driver.findElement(By.id("location")).sendKeys("Seville");
	    new Select(driver.findElement(By.id("category"))).selectByVisibleText("Disc");
	    driver.findElement(By.xpath("//option[@value='Disc']")).click();
	    new Select(driver.findElement(By.id("petType"))).selectByVisibleText("Cat");
	    driver.findElement(By.xpath("//option[@value='Cat']")).click();
	    driver.findElement(By.id("applyDate")).click();
	    driver.findElement(By.id("applyDate")).clear();
	    driver.findElement(By.id("applyDate")).sendKeys("2020/12/10");
	    driver.findElement(By.id("startDate")).click();
	    driver.findElement(By.id("startDate")).clear();
	    driver.findElement(By.id("startDate")).sendKeys("2020/12/14");
	    driver.findElement(By.id("endDate")).click();
	    driver.findElement(By.id("endDate")).clear();
	    driver.findElement(By.id("endDate")).sendKeys("2020/12/18");
	    driver.findElement(By.id("prize.amount")).click();
	    driver.findElement(By.id("prize.amount")).clear();
	    driver.findElement(By.id("prize.amount")).sendKeys("100.00");
	    driver.findElement(By.id("prize.currency")).click();
	    driver.findElement(By.id("prize.currency")).clear();
	    driver.findElement(By.id("prize.currency")).sendKeys("EUR");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Cats tournament 2020")).click();
	    new Select(driver.findElement(By.id("category"))).selectByVisibleText("Obstacles");
	    driver.findElement(By.xpath("//option[@value='Obstacles']")).click();
	    new Select(driver.findElement(By.id("judge"))).selectByVisibleText("Dredd");
	    driver.findElement(By.xpath("//option[@value='Dredd']")).click();
	    new Select(driver.findElement(By.id("field"))).selectByVisibleText("Map 10");
	    driver.findElement(By.xpath("//option[@value='Map 10']")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    return this;
		
	}
	
	private AdminUITests testProbandoCreateCategory() {
	    driver.findElement(By.id("adminbar")).click();
	    driver.findElement(By.id("allCategories")).click();
	    driver.findElement(By.linkText("Add category")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Dogde");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
		return this;
	}
	
	public AdminUITests testProbandoCreateJudge() throws Exception {			
		
	    driver.findElement(By.id("adminbar")).click();
	    driver.findElement(By.id("newJudge")).click();
	    driver.findElement(By.id("firstName")).click();
	    driver.findElement(By.id("firstName")).clear();
	    driver.findElement(By.id("firstName")).sendKeys("Michael");
	    driver.findElement(By.id("lastName")).click();
	    driver.findElement(By.id("lastName")).clear();
	    driver.findElement(By.id("lastName")).sendKeys("Finn");
	    driver.findElement(By.id("address")).click();
	    driver.findElement(By.id("address")).clear();
	    driver.findElement(By.id("address")).sendKeys("Santa Street");
	    driver.findElement(By.id("city")).click();
	    driver.findElement(By.id("city")).click();
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys("Boston");
	    driver.findElement(By.id("telephone")).click();
	    driver.findElement(By.id("telephone")).clear();
	    driver.findElement(By.id("telephone")).sendKeys("978124786");
	    driver.findElement(By.id("user.username")).click();
	    driver.findElement(By.id("user.username")).clear();
	    driver.findElement(By.id("user.username")).sendKeys("michael");
	    driver.findElement(By.id("user.password")).click();
	    driver.findElement(By.id("user.password")).clear();
	    driver.findElement(By.id("user.password")).sendKeys("justice");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
	    driver.findElement(By.id("logout")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.id("login")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("michael");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("justice");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
		username = "michael";
		return this;
	}
	
	public AdminUITests testProbandoCreateField() throws Exception {			  
	    driver.findElement(By.id("adminbar")).click();
	    driver.findElement(By.id("allFields")).click();
	    driver.findElement(By.linkText("Add Field")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys("Dogde map");
	    driver.findElement(By.id("photoURL")).click();
	    driver.findElement(By.id("photoURL")).clear();
	    driver.findElement(By.id("photoURL")).sendKeys("http://localhost:8080/");
	    driver.findElement(By.id("lenght")).click();
	    driver.findElement(By.id("lenght")).clear();
	    driver.findElement(By.id("lenght")).sendKeys("23.00");
	    driver.findElement(By.id("width")).click();
	    driver.findElement(By.id("width")).clear();
	    driver.findElement(By.id("width")).sendKeys("24.00");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    return this;
		
	}
	
	@AfterEach
	public void tearDown() throws Exception {

		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	private CharSequence passwordOf(String username) {
		return "4dm1n";
	}
	
	private void thenISeeMyUsernameInTheMenuBar() {		
		assertEquals(username.toUpperCase(),
				driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());

	}

}
 