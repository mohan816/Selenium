package testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PracticeClass_3 {
	
	@Test(groups = {"Smoke"})
	public void day1() {
		System.out.println("PracticeClass_3_day1");
	}
	
	@Test(groups = {"Regression"})
	public void day2() {
		System.out.println("PracticeClass_3_day2");
	}
	
	@BeforeTest
	public void firstDay() {
		System.out.println("Practice is going to start");
	}

}
