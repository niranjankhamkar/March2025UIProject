package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.Utils.ElementUtil;
import com.qa.opencart.constant.AppConstant;

public class ProductInfoPage {

	public WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader= By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails li");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPricedata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	private Map<String,String> productMap = new HashMap<String,String>();
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);

	}
	
	public String getProductHeaderName() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("product header is : "+productHeaderVal);
		return productHeaderVal;
	}
	
	public int getProductImageCount(){
		int imageCount = eleUtil.waitForVisibilityOfElements(productImages, AppConstant.MEDIUM_DEFAULT_WAIT).size();
		System.out.println("Product "+getProductHeaderName()+" image count is "+imageCount);
		return imageCount;
	}
	
	private void getProductMetaData() {
		List<WebElement> metaDataList = eleUtil.waitForVisibilityOfElements(productMetaData, AppConstant.MEDIUM_DEFAULT_WAIT);
		
		for(WebElement e : metaDataList) {
			String mataData = e.getText();
			String key = mataData.split(":")[0].trim();
			String value = mataData.split(":")[1].trim();
			productMap.put(key, value);
		}
	}
	
	private void getProductPriceData() {
		List<WebElement> metaListPrice = eleUtil.waitForVisibilityOfElements(productPricedata, AppConstant.SHORT_DEFAULT_WAIT);
		
		String productPrice = metaListPrice.get(0).getText();
		String productExTaxPrice = metaListPrice.get(1).getText().split(":")[1].trim();
		
		productMap.put("price", productPrice);
		productMap.put("exTaxPrice", productExTaxPrice);
	}
	
	public Map<String, String> getProductDetails() {
		productMap.put("product name", getProductHeaderName());
		getProductMetaData();
		getProductPriceData();
		System.out.println(productMap);
		return productMap;
	}
	
	

}
