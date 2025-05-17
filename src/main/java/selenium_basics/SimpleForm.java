package selenium_basics;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class SimpleForm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://rahulshettyacademy.com/angularpractice/");
		driver.findElement(By.name("name")).sendKeys("Mohanrajan");
		driver.findElement(By.name("email")).sendKeys("mohanshiva438@gmail.com");
		driver.findElement(By.id("exampleInputPassword1")).sendKeys("mohan");
		driver.findElement(By.id("exampleCheck1")).click();
		//select gender
		driver.findElement(By.id("exampleFormControlSelect1")).click();
		driver.findElement(By.xpath("//option[text()='Female']")).click();
		driver.findElement(By.id("inlineRadio2")).click();
		driver.findElement(By.name("bday")).sendKeys("25101996");
		driver.findElement(By.cssSelector("input[value='Submit']")).click();
		String output = driver.findElement(By.cssSelector(".alert.alert-success")).getText();
		Assert.assertTrue(output.contains("Success! The Form has been submitted successfully!."));
		driver.close();
	}

}
