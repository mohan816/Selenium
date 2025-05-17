package testng;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

public class PracticeClass_2 {
	
	@Test(groups = {"Smoke"})
	public void ridingBike() {
		System.out.println("Ride the bike");
		throw new NoSuchElementException(null);
	}
	
	@Test(groups = {"Regression"})
	public void driveCar() {
		System.out.println("Drive the car");
	}

}
