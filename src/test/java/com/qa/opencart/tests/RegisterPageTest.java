package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class RegisterPageTest extends BaseTest{

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.nevigateToRegisterPage();
	}
	
	
	public String getRandomEmailID() {
		return "testAutomation"+System.currentTimeMillis()+"@opencart.com";
	}

	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][]{
			{"niranjan","Khamkar","9545","abcd1234","Yes"},
			{"shweta","Khamkar","9535","abcd1234","No"}
		};
	}

	@Test(dataProvider = "getSearchData")
	public void userRegTest(String firstName,String lastName,String telephone,String password,String subcribe) {
		boolean isRegDone = registerPage.userRegistration(firstName,lastName,getRandomEmailID(),telephone,password,subcribe);
		Assert.assertTrue(isRegDone);
	}





}
