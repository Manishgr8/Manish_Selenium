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

public class Assignment2aTest {
	private WebDriver driver;
	private String baseUrl;
	private HomePagePOM homePage;
	private BlogPagePOM blog;
	private static Properties properties;
	private ScreenShot screenShot;
	private LoginPagePOM lgn;
	private DashboardPagePOM dash;
	private GenericMethods generic;
	private CategoryPagePOM category;
	private PostPagePOM post;

	@BeforeClass(groups = { "scenario1", "scenario3" })
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeMethod(groups = { "scenario1", "scenario3" })
	public void setUp() throws Exception {
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		homePage = new HomePagePOM(driver);
		blog = new BlogPagePOM(driver);
		lgn = new LoginPagePOM(driver);
		dash = new DashboardPagePOM(driver);
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		generic = new GenericMethods(driver);
		category = new CategoryPagePOM(driver);
		post = new PostPagePOM(driver);

		// open the browser
		driver.get(baseUrl);
		Thread.sleep(5000L);

		// Asserting if correct page has been launched or not
		generic.AssertTitle("Real Estate");

		// Login with admin credentials
		lgn.AdminLogin("admin", "admin@123");

		// Asserting if "Dashboard" page is opened or not
		String expDashboardText = "Dashboard ‹ Real Estate — WordPress";
		generic.AssertTitle(expDashboardText);
	}

	// To verify whether application displays added post in blog section of home
	// screen
	@Test(groups = "scenario1")
	public void Scenario1() throws InterruptedException {

		System.out.println("Scenario 1 starts");
		// Step-1 ===== Clicking on Posts link
		dash.ClickPostLink();
		screenShot.captureScreenShot();
		
		// Getting webelement to pass on to assertion for text verification
		WebElement element2 = generic.getElement("//a[@href='edit.php' and text()='All Posts']", "xpath");
		generic.AssertText("All Posts", element2);

		// Step-2 =========== Clicking on Categories link
		dash.ClickCategoryLink();
		screenShot.captureScreenShot();
		
		// Getting webelement to pass on to assertion for text verification
		WebElement element3 = generic.getElement("//h1[text()='Categories']", "xpath");

		// Asserting if Categories page is opened or not
		generic.AssertText("Categories", element3);

		// Step-3 =========== Entering Valid Credentials in Name textbox
		category.EnterName("New Launches");
		
		// Step-4 =========== Entering Valid Credentials in Slug textbox
		category.EnterSlug("launch");

		// Step-5 =========== Entering Valid Credentials in Description textbox
		category.EnterDesc("New Launches of villas, apartments, flats");
		screenShot.captureScreenShot();
		
		// Step-6a =========== Clicking on Add New Category button
		category.ClickAddCategoryLink();
		
		// Step-6b =========== Clicking on Search Categories button to refresh the list
		category.ClickSearchCategoryLink();
		screenShot.captureScreenShot();

		// Asserting if added category got added or not
		List<WebElement> list1 = generic.getElementsAsList("//table/tbody/tr/td/strong/a", "xpath");
		generic.AssertList(list1, "New Launches");

		// Step-7 =========== Clicking on All Posts link
		dash.ClickAllPostsLink();
		screenShot.captureScreenShot();

		// Getting webelement to pass on to assertion for text verification
		WebElement element4 = generic.getElement("//h1[text()='Posts']", "xpath");

		// Asserting if all posts page get displayed
		generic.AssertText("Posts", element4);

		// Step-8 =========== Clicking on Add New button
		post.ClickAddNewPostBtn();
		screenShot.captureScreenShot();

		// Asserting Add New Post page is opened
		generic.AssertTitle("Add New Post ‹ Real Estate — WordPress");

		// Step-9 =========== Entering Valid credentials in Enter title here textbox
		post.EnterPostName("New Launches");
		screenShot.captureScreenShot();
		
		// Step-10 =========== Entering valid credentials in body textbox
		post.EnterBodyText("New Launch in Home");
		screenShot.captureScreenShot();

		// Step-11 =========== Click on Checkbox beside created category name of
		// category section
		List<WebElement> list2 = generic.getElementsAsList("//div[@id='category-all']/ul/li/label", "xpath");
		List<WebElement> list3 = generic.getElementsAsList("//div[@id='category-all']/ul/li/label/input", "xpath");

		for (int i = 0; i < list2.size(); i++) {
			String text = list2.get(i).getText();

			if (text.equalsIgnoreCase("New Launches")) {
				// Clicking on checkbox
				list3.get(i).click();
				break;
			}
		}
		screenShot.captureScreenShot();
		Thread.sleep(5000L);

		// Step-12 =========== Clicking on Publish button
		post.ClickPublishBtn();
		screenShot.captureScreenShot();

		// Asserting if "Post published. View post " message is displayed
		WebElement element5 = generic.getElement("//div[@id='message']/p", "xpath");
		generic.AssertText("Post published. View post", element5);

		Thread.sleep(5000L);

		// Step-13 =========== Clicking on View Post link
		post.ClickViewPostLink();
		screenShot.captureScreenShot();

		// Asserting if Blog section of home screen containing post details is displayed
		List<WebElement> list4 = generic.getElementsAsList("//div[@class='blog-page']//div[@class='post-content']/h3",
				"xpath");

		generic.AssertList(list4, "New Launches");
		System.out.println("Scenario 1 ends");
	}

