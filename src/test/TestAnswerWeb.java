package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAnswerWeb {
	
	@Test
	//Luca Falasca's selenium test
	public void testgui() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.navigate().to("http://localhost:8080/HuntsTestWebConversion/MainMenu.jsp");
		
		driver.findElement(By.name("play")).click();
		
		String a = driver.findElement(By.xpath("/html/body/div/div/h2")).getText();
		
		assertEquals("Riddles", a);
	}
}
