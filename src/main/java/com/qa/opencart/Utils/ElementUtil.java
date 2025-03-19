package com.qa.opencart.Utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exception.FrameworkException;
import com.qa.opencart.facory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private  WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(this.driver);
	}
	
	private void isHighlight(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.drawBorder(element);
			jsUtil.flash(element);
		}	
	}

	
	public By getBy(String locatorType, String locatorValue) {
		By by = null;
		switch (locatorType.trim().toLowerCase()) {
		case "id":
			by = By.id(locatorValue);
			break;
		case "name":
			by = By.name(locatorValue);
			break;
		case "classname":
			by = By.className(locatorValue);
			break;
		case "xpath":
			by = By.xpath(locatorValue);
			break;
		case "css":
			by = By.cssSelector(locatorValue);
			break;
		case "linktext":
			by = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			by = By.partialLinkText(locatorValue);
			break;
		case "tag":
			by = By.tagName(locatorValue);
			break;

		default:
			System.out.println("wrong locator type is passed....." + locatorType);
			throw new FrameworkException("WRONG LOCATOR TYPE");

		}
		return by;

	}

	public String doGetElementAttribute(By locator, String attrName) {
		//return getElement(locator).getAttribute(attrName);
		return getElement(locator).getDomAttribute(attrName);
	}

	// -----------------------
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doElementGetText(String locatorType, String locatorValue) {
		return getElement(locatorType, locatorValue).getText();
	}

	// -----------------------
	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(String locatorType, String locatorValue) {
		getElement(locatorType, locatorValue).click();
	}
	// -----------------------

	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
	}

	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}
	// ----------------------

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		isHighlight(element);
		return element;
	}

	public WebElement getElement(String locatorType, String locatorValue) {
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		isHighlight(element);
		return element;
	}
	// -----------------------

	public List<String> getElementsTextList(By locator) {

		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<>();// pc=0 {}
		for (WebElement e : eleList) {
			String textList = e.getText();

			if (textList.length() != 0) {
				eleTextList.add(textList);
			}
		}
		return eleTextList;
	}

	public List<String> getElementsAttributeList(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<>();// pc=0

		for (WebElement e : eleList) {
			String attrValue = e.getDomAttribute(attrName);
			eleAttrList.add(attrValue);
		}
		return eleAttrList;

	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	// -----------------------

	public void search(By searchField, By suggestion, String searchKey, String suggName) throws InterruptedException {
		doSendKeys(searchField, searchKey);
		Thread.sleep(3000);
		List<WebElement> eleList = driver.findElements(suggestion);
		System.out.println(eleList.size());
		for (WebElement e : eleList) {
			String suggestionName = e.getText();
			System.out.println(suggestionName);
			if (suggestionName.contains(suggName)) {
				e.click();
				break;
			}
		}
	}

	// -----------------------
	public void clickOnElement(By locator, String eleText) {
		List<WebElement> langLinks = getElements(locator);
		System.out.println(langLinks.size());
		for (WebElement e : langLinks) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(eleText)) {
				e.click();
				break;
			}
		}
	}

	// WAF : capture specific attribute from the list
	public List<String> getElementAttributeList(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<>();

		for (WebElement e : eleList) {
			String attrValue = e.getDomAttribute(attrName);
			eleAttrList.add(attrValue);

		}
		return eleAttrList;
	}

	public void googleSearch(By searchField, By suggestions, String searchKey, String suggName)
			throws InterruptedException {

		doSendKeys(searchField, searchKey);
		Thread.sleep(3000);

		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}
		}
	}



	// *********** Select Drop Down Util ***********

	public Select createSelect(By locator) {
		Select select = new Select(getElement(locator));
		return select;
	}

	public void doSelectDropDownByIndex(By locator, int value) {
		createSelect(locator).selectByIndex(value);
	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		createSelect(locator).selectByVisibleText(visibleText);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		createSelect(locator).selectByValue(value);
	}

	public int getDropDownOptionCount(By locator) {
		return createSelect(locator).getOptions().size();
	}

	public List<String> getDropDownOptions(By locator) {

		List<WebElement> optinsList = createSelect(locator).getOptions();
		List<String> optionsTextList = new ArrayList<>();

		for (WebElement e : optinsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public void selectDropDownOption(By locator, String dropDownValue) {
		List<WebElement> optinsList = createSelect(locator).getOptions();
		System.out.println(optinsList.size());

		for (WebElement e : optinsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}

	public void selectDropDownValue(By locator, String value) {
		List<WebElement> optionList = getElements(locator);
		for (WebElement e : optionList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public boolean selectDropDownMultiple(By locator) {
		return createSelect(locator).isMultiple() ? true : false;
	}

	/**
	 * This method is used to select the values from the drop down. it can select;
	 * 1.Single selection 2.Multiple selection 3.All selection: please pass "all" as
	 * a value to select all values
	 *
	 * @param locator
	 * @param values
	 */
	public void selectDropDownMultipleValues(By locator, By optionLocator, String... values)
			throws InterruptedException {
		if (selectDropDownMultiple(locator)) {

			if (values[0].equalsIgnoreCase("all")) {
				List<WebElement> optionList = getElements(optionLocator);
				for (WebElement e : optionList) {
					Thread.sleep(1000);
					e.click();
				}
			} else {
				for (String value : values) {
					createSelect(locator).selectByVisibleText(value);
				}
			}

		}

	}

//	*********Action Util***********

	public Actions createAction(WebDriver driver) {
		Actions act = new Actions(driver);
		return act;
	}
	public void moveToElementMethod(By locator) {
		//Actions act = createAction(driver);
		createAction(driver).moveToElement(getElement(locator)).build().perform();

	}
	public  void doActionsSendKeys(By locator, String value) {
		//Actions act = new Actions(driver);
		createAction(driver).sendKeys(getElement(null), value);
	}
	public  void doActionsClick(By locator) {
		//Actions act = new Actions(driver);
		createAction(driver).click(getElement(locator));
	}


	public  void twoLevelMenuHandle(By parentlocator, By childLocator ) throws InterruptedException {
		//Actions act = new Actions(driver);
		createAction(driver).moveToElement(getElement(parentlocator)).perform();
		Thread.sleep(2000);
		doClick(childLocator);
	}
	public  void fourLevelMenuHandle(By parentMenuLocator,By firstChildMenuLocator,
			By secondChildMenuLocator,By thirdChildMenuLocator) throws InterruptedException {

		doClick(parentMenuLocator);
		//Actions act = createAction(driver);
		Thread.sleep(2000);
		moveToElementMethod(firstChildMenuLocator);
		//act.moveToElement(getElement(firstChildMenuLocator)).build().perform();
		Thread.sleep(2000);
		moveToElementMethod(secondChildMenuLocator);
		//act.moveToElement(getElement(secondChildMenuLocator)).build().perform();
		Thread.sleep(2000);
		doClick(thirdChildMenuLocator);
	}
	public void doActionsSendKeysWithPause(By locator,String value){
//		Actions act = new Actions(driver);
		char val[] = value.toCharArray();
		for(char c : val) {
			createAction(driver).sendKeys(getElement(locator), String.valueOf(c)).pause(100).build().perform();
		}
	}
	
	
	
//	*******************Wait Utils*************
	/**
	 * An expectation for checking that an element is present on the DOM of a page. This does not
	 *  necessarily mean that the element is visible.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public  WebElement waitForPresenceOfElement(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		  WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		  isHighlight(element);
		  return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page. This does not
	 *  necessarily mean that the element is visible.
	 * @param locator
	 * @param timeout
	 * @param intervalTime
	 * @return
	 */
	public  WebElement waitForPresenceOfElement(By locator, int timeout,int IntervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),Duration.ofSeconds(IntervalTime));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is
	 * greater than 0.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("waiting for element: {0} with timeout {1}")
	public  WebElement waitForVisibilityOfElement(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is
	 * greater than 0.
	 * @param locator
	 * @param timeout
	 * @param intervalTime
	 * @return
	 */
	public  WebElement waitForVisibilityOfElement(By locator, int timeout,int IntervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),Duration.ofSeconds(IntervalTime));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public  List<WebElement> waitForVisibilityOfElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @param intervalTime
	 * @return
	 */

	public  List<WebElement> waitForVisibilityOfElements(By locator, int timeout,int IntervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),Duration.ofNanos(IntervalTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForPresenceOfElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeout
	 * @param intervalTime
	 * @return
	 */
	public List<WebElement> waitForPresenceOfElements(By locator, int timeout,int IntervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(IntervalTime));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public void doClickWithWait(By locator, int timeout) {
		waitForVisibilityOfElement(locator, timeout).click();
	}
	public void doSendKeysWithWait(By locator,String value, int timeout){
		waitForVisibilityOfElement(locator, timeout).sendKeys(value);
	}

	public  String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println(titleFraction + " title value is not present....");
			e.printStackTrace();
		}
		return null;

	}


	public  String waitForTitleIs(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println(title + " title value is not present....");
			e.printStackTrace();
		}
		return null;

	}



	public  String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println(urlFraction + " url value is not present....");
			e.printStackTrace();
		}
		return null;

	}


	public  String waitForURLToBe(String url, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlToBe(url))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println(url + " url value is not present....");
			e.printStackTrace();
		}
		return null;

	}

	//********************Alert**********************
	public  Alert waitForJsAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	public  void acceptJSAlert(int timeOut) {
		waitForJsAlert(timeOut).accept();
	}

	public  void dismissJSAlert(int timeOut) {
		waitForJsAlert(timeOut).dismiss();
	}

	public  String getJSAlertText(int timeOut) {
		return waitForJsAlert(timeOut).getText();
	}

	public  void enterValueOnjsAlert(String value , int timeOut) {
		waitForJsAlert(timeOut).sendKeys(value);
	}

	//********************Frame**********************

	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameByIndex(String idOrName, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFrameByElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public  boolean checkNewWindowExist(int timeOut, int expectedWindows) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		if(wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindows))) {
			return true;
		}
		}catch(TimeoutException e) {
			System.out.println("Number of Windows are not same..");
		}
		return false;
	}
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		}catch(TimeoutException e) {
			System.out.println("Element is not clickable or enable..");
		}
	}

	public WebElement waitForElementWithFluentWait(By locator, int timeOut,int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("..time out is done.. element not found..")
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotInteractableException.class);

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
		
		
	}

	public void waitForFrameWithFluentWait(String frameIDOrName, By locator, int timeOut,int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("..time out is done.. frame not found..")
				.ignoring(NoSuchFrameException.class);

		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
	}

	public Alert waitForAlertWithFluentWait(By locator, int timeOut,int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("..time out is done.. alert not appeared..")
				.ignoring(NoAlertPresentException.class);

		return wait.until(ExpectedConditions.alertIsPresent());
	}

	//****************CustomeWait*******************
	public  WebElement retryingElement(By locator, int timeOut) {
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = getElement(locator);
				System.out.println("element is found.." + locator + " in attempt " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found..." + locator + "in attempt " + attempts);
				try {
					Thread.sleep(500);// default polling time
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
			attempts++;
		}
		if(element == null) {
			System.out.println("element is not found... tried for "+timeOut+"times"
					+" with the interval of "+500+" milli seconds ");
		throw new FrameworkException("No Such Element");
		}
		isHighlight(element);
		return element;
	}

	public  WebElement retryingElement(By locator, int timeOut,int intervalTime) {
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = getElement(locator);
				System.out.println("element is found.." + locator + " in attempt " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found..." + locator + "in attempt " + attempts);
				try {
					Thread.sleep(intervalTime);// default polling time
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
			attempts++;
		}
		if(element == null) {
			System.out.println("element is not found... tried for "+timeOut+"times"
					+" with the interval of "+500+" milli seconds ");
		throw new FrameworkException("No Such Element");
		}
		isHighlight(element);
		return element;
	}

	public boolean isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'")).toString();
		return Boolean.parseBoolean(flag);
	}





}
