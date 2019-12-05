package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PropertyPagePOM {
	
private WebDriver driver; 
	
	public PropertyPagePOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(name="post_title")
	private WebElement propNameText;
	
	@FindBy(xpath="//textarea[@name='content']")
	private WebElement bodyText;
	
	@FindBy(id="tag-slug")
	private WebElement slugNameText;
	
	@FindBy(id="tag-description")
	private WebElement descText;
	
	@FindBy(name="submit")
	private WebElement addNewFeatureBtn;
	
	@FindBy(id="publish")
	private WebElement pubBtn;
	
	public void EnterPropName(String pName) {
		this.propNameText.clear(); 
		this.propNameText.sendKeys(pName); 
	}
	
	public void EnterBodyText(String bText) {
		this.bodyText.clear(); 
		this.bodyText.sendKeys(bText); 
	}
	
	public void ClickAddNewFeatureBtn() {
		this.addNewFeatureBtn.click();
	}
	
	public void ClickPublishBtn() {
		this.pubBtn.click();
	}
	
	
}
