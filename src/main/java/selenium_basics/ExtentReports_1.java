package selenium_basics;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;



public class ExtentReports_1 {

	ExtentReports report;
	
	@BeforeTest
	public void createExtentReport() {
		//Path where the report has to be generated
		String path = System.getProperty("user.dir")+"\\reports\\report.html";
		
		//To generate the report in the mentioned path and also to configure the name of the report and the title of the report
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Test Automation Results");
		reporter.config().setDocumentTitle("Test Report");
		
		//Once all the details are pre-configured like above, then we have to attach it to the extent report.
		report = new ExtentReports();
		report.attachReporter(reporter);
		report.setSystemInfo("Tester", "Mohan");
	}
	
	@Test
	public void firstTestCase() {
		report.createTest("First Test Case");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.cricbuzz.com");
		driver.close();
		report.flush();
	}
}
