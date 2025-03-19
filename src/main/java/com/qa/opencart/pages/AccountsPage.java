package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.Utils.ElementUtil;
import com.qa.opencart.constant.AppConstant;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By logoutLink = By.linkText("Logout");
	private By search = By.name("search");
	private By SearchIcon = By.cssSelector("button.btn.btn-default.btn-lg");
	private By accHeaders = By.xpath("//div[@id='content']/h2");
	

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstant.ACCOUNTS_PAGE_TITLE, AppConstant.SHORT_DEFAULT_WAIT);
		System.out.println("Acc page Title is : "+title);
		return title;
	}


	public String getAccPageURL() {
		String url =eleUtil.waitForURLContains(AppConstant.ACCOUNTS_PAGE_URL_FRACTION, AppConstant.SHORT_DEFAULT_WAIT);
		System.out.println("Acc page url is : "+url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.waitForVisibilityOfElement(logoutLink,AppConstant.SHORT_DEFAULT_WAIT).isDisplayed();
	}

	public void logout() {
		if(isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
		}
	}

	public boolean isSearchFieldExist() {
		return eleUtil.waitForPresenceOfElement(search, AppConstant.SHORT_DEFAULT_WAIT).isDisplayed();

	}

	public List<String> getAccountHeaders() {
		List<WebElement> headersList = eleUtil.waitForPresenceOfElements(accHeaders,  AppConstant.LONG_DEFAULT_WAIT);
		List<String> headersValueList = new ArrayList<>();

		for(WebElement e : headersList) {
			String text = e.getText();
			headersValueList.add(text);
		}
		return headersValueList;
	}

	public SearchResultsPage doSearch(String searchKey) {

		eleUtil.waitForVisibilityOfElement(search, AppConstant.MEDIUM_DEFAULT_WAIT).clear();
		eleUtil.waitForVisibilityOfElement(search, AppConstant.MEDIUM_DEFAULT_WAIT).sendKeys(searchKey);
		eleUtil.doClick(SearchIcon);
		return new SearchResultsPage(driver);

	}





}
