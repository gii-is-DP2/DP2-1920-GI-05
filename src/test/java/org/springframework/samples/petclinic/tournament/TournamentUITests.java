/* package org.springframework.samples.petclinic.tournament;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
		driver.get("https://www.google.com/");

		WebElement searchbox = driver.findElement(By.name("q"));

		searchbox.clear();

		searchbox.sendKeys("Coronavirus");

		searchbox.submit();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		assertEquals("Coronavirus - Buscar con Google", driver.getTitle());

		driver.quit();

	}

}
 */