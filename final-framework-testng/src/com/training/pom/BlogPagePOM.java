package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlogPagePOM {
	private WebDriver driver; 
	
	public BlogPagePOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@href='/contact/']")
	private WebElement contactLink; 
	
	@FindBy(id= "comment")
	private WebElement commentBox; 
	
	@FindBy(id= "author")
	private WebElement authorNameBox; 
	
	@FindBy(id= "email")
	private WebElement emailTextBox; 
	
	@FindBy(xpath="//div[@class='wp-menu-name' and text()='Comments ']")
	private WebElement commentsLink; 
	
	@FindBy(xpath="//input[@id='submit']")
	private WebElement postCommentBtn; 
	
	
	
	public void ClickContactLink() {
		this.contactLink.click();
	}
	
	
	public void EnterComments(String comment) {
		this.commentBox.clear(); 
		this.commentBox.sendKeys(comment); 
	}
	
	public void ClickCommentsLink() {
		this.commentsLink.click();
	}
	
	public void ClickPostCommentsBtn() {
		this.postCommentBtn.click();
	}
	
	public void EnterAuthorName(String comment) {
		this.authorNameBox.clear(); 
		this.authorNameBox.sendKeys(comment); 
	}
	
	public void EnterEmailId(String comment) {
		this.emailTextBox.clear(); 
		this.emailTextBox.sendKeys(comment); 
	}
	
	
}