package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Utils.ElementUtil;
import com.qa.opencart.Utils.JavaScriptUtil;
import com.qa.opencart.constant.AppConstant;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private JavaScriptUtil jsUtil;
	
	//By locator
	private By userName = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink =By.linkText("Forgotten Password");
	private By logo = By.xpath("//img[@title='naveenopencart']");
	private By registerLink = By.linkText("Register");

	//constructor to assign the driver
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);//insted of use local driver Use global driver
		jsUtil = new JavaScriptUtil(this.driver);
		
	}
	
	//Page actions/method
	@Step("getting login page title")
	public String getLoginPageTitle() {
		//jsUtil.scrollPageDown();
		String title = eleUtil.waitForTitleIs(AppConstant.LOGIN_PAGE_TITLE, AppConstant.SHORT_DEFAULT_WAIT);
		System.out.println("Title is : "+title);
		return title;
	}

	@Step("getting login page url")
	public String getLoginPageURL() {
		String url =eleUtil.waitForURLContains(AppConstant.LOGIN_PAGE_URL_FRACTION, AppConstant.SHORT_DEFAULT_WAIT);
		System.out.println("login page url : "+url);
		return url;
	}
	
	@Step("checking forgot pwd link exist")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.waitForVisibilityOfElement(forgotPwdLink, AppConstant.SHORT_DEFAULT_WAIT).isDisplayed();
	}
	
	@Step("checking logo exist")
	public boolean isLogoExist() {
		return eleUtil.waitForPresenceOfElement(logo, AppConstant.SHORT_DEFAULT_WAIT).isDisplayed();
	}
	
	@Step("username is : {0} and password {1} ")
	public AccountsPage doLogin(String username, String pwd) {
		//jsUtil.scrollIntoView(driver.findElement(loginBtn));
		System.out.println("Username is : "+username+" & Password is : "+pwd);
		eleUtil.waitForPresenceOfElement(userName, AppConstant.SHORT_DEFAULT_WAIT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		System.out.println("User is logged in....");
		return new AccountsPage(driver);
	}
	
	@Step("navigating to register page")
	public RegisterPage nevigateToRegisterPage() {
		eleUtil.waitForVisibilityOfElement(registerLink,AppConstant.MEDIUM_DEFAULT_WAIT).click();;
		return new RegisterPage(driver);
	}


}
