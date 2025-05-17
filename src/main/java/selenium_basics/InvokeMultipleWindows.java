package selenium_basics;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/*Launch https://rahulshettyacademy.com/angularpractice/
 * Launch https://rahulshettyacademy.com/
 * get the first course, then switch to the parent window then paste in the name text box
 * */
public class InvokeMultipleWindows {
	
	@Test
	public void invokeMultipleWindows() throws IOException {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://rahulshettyacademy.com/angularpractice/");
		driver.switchTo().newWindow(WindowType.WINDOW);
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		String parentWindow = it.next();
		String childWindow = it.next();
		driver.switchTo().window(childWindow);
		driver.get("https://rahulshettyacademy.com/");
		String firstCourse = driver.findElement(By.xpath("(//section[@class='courses-section']//h2)[2]")).getText();
		driver.switchTo().window(parentWindow);
		WebElement name = driver.findElement(By.cssSelector(".form-control.ng-invalid"));
		name.sendKeys(firstCourse);
		//Take screenshot of name WebElement
		File source = name.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source,new File("./reports/name.png"));
		//get the height and width of the name text box
		Dimension rect = name.getRect().getDimension();
		System.out.println("Height is "+ rect.getHeight());
		System.out.println("Width is "+rect.getWidth());
		driver.quit();
	}

}
