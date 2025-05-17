package selenium_basics;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v130.fetch.Fetch;
import org.openqa.selenium.devtools.v130.network.Network;
import org.openqa.selenium.devtools.v130.network.model.ErrorReason;
import org.openqa.selenium.devtools.v130.network.model.Response;
import org.openqa.selenium.devtools.v131.network.model.LoadingFailed;
import org.openqa.selenium.devtools.v130.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;

import io.reactivex.rxjava3.internal.operators.flowable.FlowableInternalHelper.RequestMax;

public class ChromeDevTools {
	
	//Emulation domain -> To open the web apps in different dimension, we can open it using iPhone etc right in the dev tools we pass the res
	//pective dimensions then it will open in the particular dimension
	//@Test
	public void mobileEmulator() {
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	    //Create an object for dev tools
		DevTools devTools = driver.getDevTools();
		
		//Create a session
		devTools.createSession();
		
		//Use send command to send the command to the CDP
		devTools.send(Emulation.setDeviceMetricsOverride(600, 1000, 50, true, Optional.empty(), Optional.empty(), 
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
		driver.get("https://www.google.com");
	}
	
	//If we don't have pre-defined function then we have to use executeCDPCommand
	
	//@Test
	public void execute_CDP_Command() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	    //Create an object for dev tools
		DevTools devTools = driver.getDevTools();
		
		//Create a session
		devTools.createSession();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("width", 600);
		map.put("height", 1000);
		map.put("deviceScaleFactor", 50);
		map.put("mobile", true);
		driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", map);
		driver.get("https://www.google.com");
	}
	
	//Localization Testing: Testing the application in their local language
	//@Test
	public void setGeoLocation() throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	    //Create an object for dev tools
		DevTools devTools = driver.getDevTools();
		
		//Create a session
		devTools.createSession();
		//g
		devTools.send(Emulation.setGeolocationOverride(Optional.of(35), Optional.of(6), Optional.of(50)));
		driver.get("https://netflix.com");
//		//driver.findElement(By.name("q")).sendKeys("Near by hotel", Keys.ENTER);
//		Thread.sleep(5000);
//		driver.findElement(By.cssSelector(".recaptcha-checkbox-border")).click();
	}
	
	//@Test
	public void networkLogActivity() throws Exception {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
	    //Create an object for dev tools
		DevTools devTools = driver.getDevTools();
		//Create a session
		devTools.createSession();
		//It tracks the network event
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.responseReceived(), response ->
		{
			Response res = response.getResponse();
			int statusCode = res.getStatus();
			System.out.println("Status code is "+statusCode);
			try {
			if(statusCode != 200)
				throw new APIFailedException();
			}
			catch(Exception e) {
				System.out.println("Url is not working");
			}
			
		});
		driver.get("https://google.com");
	}
	
	//@Test
	/*
	 * 1) Hitting the browser which is mentioned in the below url, once after clicking Virtual Library button it will hit the different url
	 * 2) Once that url contains GetBook, then mocking that request with adding some parameters to it, so that it will navigate to
	 *    different API request.
	 */
	public void networkMocking() throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
	    //Create an object for dev tools
		DevTools devTools = driver.getDevTools();
		//Create a session
		devTools.createSession();
		//UrlPattern
		Optional<List<RequestPattern>> patterns = Optional.of(Arrays.asList(new RequestPattern(Optional.of("*GetBook*"), Optional.empty(), Optional.empty())));
		devTools.send(Fetch.enable(patterns, Optional.empty()));
		devTools.addListener(Fetch.requestPaused(), request -> {
			String url = request.getRequest().getUrl();
			if(url.contains("=shetty")) {
				String mockedUrl = url.replace("=shetty", "=BadGuy");
				//System.out.println(mockedUrl);
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(mockedUrl), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
				//System.out.println("Status is "+request.getRequest().get;
				//System.out.println(result.getParams().get("url"));
			}
			else {
				Fetch.continueRequest(request.getRequestId(), Optional.of(url), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
			System.out.println("Status is "+request.getResponseStatusCode()+" Url is "+url);
			}
		});
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("button[routerlink*='lib']")).click();
		Thread.sleep(5000);
		Assert.assertTrue(driver.findElement(By.cssSelector("p")).getText().contains("1"), "Multiple books found");
	}
	
	//@Test
	/*
	 * /*
	 * 1) Hitting the browser which is mentioned in the below url, once after clicking Virtual Library button it will hit the different url
	 * 2) Once that url contains GetBook, then failing the request there itself means it wont perform any API request after that.
	 */
	public void networkFailedRequest() throws InterruptedException {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
	    //Create an object for dev tools
		DevTools devTools = driver.getDevTools();
		//Create a session
		devTools.createSession();
		//UrlPattern
		Optional<List<RequestPattern>> patterns = Optional.of(Arrays.asList(new RequestPattern(Optional.of("*GetBook*"), Optional.empty(), Optional.empty())));
		devTools.send(Fetch.enable(patterns, Optional.empty()));
		devTools.addListener(Fetch.requestPaused(), request -> {
			devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
		});
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("button[routerlink*='lib']")).click();
	}
	/*
	 * Block the un-necessary links, In this scenario to increase the speed of the execution going to block jpg and css files to load
	 */
	//@Test
	public void networkBlockRequest() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
	    //Create an object for dev tools
		DevTools devTools = driver.getDevTools();
		//Create a session
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.setBlockedURLs(ImmutableList.of("*.jpg", "*.csv")));
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("a[routerlink*='products']")).click();
		driver.findElement(By.linkText("Selenium")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		System.out.println(driver.findElement(By.cssSelector("p")).getText());
	}
	
	/*
	 * Reducing the network speed 
	 */
	//@Test
	public void emulateNetworkSpeed() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
	    //Create an object for dev tools
		DevTools devTools = driver.getDevTools();
		//Create a session
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		//devTools.send(Network.emulateNetworkConditions(true, 3000, 0, 0, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.emulateNetworkConditions(true, 3000, 0, 0, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.loadingFailed(), loadingFailed -> 
		{
			System.out.println(loadingFailed.getErrorText());
		});
		long start = System.currentTimeMillis();
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("a[routerlink*='products']")).click();
		driver.findElement(By.linkText("Selenium")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		System.out.println(driver.findElement(By.cssSelector("p")).getText());
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
	
	@Test
	public void basicAuthentication() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
	//	driver.get("http://foo:bar@httpbin.org/basic-auth/foo/bar");
	// we can use predicate as well
	   Predicate<URI> uriPredicate =  uri -> uri.getHost().contains("httpbin.org");
	   ((HasAuthentication)driver).register(uriPredicate, UsernameAndPassword.of("foo", "bar"));
	   driver.get("http://httpbin.org/basic-auth/foo/bar");
	}
	/*
	 * Log java script errors, this logging code should be in the onTestFailure function of TestNg
	 */
	@Test
	public void logJavaScriptErrors() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("a[routerlink*='products']")).click();
		driver.findElement(By.linkText("Selenium")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		driver.findElement(By.linkText("Cart")).click();
		driver.findElement(By.id("exampleInputEmail1")).clear();
		driver.findElement(By.id("exampleInputEmail1")).sendKeys("2");
		
		//add below code in onTestFailure
		LogEntries log = driver.manage().logs().get(LogType.BROWSER);
		
		for(LogEntry a: log) {
			System.out.println(a.getMessage());
		}
		
	}

}
