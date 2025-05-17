package selenium_basics;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WindowHandle {
	  
	static EdgeDriver driver = new EdgeDriver();

	public static void isNewWindowOpened() {
		// TODO Auto-generated method stub
		driver.get("https://www.leafground.com/dashboard.xhtml");
		//click browser icon
		driver.findElement(By.xpath("//span[contains(text(), 'Browser')]/..")).click();
		//click window icon
		driver.findElement(By.xpath("//span[starts-with(text(), 'Win')]/..")).click();
		List<WebElement> list = driver.findElements(By.xpath("//button[@role='button']"));
		list.get(0).click();
		Set<String> windowHandles = driver.getWindowHandles();
		int i = 1;
		for(String windowHandle: windowHandles) {
			if(i == 2) {
				driver.switchTo().window(windowHandle);
				break;
			}
			i++;
		}
		if(!driver.getTitle().equals("Window"))
			System.out.println("New window is opened");
		else
			System.out.println("No new Window");
		
		System.out.println(driver.getTitle());
		driver.close();

	}
	
	public static void numberOfOpenedTabs() {
		driver.get("https://www.leafground.com/dashboard.xhtml");
		//click browser icon
		driver.findElement(By.xpath("//span[contains(text(), 'Browser')]/..")).click();
		//click window icon
		driver.findElement(By.xpath("//span[starts-with(text(), 'Win')]/..")).click();
		//click open multiple
		driver.findElement(By.xpath("//span[text()='Open Multiple']/..")).click();
		System.out.println("Number of opened tabs "+driver.getWindowHandles().size());
	}
	
	public static void closeAllWindowsExceptPrimary() {
		driver.get("https://www.leafground.com/dashboard.xhtml");
		//click browser icon
		driver.findElement(By.xpath("//span[contains(text(), 'Browser')]/..")).click();
		//click window icon
		driver.findElement(By.xpath("//span[starts-with(text(), 'Win')]/..")).click();
		//click close all windows
		driver.findElement(By.xpath("//span[contains(text(),'Close')]")).click();
		String primaryWindow = driver.getWindowHandle();
		Set<String> listOfWindows = driver.getWindowHandles();
		for(String window: listOfWindows) {
			if(!window.equals(primaryWindow)) {
				driver.switchTo().window(window);
				driver.close();
			}
		}
		
	}
	
	public static void openWithDelay() {
		driver.get("https://www.leafground.com/dashboard.xhtml");
		//click browser icon
		driver.findElement(By.xpath("//span[contains(text(), 'Browser')]/..")).click();
		//click window icon
		driver.findElement(By.xpath("//span[starts-with(text(), 'Win')]/..")).click();
		//click open with delay
		driver.findElement(By.xpath("//span[contains(text(),'delay')]/..")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.numberOfWindowsToBe(3));
		if(driver.getWindowHandles().size() == 3)
			System.out.println("We waited for the windows to open");
	}
	
	public static void main(String[] args) {
		//numberOfOpenedTabs();
		//closeAllWindowsExceptPrimary();
		openWithDelay();
	}

}
