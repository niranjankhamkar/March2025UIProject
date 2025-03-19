package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.facory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {

	protected WebDriver driver;
	protected Properties prop;
	public DriverFactory df;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	protected SoftAssert softAssert;
	
	
	
	@BeforeTest
	@Parameters({"browser"})	//browser come from testng_regression.xml file
	public void setUp(String browserName) {
		df = new DriverFactory();
		prop =  df.initProp();	//ya prop mdhe srv properties aalya aahet eg.browser,url,un,pwd,..etc
		
		if(browserName != null) {//browser chi value xml kadun aali tr aat mdhe yeil
			prop.setProperty("browser", browserName);	//setProperty method use krun runtime mdhe browser
														//chi value update in config.properties file
		}
		
		//tdDriver cha return type aahe WebDriver. mnun driver ref use kela
		driver = df.initDriver(prop);//ata ha tdDriver mnun ch kam krnar.now this is not normal driver
		loginPage = new LoginPage(driver);
		
		softAssert = new SoftAssert();
	}


	@AfterTest
	public void tearDown() {
		driver.quit();
	}


}
