package selenium_basics;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class PlayAroundStreams {

	/*
	 * 1) Get the unique numbers and sort it
	 */
	//@Test
	public void sortAnArray() {
		List<Integer> list = Arrays.asList(3,2,2,7,5,1,9,7);
		list.stream().distinct().sorted().forEach(s -> System.out.println(s));
	}
	
	@Test
	public void listOfIphones() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://Amazon.in");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Apple phone",Keys.ENTER);
		List<WebElement> listOfItems = driver.findElements(By.cssSelector(".s-main-slot div[data-cy='title-recipe']"));
		listOfItems.stream().filter(s -> s.getText().contains("Apple") || s.getText().contains("iPhone"))
		.forEach(s -> System.out.println(s.getText()));
		driver.close();
	}
}
