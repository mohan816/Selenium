package selenium_basics;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class CheckBoxes {

	public static void main(String[] args) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		WebElement checkBox1 = driver.findElement(By.id("checkBoxOption1"));
		if(!checkBox1.isSelected())
			checkBox1.click();
		if(checkBox1.isSelected())
			checkBox1.click();
		Assert.assertEquals(checkBox1.isSelected(), false);
		//total checkbox cout
		int totalCheckBoxes = driver.findElements(By.cssSelector("div[id='checkbox-example'] input")).size();
		System.out.println(totalCheckBoxes);

	}

}
