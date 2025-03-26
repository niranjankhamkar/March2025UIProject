package com.qa.opencart.facory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	//public WebDriver driver;
	protected Properties prop;
	private OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static String highlight = null;
	

	public Properties initProp() {

		FileInputStream ip = null;
		prop = new Properties();
		
		String envName = System.getProperty("env");//come from cammand prompt
		System.out.println("env name is : " + envName+" comes from cammand prompt");

		try {
			if (envName == null) {
				System.out.println("envName is 'Null', so Bydefault TC run on 'qa'env");
				ip = new FileInputStream(".\\src\\test\\resources\\config\\config.qa.properties");
			} else {

				switch (envName.toLowerCase().trim()) {
				case "qa": {
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.qa.properties");
					break;
				}
				case "dev": {
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.dev.properties");
					break;
				}
				case "stage": {
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.stage.properties");
					break;
				}
				case "uat": {
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.uat.properties");
					break;
				}
				case "prod": {
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.prod.properties");
					break;
				}
				default:
					System.out.println("Please pass the right env name " + envName);
					throw new FrameworkException("Wrong envName : " + envName);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	
	
	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser"); //come from config.properties
//		String browserName = System.getProperty("browser");//come from cammand prompt
		
		System.out.println("Browser Name is : " + browserName);
		
		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase().trim()) {
		case "chrome": {
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//if true:- run it on grid
				initRemoteDriver(browserName);
			}else {
				//if false :- run it on local
				//driver = new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			
			break;
		}
		case "firefox": {
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//if true:- run it on grid
				initRemoteDriver(browserName);
			}else {
				//if false :- run it on local
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
		}
		case "edge": {
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//if true:- run it on grid
				initRemoteDriver(browserName);
			}else {
				//if false :- run it on local
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;
		}
		default:
			System.out.println("please pass right browser name... : " + browserName);
			throw new FrameworkException("No browser found");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}
	
	
	/**
	 * run test on Grid
	 * @param browserName
	 */
	private void initRemoteDriver(String browserName) {
		System.out.println("Running tests on GRID with browser "+browserName);
		
		try {
			switch (browserName.toLowerCase().trim()){
			case "chrome": 
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
				
			case "firefox":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;
				
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;
			
				
			default:
				System.out.println("Wrong browser info.. can not run on Grid remote machine..");
				break;
			}
		}catch(MalformedURLException e) {
			
		}
		
	}
	
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	
	public static String getScreenshot(String methodName) {

		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
	
}
