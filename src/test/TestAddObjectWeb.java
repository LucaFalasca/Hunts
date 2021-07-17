package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAddObjectWeb {

	@Test
	//Andrea Paolo Mancuso's selenium test
	public void testgui() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.navigate().to("http://localhost:8080/HuntsTestWebConversion/MainMenu.jsp");
		
		driver.findElement(By.xpath("/html/body/nav/div/ul/li[1]/a")).click();
		
		driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/div[1]/input")).sendKeys("pippo");
		
		driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/div[2]/input")).sendKeys("pippopass");
		
		driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/div[3]/button")).click();
		
		
		driver.findElement(By.xpath("/html/body/div/div/div[2]/table/tbody/tr/td[2]/form/button/img")).click();
		
		driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("Anello");
		driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/form/div[2]/input")).sendKeys("Anello da 32 carati");
		
		driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/form/input[2]")).click();
		
		boolean addObject = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/table/tbody/tr")).isDisplayed();
		
		assertEquals(true, addObject);
	}
	
}
