package selenium_basics;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Headers;



public class BasicAuthentication {

	public static void main(String[] args) throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.leafground.com/dashboard.xhtml");
		//Click the home button
		driver.findElement(By.xpath("//span[text()='Home']/parent::a")).click();
		//Click the Auth
		driver.findElement(By.xpath("//span[text()='Auth']/parent::a")).click();
		//Click Basic Auth
		driver.findElement(By.xpath("//button[@role='button']")).click();
		Set<String> windowHandles = driver.getWindowHandles();
		int i = 1;
		for(String windowHandle: windowHandles) {
			if(i == 2) {
				driver.switchTo().window(windowHandle);
				driver.get("http://admin:testleaf@leafground.com:8090/login");
				System.out.println(driver.findElement(By.xpath("//body")).getText());
				break;
			}
			i++;
		}
		//pass the url
		driver.quit();
	}

}