	// To verify whether application displays added post in all post
	@Test(groups = "scenario3")
	public void Scenario3() throws InterruptedException {

		System.out.println("Scenario 3 starts");
		// Step-1 ===== Clicking on Posts link
		dash.ClickPostLink();
		screenShot.captureScreenShot();

		// Getting webelement to pass on to assertion for text verification
		WebElement element2 = generic.getElement("//a[@href='edit.php' and text()='All Posts']", "xpath");
		generic.AssertText("All Posts", element2);

		// Step-2 =========== Click on Add New link
		post.ClickAddNewPostBtn();
		screenShot.captureScreenShot();

		// Asserting Add New Post page is opened
		generic.AssertTitle("Add New Post ‹ Real Estate — WordPress");

		// Step-3 =========== Entering Valid credentials in Enter title here textbox
		post.EnterPostName("New Launches");
		screenShot.captureScreenShot();

		// Step-4 =========== Entering valid credentials in body textbox
		post.EnterBodyText("New Launch in Home");
		screenShot.captureScreenShot();
		Thread.sleep(5000L);

		// Step-5 =========== Clicking on Publish button
		post.ClickPublishBtn();
		screenShot.captureScreenShot();
		Thread.sleep(5000L);

		// Asserting if "Post published. View post " message is displayed
		WebElement element3 = generic.getElement("//div[@id='message']/p", "xpath");
		generic.AssertText("Post published. View post", element3);

		Thread.sleep(5000L);

		// Step-6 =========== Click on All Posts
		dash.ClickAllPostsLink();
		screenShot.captureScreenShot();
		
		// Asserting if Blog section of home screen containing post details is displayed
		List<WebElement> list = generic.getElementsAsList("//table/tbody//tr/td/strong/a", "xpath");

		for (int i = 0; i < list.size(); i++) {
			String text = list.get(i).getText();

			if (text.equalsIgnoreCase("New Launches")) {
				// Step-7 ============= Click on Post created (New Launches)
				list.get(i).click();
				break;
			} else if (i == (list.size() - 1)) {
				Assert.assertTrue(false, "Post could not be found");
			}
		}
		screenShot.captureScreenShot();
		
		// Asserting if Details of added post are displayed
		WebElement element4 = generic.getElement("//textarea[@name='content']", "xpath");
		generic.AssertText("New Launch in Home", element4);
		System.out.println("Scenario 3 ends");
	}

	@AfterMethod(groups = { "scenario1", "scenario3" })
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
