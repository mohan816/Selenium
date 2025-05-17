package selenium_basics;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckBoxAndDropList_Assignment {

	@Test(enabled=false)
	public void checkBoxDropListAssignment() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		//click option 2
		WebElement optionToSelect = driver.findElement(By.cssSelector("label[for='benz'] input"));
		optionToSelect.click();
		String textOfSelectedCheckBox = driver.findElement(By.cssSelector("label[for='benz']")).getText();
		//Drop Down
		WebElement dropDown = driver.findElement(By.id("dropdown-class-example"));
		Select select = new Select(dropDown);
		select.selectByVisibleText(textOfSelectedCheckBox);
		//enter in the text box
		driver.findElement(By.id("name")).sendKeys(textOfSelectedCheckBox);
		//click on Alert button
		driver.findElement(By.id("alertbtn")).click();
		Alert alert = driver.switchTo().alert();
		String textToValidate = alert.getText();
		alert.accept();
		Assert.assertTrue(textToValidate.contains(textOfSelectedCheckBox));
		driver.close();
	}
	
	@Test
	public void autoSuggestiveDropDown() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		driver.findElement(By.id("autocomplete")).sendKeys("Ind");
		driver.findElement(By.xpath("//li[@class='ui-menu-item']//div[text()='India']")).click();
		//System.out.println(driver.findElement(By.id("autocomplete")).getDomAttribute("value"));
		String value = "return document.getElementById(\"autoComplete\").value;";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String text = (String)js.executeScript(value);
		System.out.println(text);
		
	}

}
