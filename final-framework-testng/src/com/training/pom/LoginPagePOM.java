package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPagePOM {
	private WebDriver driver; 
	private HomePagePOM home1;
	
	public LoginPagePOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="user_login")
	private WebElement userName; 
	
	@FindBy(id="user_pass")
	private WebElement password;
	
	@FindBy(name="login")
	private WebElement loginButton;
	
	@FindBy(xpath="//form[@class='login']/p[@class='lost_password']/a[contains(text(),'Lost Your Password')]")
	private WebElement lostPwdLink;
	
	@FindBy(partialLinkText="Lost Your Password")
	private WebElement lostLink; 
	
	public void sendUserName(String user) {
		this.userName.clear();
		this.userName.sendKeys(user);
	}
	
	public void sendPassword(String pwd) {
		this.password.clear(); 
		this.password.sendKeys(pwd); 
	}
	
	public void clickLoginBtn() {
		this.loginButton.click(); }
		
	public void clickLostPwdLink() {
		this.lostPwdLink.click(); 
	}
	
	public void ClickLink() {
		this.lostLink.click();
	}
	
	public void AdminLogin(String name, String pwd)
	{
		home1= new HomePagePOM(driver);
		// Clicking on "LOG IN/REGISTER" button
		home1.ClickSigninLink();

		// Entering username and password
		this.sendUserName(name);
				this.sendPassword(pwd);

		// Clicking on "Sign In" button
		this.clickLoginBtn();
	}
	
}