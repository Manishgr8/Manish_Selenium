package com.training.sanity.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.training.generics.GenericMethods;
import com.training.generics.ScreenShot;
import com.training.pom.ContactFormPOM;
import com.training.pom.HomePagePOM;

import com.training.pom.BlogPagePOM;
import com.training.pom.LoginPagePOM;
import com.training.pom.LostPasswordPOM;
import com.training.pom.PropertyPagePOM;
import com.training.pom.RealEstatePagePOM;
import com.training.pom.DashboardPagePOM;
import com.training.pom.FeaturePagePOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;
import com.training.dataproviders.LoginDataProviders;
import com.training.readexcel.*;

public class AssignmentComplexTest {
	private WebDriver driver;
	private String baseUrl;
	private HomePagePOM homePage;
	private ContactFormPOM contact;
	private BlogPagePOM blog;
	private static Properties properties;
	private ScreenShot screenShot;
	private LoginPagePOM lgn;
	private LostPasswordPOM lost;
	private DashboardPagePOM dash;
	private GenericMethods generic;
	private FeaturePagePOM feature;
	private PropertyPagePOM property;
	private RealEstatePagePOM realEst;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeMethod
	public void setUp() throws Exception {
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		homePage = new HomePagePOM(driver);
		contact = new ContactFormPOM(driver);
		blog = new BlogPagePOM(driver);
		lgn = new LoginPagePOM(driver);
		lost = new LostPasswordPOM(driver);
		dash = new DashboardPagePOM(driver);
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		generic = new GenericMethods(driver);
		feature = new FeaturePagePOM(driver);
		property = new PropertyPagePOM(driver);
		realEst = new RealEstatePagePOM(driver);
		// open the browser
		driver.get(baseUrl);
		Thread.sleep(5000L);

		// Asserting if correct page has been launched or not
		generic.AssertTitle("Real Estate");
	}

	// RETC_070: To verify whether application allows admin to create property
	// details based on the Feature created & added property get displayed on home
	// screen for user
	@Test
	public void Scenario1() throws InterruptedException, AWTException {
		
		System.out.println("Scenario 1 starts");

		// Login with admin credentials
		lgn.AdminLogin("admin", "admin@123");

		// Click on Properties link
		dash.ClickPropertiesLink();

		// Assert if All Properties links should are displayed
		// Getting Webelement to pass on to assertion for text verification
		WebElement element1 = generic.getElement("//a[text()='All Properties']", "xpath");
		generic.AssertText("All Properties", element1);

		// Click on Features link
		dash.ClickFeaturesLink();

		// Asserting if Features page is displayed
		WebElement element2 = generic.getElement("//h1[@class='wp-heading-inline' and text()='Features']", "xpath");
		generic.AssertText("Features", element2);

		// Enter Valid Credentials in Name textbox
		feature.EnterFeatureName("New Launches");

		// Enter Valid Credentials in Slug textbox
		feature.EnterSlugName("launch");

		// Enter Valid Credentials in Description textbox
		feature.EnterDescText("New Launches of villas, apartments, flats");

		// Click on Add New Feature button
		feature.ClickAddNewFeatureBtn();

		// Asserting if Added feature in existing feature module is displayed
		// Searching for added feature in search box
		feature.SearchFeature("New Launches");

		// Clicking on "Search Features" button to refresh the page
		feature.ClickSearchFeaturesBtn();

		// Getting the details of table returned
		List<WebElement> list1 = generic.getElementsAsList("//table/tbody/tr/td/strong/a", "xpath");

		// Last step to assert if feature added is present in table or not
		generic.AssertList(list1, "New Launches");

		Thread.sleep(5000L);

		// Click on Add new link of Properties section
		dash.ClickAddNewPropLink();

		Thread.sleep(5000L);

		// Asserting if Add Property page is displayed
		WebElement element3 = generic.getElement("//h1[@class='wp-heading-inline' and text()='Add Property']", "xpath");
		generic.AssertText("Add Property", element3);

		// Enter valid credentials in Enter Title Here textbox
		property.EnterPropName("prestige");

		// Enter valid credentials in textbox
		property.EnterBodyText("home town");

		// Click on checkbox beside added Feature of Features section
		List<WebElement> list2 = generic.getElementsAsList("//ul[@id='property_featurechecklist']/li/label", "xpath");
		List<WebElement> list3 = generic.getElementsAsList("//ul[@id='property_featurechecklist']/li/label/input",
				"xpath");

		for (int i = 0; i < list2.size(); i++) {
			String text = list2.get(i).getText();

			if (text.equalsIgnoreCase("New Launches")) {
				// Clicking on checkbox
				list3.get(i).click();
				break;
			}
		}

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-200);");
		Thread.sleep(5000L);

		// Click on Publish button
		property.ClickPublishBtn();

		Thread.sleep(5000L);

		// Asserting if "Post published. View post" message is displayed
		WebElement element4 = generic.getElement("//div[@id='message']/p", "xpath");
		generic.AssertText("Post published. View post", element4);

		// Click on Logout
		WebElement element5 = generic.getElement(
				"//li[@id='wp-admin-bar-my-account']/a/span[@class='display-name' and text()='admin']", "xpath");
		Actions act = new Actions(driver);
		act.moveToElement(element5).build().perform();
		dash.ClickLogOutLink();
		

		Thread.sleep(5000L);

		// Asserting if login page is displayed
		WebElement element6 = generic.getElement("//a[text()='Log In']", "xpath");
		generic.AssertText("Log In", element6);

