package selenium_basics;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.openqa.selenium.support.locators.RelativeLocator;

public class SeleniumRelativeLocators {
	
	@Test
	public void performSeleniumRelativeLocators() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		
		//above
		WebElement selectCountries = driver.findElement(By.id("autocomplete"));
		System.out.println(driver.findElement(RelativeLocator.with(By.tagName("legend")).above(selectCountries)).getText());
		
		//below
		WebElement switchToAlert = driver.findElement(By.xpath("//legend[contains(text(),'Switch To')]"));
		driver.findElement(RelativeLocator.with(By.tagName("input")).below(switchToAlert)).sendKeys("Mohan");
		
		//toRightOf
		WebElement alertButton = driver.findElement(By.id("alertbtn"));
		System.out.println(driver.findElement(RelativeLocator.with(By.tagName("input")).toRightOf(alertButton))
				.getDomAttribute("value"));
		
		//nearByWebelement
		WebElement alertButton1 = driver.findElement(By.id("alertbtn"));
		System.out.println(driver.findElement(RelativeLocator.with(By.tagName("input")).near(alertButton1))
				.getDomAttribute("value"));
		
		//toLeftOf
		WebElement confirmButton = driver.findElement(By.id("confirmbtn"));
		System.out.println(driver.findElement(RelativeLocator.with(By.tagName("input")).toLeftOf(confirmButton))
				.getDomAttribute("value"));
		
		driver.close();
		
	}

}
