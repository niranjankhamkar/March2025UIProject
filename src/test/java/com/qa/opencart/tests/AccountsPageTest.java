package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constant.AppConstant;

public class AccountsPageTest extends BaseTest{



	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));//come from config.properties

	}

	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstant.ACCOUNTS_PAGE_TITLE);
	}

	@Test
	public void accPageURLTest() {
		String actURL = accPage.getAccPageURL();
		Assert.assertTrue(actURL.contains(AppConstant.ACCOUNTS_PAGE_URL_FRACTION));
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void isSearchFieldExistTest() {
		Assert.assertTrue(accPage.isSearchFieldExist());
	}


	@Test
	public void accPageHeadersCountTest() {
		List<String> actAccPageHeaderList = accPage.getAccountHeaders();
		System.out.println(actAccPageHeaderList);
		Assert.assertEquals(actAccPageHeaderList.size(), AppConstant.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHearsTest() {
		List<String> actAccPageHeaderList = accPage.getAccountHeaders();
		System.out.println(actAccPageHeaderList);
		Assert.assertEquals(actAccPageHeaderList, AppConstant.ACCOUNTS_PAGE_HEADERS_LIST);
		
	}
	
	@Test
	public void searchTest() {
		searchResultPage = accPage.doSearch("MacBook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");
		String actProductHeader = productInfoPage.getProductHeaderName();
		Assert.assertEquals(actProductHeader,"MacBook Pro");
	}
	
	
	
	
	


//	@DataProvider
//	public Object[][] searchFieldExcelData() {
//		Object searchData[][] = ExcelUtil.getTestData(AppConstant.PRODUCT_DATA_SHEET_NAME);
//		return searchData;
//
//	}



	
}
