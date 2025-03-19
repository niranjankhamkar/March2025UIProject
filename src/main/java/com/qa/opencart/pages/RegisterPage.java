package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Utils.ElementUtil;
import com.qa.opencart.constant.AppConstant;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");

	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton =  By.xpath("//input[@type='submit' and @value='Continue']");
	
	private By successMessg =  By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink =  By.linkText("Register");
	
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	public boolean userRegistration(String firstName, String lastName,String email,String telephone,
									String password, String subscribe) {

	
		eleUtil.waitForVisibilityOfElement(this.firstName, AppConstant.MEDIUM_DEFAULT_WAIT).sendKeys(firstName);
		
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);

		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
			System.out.println("'Yes' <-- radio button selected");
		}else {
			eleUtil.doClick(subscribeNo);
			System.out.println("'No' <-- radio button selected");
		}
		
		eleUtil.doClick(agreeCheckBox);
		System.out.println("click on agree check box");
		
		eleUtil.doClick(continueButton);
		System.out.println("click on continue button");
		
		String successMsg = eleUtil.waitForVisibilityOfElement(successMessg, AppConstant.LONG_DEFAULT_WAIT).getText();
		System.out.println("*****"+successMsg+"******");
		
		if(successMsg.contains(AppConstant.USER_REGISTER_SUCCES_MESSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}else{
			System.out.println("Something wrong in UserRegisterPage");
			return false;
		}
		
		
		
	}



}
