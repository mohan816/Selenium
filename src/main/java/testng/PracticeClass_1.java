package testng;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PracticeClass_1 {
	
	@Test(priority = 1, enabled = false)
	public void day1() {
		System.out.println("PracticeClass_1_day1");
	//	throw new NoSuchElementException(null);
	}
	
	@Test(invocationCount = 3)
	public void day2() {
		System.out.println("PracticeClass_1_day2");
	}
	
	@Test(priority = 2)
	public void firstDay() {
		System.out.println("Practice is going to start");
	}

}
