package com.training.pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RealEstatePagePOM {
	
private WebDriver driver; 
	
	public RealEstatePagePOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
		
	@FindBy(xpath="//input[@title='Search input']")
	private WebElement searchText;
	
	@FindBy(xpath="//div[@class='resdrg']/div/div/h3/a[1]")
	private List<WebElement> list;
	
	public void SearchProp(String sText) {
		this.searchText.clear(); 
		this.searchText.sendKeys(sText); 
	}
	
	public void ClickList() {
		this.list.get(0).click();
	}
	
	
}
