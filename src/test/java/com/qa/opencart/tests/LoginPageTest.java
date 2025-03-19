package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design open cart login page")
@Story("US 101: Login page features")
@Feature("F50: Feature login page")
@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest {


	@Description("login page title test...")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstant.LOGIN_PAGE_TITLE);
	}

	@Description("login page url test verficication...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstant.LOGIN_PAGE_URL_FRACTION));
	}

	@Description("verifying forgot pwd link test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotLinkExistTest() {
		Assert.assertTrue( loginPage.isForgotPwdLinkExist());
	}

	@Description("verifying App logo exist test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4)
	public void appLogoExistTest() {
		Assert.assertTrue(loginPage.isLogoExist());
	}

	@Description("verifying user is able to login with correct credentials...")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 5)
	public void loginPageTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password")); //this maintain in config.properties
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}
