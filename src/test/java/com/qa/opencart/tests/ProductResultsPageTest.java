package com.qa.opencart.tests;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductResultsPageTest extends BaseTest{

	@BeforeClass
	public void productInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));//come from config.properties
	}
	
	@DataProvider
	public Object[][] searchField() {
		return new Object[][] {
			{"macBook","MacBook Pro","4"},
			{"samsung","Samsung SyncMaster 941BW","1"},
			{"canon","Canon EOS 5D","3"}
		};

	}
	
	@Test(dataProvider = "searchField")
	public void productImageTest(String searchKey, String productName,String imageCount) {
		searchResultPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		productInfoPage.getProductImageCount();
	}
	
	@Test
	public void productInfoTest() {
		searchResultPage = accPage.doSearch("macBook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");
		Map<String, String> productDetailsMap = productInfoPage.getProductDetails();
		
		softAssert.assertEquals(productDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDetailsMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productDetailsMap.get("Reward Points"), "800");
		softAssert.assertEquals(productDetailsMap.get("price"), "$2,000.00");
		softAssert.assertEquals(productDetailsMap.get("exTaxPrice"), "$2,000.00");
		softAssert.assertAll();
		
	}
	
	
	
}
