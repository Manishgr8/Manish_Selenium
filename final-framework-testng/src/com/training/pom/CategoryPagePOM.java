package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CategoryPagePOM {
	
private WebDriver driver; 
	
	public CategoryPagePOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	
	
	
	@FindBy(id="tag-name")
	private WebElement nameTextBox; 
	
	@FindBy(id="tag-slug")
	private WebElement slugTextBox; 
	
	@FindBy(id="tag-description")
	private WebElement descTextBox; 
	
	@FindBy(name="submit")
	private WebElement addCategory; 
	
	@FindBy(id="search-submit")
	private WebElement searchCategory; 
	
	@FindBy(xpath="//h1[text()='Categories']")
	private WebElement categoryText; 
	
	
	public void EnterName(String name) {
		this.nameTextBox.clear(); 
		this.nameTextBox.sendKeys(name); 
	}
	
	public void EnterSlug(String slugVal) {
		this.slugTextBox.clear(); 
		this.slugTextBox.sendKeys(slugVal); 
	}
	
	public void EnterDesc(String des) {
		this.descTextBox.clear(); 
		this.descTextBox.sendKeys(des); 
	}
	
	public void ClickAddCategoryLink() {
		this.addCategory.click();
	}
	
	public void ClickSearchCategoryLink() {
		this.searchCategory.click();
	}
	
	
	
	
}
