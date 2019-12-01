package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PostPagePOM {
	
private WebDriver driver; 
	
	public PostPagePOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//a[@class='page-title-action' and text()='Add New']")
	private WebElement addNewPostBtn;
	
	@FindBy(name="post_title")
	private WebElement postNameText;
	
	@FindBy(xpath="//textarea[@name='content']")
	private WebElement bodyText;
	
	@FindBy(id="publish")
	private WebElement publishBtn;
	
	@FindBy(xpath= "//a[text()='View post']")
	private WebElement viewPostLink;
	
	
	
	public void ClickAddNewPostBtn() {
		this.addNewPostBtn.click();
	}
	
	public void EnterPostName(String postName) {
		this.postNameText.clear(); 
		this.postNameText.sendKeys(postName); 
	}
	
	public void EnterBodyText(String body) {
		this.bodyText.clear(); 
		this.bodyText.sendKeys(body); 
	}
	
	public void ClickPublishBtn() {
		this.publishBtn.click();
	}
	
	public void ClickViewPostLink() {
		this.viewPostLink.click();
	}
	
	
}
