package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
import com.training.pom.DashboardPagePOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

import com.training.pom.CategoryPagePOM;
import com.training.pom.PostPagePOM;
import com.training.sanity.tests.AssignmentMedium2aTest;

public class AssignmentMedium2bTest {
	private WebDriver driver;
	private String baseUrl;
	private HomePagePOM homePage;
	private BlogPagePOM blog;
	private static Properties properties;
	private ScreenShot screenShot;
	private LoginPagePOM lgn;
	private DashboardPagePOM dash;
	private GenericMethods generic;

	@BeforeClass(groups = "scenario2")
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeMethod(groups = "scenario2")
	public void setUp() throws Exception {
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		homePage = new HomePagePOM(driver);
		blog = new BlogPagePOM(driver);
		lgn = new LoginPagePOM(driver);
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

	// To verify whether application display comments added by the user in admin
	// page

	@Test(groups = "scenario2", dependsOnGroups="scenario1")
	public void Scenario2() throws InterruptedException {
		System.out.println("Scenario 2 starts");
		// Step-1 ======== Clicking on "Blog" link
		homePage.ClickBlogLink();
		screenShot.captureScreenShot();

		// Asserting if correct url has been launched or not
		generic.AssertUrl("http://realestatem1.upskills.in/blog/");

		// Step-2 ======== Clicking on Read More link of post added by admin
		List<WebElement> list1 = generic.getElementsAsList("//div[@class='blog-page']//div[@class='post-content']/h3",
				"xpath");

		List<WebElement> list2 = generic.getElementsAsList(
				"//div[@class='blog-page']//div[@class='post-content']/h3/following-sibling::a", "xpath");

		for (int i = 0; i < list1.size(); i++) {
			String text = list1.get(i).getText();

			if (text.equalsIgnoreCase("New Launches")) {
				// Clicking on Read more link
				list2.get(i).click();
				break;
			}
		}
		screenShot.captureScreenShot();
		
		// Step-3 ======== Entering valid details in Comment, name, email textbox
		blog.EnterComments("good");
		blog.EnterAuthorName("manish");
		blog.EnterEmailId("mka@rediffmail.com");
		screenShot.captureScreenShot();

		// Step-4 ======== Click on Post Comment button
		blog.ClickPostCommentsBtn();

		// Asserting whether Comment has been added and displayed on the screen
		List<WebElement> list3 = generic.getElementsAsList("//section[@class='comments']//li/div/div[2]/p", "xpath");
		generic.AssertList(list3, "good");
		screenShot.captureScreenShot();

		// Step-5 =========== Open admin site in new window
		driver.navigate().to("http://realestatem1.upskills.in/my-profile/");
		screenShot.captureScreenShot();

		// Steps 6 to 8 ========= Login with admin credentials
		lgn.AdminLogin("admin", "admin@123");
		screenShot.captureScreenShot();

		// Asserting if "Dashboard" page is opened or not
		String expDashboardText = "Dashboard ‹ Real Estate — WordPress";
		generic.AssertTitle(expDashboardText);

		// Step-9 ============ Click on Comments tab
		dash.ClickCommentsLink();
		screenShot.captureScreenShot();
		// Asserting whether comments added for the post is displayed
		List<WebElement> list4 = generic.getElementsAsList("//table/tbody/tr/td[2]/p", "xpath");
		generic.AssertList(list4, "good");
		System.out.println("Scenario 2 ends");
	}

	@AfterMethod(groups = "scenario2")
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
