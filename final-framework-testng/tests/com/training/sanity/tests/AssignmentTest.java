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

public class AssignmentTest {
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
	private CategoryPagePOM category;
	private PostPagePOM post;

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
		category = new CategoryPagePOM(driver);
		post = new PostPagePOM(driver);

		// open the browser
		driver.get(baseUrl);
		Thread.sleep(5000L);

		// Asserting if correct page has been launched or not
		generic.AssertTitle("Real Estate");
	}

	/*
	 * // To Verify whether application allows user to send the query in Contact
	 * Form // Page
	 * 
	 * @Test public void Scenario1() throws InterruptedException {
	 * System.out.println("Test Scenario1 starts");
	 * 
	 * 
	 * // Clicking on "Blog" link homePage.ClickBlogLink();
	 * 
	 * // Asserting if correct url has been launched or not
	 * generic.AssertUrl("http://realestatem1.upskills.in/blog/");
	 * 
	 * // Clicking on "Drop us a line" button after assertion is passed.
	 * blog.ClickContactLink();
	 * 
	 * // Getting Webelement to pass on to assertion for text verification
	 * WebElement element =
	 * generic.getElement("//div[@role='form']/preceding-sibling::h4", "xpath");
	 * 
	 * // Asserting if we have "Contact Form" text on the page or not
	 * generic.AssertText("Contact Form", element);
	 * 
	 * // Entering fields of contact form after above assertion is passed
	 * contact.EnterName("reva"); contact.EnterEmail("revasharma@gmail.com");
	 * contact.EnterSubject("apartments");
	 * contact.EnterMessage("looking for an appartment");
	 * 
	 * // Clicking on "Send" button contact.ClickSendLink();
	 * 
	 * // Scrolling to get confirmation message screenshot Thread.sleep(5000L);
	 * JavascriptExecutor js = (JavascriptExecutor) driver;
	 * js.executeScript("window.scrollBy(0,100);");
	 * 
	 * // Asserting if correct confirmation message is displayed or not String
	 * expectedConfirmationMessage =
	 * "Thanks You for your message. It has been sent";
	 * 
	 * // Getting webelement to pass on to assertion for text verification
	 * WebElement element2 =
	 * generic.getElement("//form[@method='post']/div[@role='alert']", "xpath");
	 * 
	 * // Asserting if we have correct confirmation message on the page or not
	 * generic.AssertText(expectedConfirmationMessage, element2); }
	 * 
	 * // To Verify whether application allows registered admin to login into //
	 * application
	 * 
	 * @Test public void Scenario2() { System.out.println("Test Scenario2 starts");
	 * 
	 * // Clicking on "LOG IN/REGISTER" button homePage.ClickSigninLink();
	 * 
	 * // Asserting if "My Profile" page is launched or not // Getting webelement to
	 * pass on to assertion for text verification WebElement element =
	 * generic.getElement("//nav[@id='breadcrumbs']/preceding-sibling::h2",
	 * "xpath");
	 * 
	 * // Asserting if we have "My Profile" text present on the page or not
	 * generic.AssertText("My Profile", element);
	 * 
	 * // Entering username and password lgn.sendUserName("admin");
	 * lgn.sendPassword("admin@123");
	 * 
	 * // Clicking on "Sign In" button lgn.clickLoginBtn();
	 * 
	 * // Asserting if "Dashboard" page is opened or not String expDashboardText =
	 * "Dashboard ‹ Real Estate — WordPress"; generic.AssertTitle(expDashboardText);
	 * }
	 * 
	 * // 3. To verify whether application allows the admin to recover the password
	 * // a. Assuming expected title of page launched after clicking on
	 * "Lost Password" // link is "Reset Password".
	 * 
	 * @Test public void Scenario3() throws InterruptedException {
	 * System.out.println("Test Scenario3 starts");
	 * 
	 * // Clicking on "LOG IN/REGISTER" button homePage.ClickSigninLink();
	 * 
	 * // Asserting if "My Profile" page is launched or not // Getting webelement to
	 * pass on to assertion for text verification WebElement element =
	 * generic.getElement("//nav[@id='breadcrumbs']/preceding-sibling::h2",
	 * "xpath");
	 * 
	 * // Asserting if we have "My Profile" text present on the page or not
	 * generic.AssertText("My Profile", element);
	 * 
	 * // Scrolling the page Thread.sleep(5000L); JavascriptExecutor js =
	 * (JavascriptExecutor) driver; js.executeScript("window.scrollBy(0,300);");
	 * 
	 * // Clicking on "Lost Your Password" link lgn.clickLostPwdLink();
	 * 
	 * // Getting webelement to pass on to assertion for text verification
	 * WebElement element2 =
	 * generic.getElement("//nav[@id='breadcrumbs']/preceding-sibling::h2",
	 * "xpath"); generic.AssertText("Lost Password", element2);
	 * 
	 * // Entering valid email id and click on "Reset Password" button after above
	 * // assertion is passed lost.EnterEmail("revasharma@gmail.com");
	 * lost.ClickSubmitButton();
	 * 
	 * // Asserting if "Reset Password" page has opened or not
	 * generic.AssertTitle("Reset Password");
	 * 
	 * }
	 * 
	 * 
	 */

	// To verify whether application displays added post in blog section of home
	// screen
	@Test
	public void Scenario4() throws InterruptedException {

		// Clicking on "LOG IN/REGISTER" button
		homePage.ClickSigninLink();

		// Asserting if "My Profile" page is launched or not
		// Getting webelement to pass on to assertion for text verification
		WebElement element = generic.getElement("//nav[@id='breadcrumbs']/preceding-sibling::h2", "xpath");

		// Asserting if we have "My Profile" text present on the page or not
		generic.AssertText("My Profile", element);

		// Entering username and password
		lgn.sendUserName("admin");
		lgn.sendPassword("admin@123");

		// Clicking on "Sign In" button
		lgn.clickLoginBtn();

		// Asserting if "Dashboard" page is opened or not
		String expDashboardText = "Dashboard ‹ Real Estate — WordPress";
		generic.AssertTitle(expDashboardText);

		// Step-1 ===== Clicking on Posts link
		dash.ClickPostLink();

		// Getting webelement to pass on to assertion for text verification
		WebElement element2 = generic.getElement("//a[@href='edit.php' and text()='All Posts']", "xpath");
		generic.AssertText("All Posts", element2);

		// Step-2 =========== Clicking on Categories link
		dash.ClickCategoryLink();

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

		// Step-6a =========== Clicking on Add New Category button
		category.ClickAddCategoryLink();

		// Step-6b =========== Clicking on Search Categories button to refresh the list
		category.ClickSearchCategoryLink();

		// Asserting if added category got added or not
		List<WebElement> list1 = generic.getElementsAsList("//table/tbody/tr/td/strong/a", "xpath");

		System.out.println("size is: " + list1.size());

		for (int i = 0; i < list1.size(); i++) {
			String text = list1.get(i).getText();
			System.out.println("Text for index: " + i + " " + text);

			if (text.equalsIgnoreCase("New Launches")) {
				break;
			} else if (i == (list1.size() - 1)) {
				Assert.assertTrue(false, "Category could not be found");
			}
		}

		// Step-7 =========== Clicking on All Posts link
		dash.ClickAllPostsLink();

		// Getting webelement to pass on to assertion for text verification
		WebElement element4 = generic.getElement("//h1[text()='Posts']", "xpath");

		// Asserting if all posts page get displayed
		generic.AssertText("Posts", element4);

		// Step-8 =========== Clicking on Add New button
		post.ClickAddNewPostBtn();

		// Asserting Add New Post page is opened
		generic.AssertTitle("Add New Post ‹ Real Estate — WordPress");

		// Step-9 =========== Entering Valid credentials in Enter title here textbox
		post.EnterPostName("New Launches");

		// Step-10 =========== Entering valid credentials in body textbox
		post.EnterBodyText("New Launch in Home");

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

		Thread.sleep(5000L);

		// Step-12 =========== Clicking on Publish button
		post.ClickPublishBtn();

		// Asserting if "Post published. View post " message is displayed
		WebElement element5 = generic.getElement("//div[@id='message']/p", "xpath");
		generic.AssertText("Post published. View post", element5);

		Thread.sleep(5000L);

		// Step-13 =========== Clicking on View Post link
		post.ClickViewPostLink();

		// Asserting if Blog section of home screen containing post details should get
		// displayed
		List<WebElement> list4 = generic.getElementsAsList("//div[@class='blog-page']//div[@class='post-content']/h3",
				"xpath");

		for (int i = 0; i < list4.size(); i++) {
			String text = list4.get(i).getText();
			if (text.equalsIgnoreCase("New Launches")) {
				break;
			} else if (i == (list1.size() - 1)) {
				Assert.assertTrue(false, "Post could not be found");
			}
		}
	}

	// To verify whether application display comments added by the user in admin
	// page
	// @Test(dependsOnMethods="Scenario4")
	@Test
	public void Scenario5() {
		System.out.println("Test Scenario5 starts");

		// Step-1 ======== Clicking on "Blog" link
		homePage.ClickBlogLink();

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

		// Step-3 ======== Entering valid details in Comment, name, email textbox
		blog.EnterComments("good");
		blog.EnterAuthorName("manish");
		blog.EnterEmailId("mka@rediffmail.com");

		// Step-4 ======== Click on Post Comment button
		blog.ClickPostCommentsBtn();

		// Asserting whether Comment has been added and displayed on the screen
		List<WebElement> list3 = generic.getElementsAsList("//section[@class='comments']//li/div/div[2]/p", "xpath");

		System.out.println("Size1 is: " + list3.size());
		for (int i = 0; i < list3.size(); i++) {
			String text = list3.get(i).getText();
			System.out.println("Text for index: " + i + " " + text);

			if (text.equalsIgnoreCase("good")) {
				break;
			} else if (i == (list3.size() - 1)) {
				Assert.assertTrue(false, "Comment could not be found");
			}
		}

		// Step-5 =========== Open admin site in new window
		driver.navigate().to("http://realestatem1.upskills.in/my-profile/");

		// Step- 6 and 7 ========= Entering username and password
		lgn.sendUserName("admin");
		lgn.sendPassword("admin@123");

		// Step-8 ============ Clicking on "Sign In" button
		lgn.clickLoginBtn();

		// Asserting if "Dashboard" page is opened or not
		String expDashboardText = "Dashboard ‹ Real Estate — WordPress";
		generic.AssertTitle(expDashboardText);

		// Step-9 ============ Click on Comments tab
		dash.ClickCommentsLink();

		// Asserting whether comments added for the post is displayed
		List<WebElement> list4 = generic.getElementsAsList("//table/tbody/tr/td[2]/p", "xpath");
		System.out.println("Size2 is: " + list4.size());

		for (int i = 0; i < list4.size(); i++) {
			String text = list4.get(i).getText();
			System.out.println("Text for index: " + i + " " + text);

			if (text.equalsIgnoreCase("good")) {
				break;
			} else if (i == (list4.size() - 1)) {
				Assert.assertTrue(false, "Comment could not be found");
			}
		}
	}

	// To verify whether application displays added post in all post
	@Test
	public void Scenario6() throws InterruptedException {

		// Clicking on "LOG IN/REGISTER" button
		homePage.ClickSigninLink();

		// Asserting if "My Profile" page is launched or not
		// Getting webelement to pass on to assertion for text verification
		WebElement element = generic.getElement("//nav[@id='breadcrumbs']/preceding-sibling::h2", "xpath");

		// Asserting if we have "My Profile" text present on the page or not
		generic.AssertText("My Profile", element);

		// Entering username and password
		lgn.sendUserName("admin");
		lgn.sendPassword("admin@123");

		// Clicking on "Sign In" button
		lgn.clickLoginBtn();

		// Asserting if "Dashboard" page is opened or not
		String expDashboardText = "Dashboard ‹ Real Estate — WordPress";
		generic.AssertTitle(expDashboardText);

		// Step-1 ===== Clicking on Posts link
		dash.ClickPostLink();

		// Getting webelement to pass on to assertion for text verification
		WebElement element2 = generic.getElement("//a[@href='edit.php' and text()='All Posts']", "xpath");
		generic.AssertText("All Posts", element2);

		// Step-2 =========== Click on Add New link
		post.ClickAddNewPostBtn();

		// Asserting Add New Post page is opened
		generic.AssertTitle("Add New Post ‹ Real Estate — WordPress");

		// Step-3 =========== Entering Valid credentials in Enter title here textbox
		post.EnterPostName("New Launches");

		// Step-4 =========== Entering valid credentials in body textbox
		post.EnterBodyText("New Launch in Home");

		Thread.sleep(5000L);

		// Step-5 =========== Clicking on Publish button
		post.ClickPublishBtn();

		Thread.sleep(5000L);

		// Asserting if "Post published. View post " message is displayed
		WebElement element3 = generic.getElement("//div[@id='message']/p", "xpath");
		generic.AssertText("Post published. View post", element3);

		Thread.sleep(5000L);

		// Step-6 =========== Click on All Posts
		dash.ClickAllPostsLink();

		// Asserting if Blog section of home screen containing post details should get
		// displayed
		List<WebElement> list = generic.getElementsAsList("//table/tbody//tr/td/strong/a", "xpath");

		System.out.println("Size 1 is " + list.size());

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

		// Asserting if Details of added post are displayed
		WebElement element4 = generic.getElement("//textarea[@name='content']", "xpath");
		generic.AssertText("New Launch in Home", element4);

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
