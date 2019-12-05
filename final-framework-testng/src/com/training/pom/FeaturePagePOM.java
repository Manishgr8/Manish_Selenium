package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FeaturePagePOM {
	
private WebDriver driver; 
	
	public FeaturePagePOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(id="tag-name")
	private WebElement featureNameText;
	
	@FindBy(id="tag-slug")
	private WebElement slugNameText;
	
	@FindBy(id="tag-description")
	private WebElement descText;
	
	@FindBy(name="submit")
	private WebElement addNewFeatureBtn;
	
	@FindBy(xpath="//input[@id='tag-search-input']")
	private WebElement searchTextBox;
	
	@FindBy(xpath="//input[@value='Search Features']")
	private WebElement searchFeaturesBtn;
	
	
	
	
	
	public void EnterFeatureName(String fName) {
		this.featureNameText.clear(); 
		this.featureNameText.sendKeys(fName); 
	}
	
	public void EnterSlugName(String sName) {
		this.slugNameText.clear(); 
		this.slugNameText.sendKeys(sName); 
	}
	
	public void EnterDescText(String desc) {
		this.descText.clear(); 
		this.descText.sendKeys(desc); 
	}
	
	public void ClickAddNewFeatureBtn() {
		this.addNewFeatureBtn.click();
	}
	
	public void ClickSearchFeaturesBtn() {
		this.searchFeaturesBtn.click();
	}
	
	public void SearchFeature(String fValue) {
		this.searchTextBox.clear(); 
		this.searchTextBox.sendKeys(fValue); 
	}
}
