package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import com.training.pom.DashboardPagePOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class AssignmentSimpleTest {
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
		// open the browser
		driver.get(baseUrl);
		Thread.sleep(5000L);
		
		// Asserting if correct page has been launched or not
		generic.AssertTitle("Real Estate");
	}

	// To Verify whether application allows user to send the query in Contact Form
	// Page
	@Test
	public void Scenario1() throws InterruptedException {
		System.out.println("Test Scenario1 starts");
	
		// Clicking on "Blog" link
		homePage.ClickBlogLink();

		// Asserting if correct url has been launched or not
		generic.AssertUrl("http://realestatem1.upskills.in/blog/");

		// Clicking on "Drop us a line" button after assertion is passed.
		blog.ClickContactLink();

		// Getting Webelement to pass on to assertion for text verification
		WebElement element = generic.getElement("//div[@role='form']/preceding-sibling::h4", "xpath");

		// Asserting if we have "Contact Form" text on the page or not
		generic.AssertText("Contact Form", element);

		// Entering fields of contact form after above assertion is passed
		contact.EnterName("reva");
		contact.EnterEmail("revasharma@gmail.com");
		contact.EnterSubject("apartments");
		contact.EnterMessage("looking for an appartment");

		// Clicking on "Send" button
		contact.ClickSendLink();

		// Scrolling to get confirmation message screenshot
		Thread.sleep(5000L);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,100);");

		// Asserting if correct confirmation message is displayed or not
		String expectedConfirmationMessage = "Thanks You for your message. It has been sent";

		// Getting webelement to pass on to assertion for text verification
		WebElement element2 = generic.getElement("//form[@method='post']/div[@role='alert']", "xpath");

		// Asserting if we have correct confirmation message on the page or not
		generic.AssertText(expectedConfirmationMessage, element2);
	}

	// To Verify whether application allows registered admin to login into
	// application
	@Test
	public void Scenario2() {
		System.out.println("Test Scenario2 starts");
		
		// Clicking on "LOG IN/REGISTER" button
		homePage.ClickSigninLink();

		// Asserting if "My Profile" page is launched or not
		// Getting webelement to pass on to assertion for text verification
		WebElement element = generic.getElement("//nav[@id='breadcrumbs']/preceding-sibling::h2", "xpath");

		// Asserting if we have "My Profile" text present on the page or not
		generic.AssertText("My Profile", element);

		// Entering username and password
		lgn.sendUserName("revasharma@gmail.com");
		lgn.sendPassword("reva321");

		// Clicking on "Sign In" button
		lgn.clickLoginBtn();

		// Asserting if "Dashboard" page is opened or not
		String expDashboardText = "Dashboard ‹ Real Estate — WordPress";
		generic.AssertTitle(expDashboardText);
	}

	// 3. To verify whether application allows the admin to recover the password
	@Test
	public void Scenario3() throws InterruptedException {
		System.out.println("Test Scenario3 starts");
		
		// Clicking on "LOG IN/REGISTER" button
		homePage.ClickSigninLink();

		// Asserting if "My Profile" page is launched or not
		// Getting webelement to pass on to assertion for text verification
		WebElement element = generic.getElement("//nav[@id='breadcrumbs']/preceding-sibling::h2", "xpath");

		// Asserting if we have "My Profile" text present on the page or not
		generic.AssertText("My Profile", element);

		// Scrolling the page
		Thread.sleep(5000L);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300);");

		// Clicking on "Lost Your Password" link
		lgn.clickLostPwdLink();

		// Getting webelement to pass on to assertion for text verification
		WebElement element2 = generic.getElement("//nav[@id='breadcrumbs']/preceding-sibling::h2", "xpath");
		generic.AssertText("Lost Password", element2);

		// Entering valid email id and click on "Reset Password" button after above
		// assertion is passed
		lost.EnterEmail("revasharma@gmail.com");
		lost.ClickSubmitButton();

		// Asserting if "Reset Password" page has opened or not
		// Getting webelement to pass on to assertion for text verification
		WebElement element3 = generic.getElement("//p", "xpath");
		generic.AssertText("A confirmation link has been sent to your email address", element3);

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
