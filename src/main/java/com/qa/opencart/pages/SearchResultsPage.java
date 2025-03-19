package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Utils.ElementUtil;
import com.qa.opencart.constant.AppConstant;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	//private  By searchField = By.name("search");

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);

	}

	public ProductInfoPage selectProduct(String productName) {
		//eleUtil.waitForVisibilityOfElement(searchField, AppConstant.LONG_DEFAULT_WAIT).clear();
		eleUtil.waitForVisibilityOfElement(By.linkText(productName), AppConstant.LONG_DEFAULT_WAIT).click();
		return new ProductInfoPage(driver);
	}




}