		Thread.sleep(5000L);

		// Click on Real Estate icon
		lgn.ClickRealEstLink();

		// Asserting if Real Estate home screen is displayed
		generic.AssertTitle("Real Estate");

		// Search for added property
		realEst.SearchProp("prestige");

		Thread.sleep(2000L);

		// Clicking first link for auto suggested drop down list
		realEst.ClickList();

		Thread.sleep(5000L);

		// Getting window id of second opened window
		Set<String> window = driver.getWindowHandles();

		Iterator<String> itr = window.iterator();

		String windowId1 = itr.next();
		String windowId2 = itr.next();

		// Switching to second window
		driver.switchTo().window(windowId2);

		// Asserting if new page has all required details of added property or not
		WebElement element7 = generic.getElement("//div[@class='property-title']/h2", "xpath");
		generic.AssertText("prestige For Sale Apartments", element7);

		System.out.println("Scenario 1 ends");
	}

	
	 //RETC_071: To Verify whether application allows multiple user to send the query in Contact Form Page
	  
	  @Test(dataProvider="xls-inputs", dataProviderClass=LoginDataProviders.class)
	  public void Scenario2(String str1, String str2, String str3, String str4) throws InterruptedException { 
		  System.out.println("Test Scenario2 starts");
	  
	  // Clicking on "Blog" link 
	 	homePage.ClickBlogLink();
	  
	  // Asserting if correct url has been launched or not
	  generic.AssertUrl("http://realestatem1.upskills.in/blog/");
	  
	  // Clicking on "Drop us a line" button after assertion is passed.
	  blog.ClickContactLink();
	  
	  // Getting Webelement to pass on to assertion for text verification
	  WebElement element =
	  generic.getElement("//div[@role='form']/preceding-sibling::h4", "xpath");
	  
	  // Asserting if we have "Contact Form" text on the page or not
	  generic.AssertText("Contact Form", element);
	  
	  // Entering fields of contact form after above assertion is passed
	  contact.EnterName(str1); contact.EnterEmail(str2);
	  contact.EnterSubject(str3); contact.EnterMessage(str4);
	  
	  // Clicking on "Send" button 
	  contact.ClickSendLink();
	  
	  // Scrolling to get confirmation message screenshot Thread.sleep(5000L);
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("window.scrollBy(0,100);");
	  
	  // Asserting if correct confirmation message is displayed or not 
	  String expectedConfirmationMessage =
	  "Thanks You for your message. It has been sent";
	  
	  // Getting webelement to pass on to assertion for text verification
	  WebElement element2 =
	  generic.getElement("//form[@method='post']/div[@role='alert']", "xpath");
	  
	  // Asserting if we have correct confirmation message on the page or not
	  generic.AssertText(expectedConfirmationMessage, element2); 
	  
	  System.out.println("Test Scenario2 ends");
	 
}
	  
	  
	  //RETC_072: To Verify whether application highlights required fields upon
	  //entering invalid details in send the query in Contact Form Page
	  
	  @Test(dataProvider="xls-inputs", dataProviderClass=LoginDataProviders.class)
	  public void Scenario3(String str1, String str2, String str3, String str4) throws InterruptedException { 
		  System.out.println("Test Scenario3 starts");
	  
	  // Clicking on "Blog" link 
	 homePage.ClickBlogLink();
	  
	  // Asserting if correct url has been launched or not
	  generic.AssertUrl("http://realestatem1.upskills.in/blog/");
	  
	  // Clicking on "Drop us a line" button after assertion is passed.
	  blog.ClickContactLink();
	  
	  // Getting Webelement to pass on to assertion for text verification
	  WebElement element =
	  generic.getElement("//div[@role='form']/preceding-sibling::h4", "xpath");
	  
	  // Asserting if we have "Contact Form" text on the page or not
	 generic.AssertText("Contact Form", element);
	  
	  // Entering fields of contact form after above assertion is passed
	  contact.EnterName(str1); contact.EnterEmail(str2);
	  contact.EnterSubject(str3); contact.EnterMessage(str4);
	  
	  // Clicking on "Send" button 
	 contact.ClickSendLink();
	  
	  // Scrolling to get confirmation message screenshot Thread.sleep(5000L);
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("window.scrollBy(0,100);");
	  
	  // Asserting if correct confirmation message is displayed or not 
	  String expectedConfirmationMessage =
	  "There was an error trying to send your message. Please try again later.";
	  
	  // Getting webelement to pass on to assertion for text verification
	  WebElement element2 =
	  generic.getElement("//form[@method='post']/div[@role='alert']", "xpath");
	  
	  // Asserting if we have correct confirmation message on the page or not
	  generic.AssertText(expectedConfirmationMessage, element2); 
	  
	  System.out.println("Test Scenario3 ends");
	 }


	@AfterMethod
	public void TestStatus(ITestResult result) throws Exception {
		int status = result.getStatus();

		if (status == result.SUCCESS) {
			System.out.println("Test " + result.getName() + " is PASSED");
		} else if (status == result.FAILURE) {
			System.out.println("Test " + result.getName() + " is FAILED");
		} else if (status == result.SKIP) {
			System.out.println("Test " + result.getName() + " is SKIPPED");
		}

		if (status == result.FAILURE) {
			screenShot.captureScreenShot(result.getName());
		}

		Thread.sleep(1000);
		driver.quit();
	}
}
