package selenium_basics;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class WebTableAssignment {
	
	@Test
	public void webTable() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,600)");
		//number of rows
		System.out.println(driver.findElements(By.cssSelector("table[name='courses'] tr")).size());
		//number of columns
		System.out.println(driver.findElements(By.cssSelector("table[name='courses'] tr:nth-child(1) th")).size());
		//print second row details
		List<WebElement> tableContents = driver.findElements(By.cssSelector("table[name='courses'] tr"));
		for(int i = 0; i < tableContents.size(); i++) {
			if(i == 2) {
				List<WebElement> columnContent = tableContents.get(i).findElements(By.tagName("td"));
				for(int j = 0; j < columnContent.size(); j++)
					 System.out.println(columnContent.get(j).getText());
				break;
			}
		}
		driver.close();
	}

}
