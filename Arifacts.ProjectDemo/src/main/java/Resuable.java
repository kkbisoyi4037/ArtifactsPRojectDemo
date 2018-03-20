




import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import TestOne.TestOne.AppTest;



import com.relevantcodes.extentreports.LogStatus;
import com.sun.java.util.*;



public class Resuable {
	public static WebDriver driver;
	private static String homeWindow = null;
	String browser;
	public static WebDriver driverInit(String browser) {
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\33421\\workspace\\Test1\\Lib\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "C:\\Users\\33421\\workspace\\Test1\\Lib\\IEDriverServer.exe");
			DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			driver = new InternetExplorerDriver(capability);
			break;
		case "ff":
			driver = new FirefoxDriver();
			break;
		default:
			break;
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}
	
//	public static WebDriver authentication_Alert(String username, String password) {
//		try {
//			Alert alert = driver.switchTo().alert();
//			alert.authenticateUsing(new UserAndPassword(username, password));
//			AppTest.logger.log(LogStatus.PASS, "Authentication alert handle", "Handled the authentication alert successfuly");
//		} catch (Exception e) {
//			AppTest.logger.log(LogStatus.FAIL, "Authentication alert handle failed", e.toString());
//		}
//		return driver;
//	}


	

	/**
	 * Method to switch to the newly opened window
	 */
	public static void switchToWindow() {
		homeWindow = driver.getWindowHandle();
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
		}
	}

	/**
	 * To navigate to the main window from child window
	 */
	public static void switchToMainWindow() {
		for (String window : driver.getWindowHandles()) {
			if (!window.equals(homeWindow)) {
				driver.switchTo().window(window);
				driver.close();
			}
			driver.switchTo().window(homeWindow);
		}
	}

	/**
	 * This method returns the no.of windows present
	 * 
	 * @return
	 */
	public static int getWindowCount() {
		return driver.getWindowHandles().size();
	}

	/****************** frames *********************/

//	public static void frames(WebElement frameElement) {
//		try {
//			driver.switchTo().frame(frameElement);
//		} catch (Exception e) {
//			AppTest.logger.log(LogStatus.FAIL, e.toString(), "failed while swirching to frame");
//		}
//	}

	public static void switchToDefaultcontent() {
		try {
			driver.switchTo().defaultContent();
		} catch (NoSuchElementException e) {
		}
	}

	public static void navigateToUrl(String url) {
		try {
			driver.navigate().to(url);
			AppTest.logger.log(LogStatus.PASS, "Application launch", "Application launched successfully");

		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(), "Failed to load the url");
		}
	}

	public static void closeBrowser() {
		try {
			driver.close();
			AppTest.logger.log(LogStatus.PASS, "Close browser", "Browser closed successfully");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(), "Browser is not closed");
		}
	}
	
	public static void setText(WebElement element, String value) {
		try {
			//waitForElementVisibility(element);
			element.clear();
			element.sendKeys(value);
			AppTest.logger.log(LogStatus.PASS, "Enter value in to textbox",
					value + " entered into" +  "textbox successfully");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(),
					value + " entered into" + "textbox failed");
		}
	}

	
	/**
	 * Verifying the visibility of element only for assert conditions
	 */

	public static boolean isElementPresent(WebElement element) {
		boolean elementPresent = false;
		try {
			waitForElementVisibility(element);
			if (element.isDisplayed()) {
				elementPresent = true;
			}
			AppTest.logger.log(LogStatus.PASS, "Verify Element Present",
					"is Displayed successfully");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, "Verify Element Present failed", e.toString());
		}
		return elementPresent;
	}

	/**
	 * Verifying the visibility of element only for assert conditions
	 */

	public static boolean isElementNotPresent(WebElement element) {
		boolean elementNotPresent = true;
		try {
			if (element.isDisplayed()) {
				elementNotPresent = false;
			}
			AppTest.logger.log(LogStatus.PASS, "Verify Element Present", "Element is Displayed successfully");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, "Verify Element Present failed", e.toString());
		}
		return elementNotPresent;
	}

	/**
	 * Method to click the element
	 * 
	 * @param element
	 */
	public static void click(WebElement element) {
		try {
			waitForElementVisibility(element);
			element.click();
			AppTest.logger.log(LogStatus.PASS, "click element", "element is clicked successfully");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(), "element is not clicked");
		}
	}

	/******************
	 * getting the text from non editable field
	 *********************/

	public static String getText(WebElement element) {
		String text = null;
		try {
			waitForElementVisibility(element);
			if (element.getText() != null) {
				text = element.getText();
			}
			AppTest.logger.log(LogStatus.PASS, "get link text of element", "link text retrieved successfully from element");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(), "link text is not retrieved from element");
		}
		return text;
	}

	/**
	 * Method to get the value from textbox
	 * 
	 * @param element
	 * @return
	 */
	public static String getValue(WebElement element) {
		String value = null;
		try {
			waitForElementVisibility(element);
			if (element.getAttribute("value") != null) {
				value = element.getAttribute("value");
			}
			AppTest.logger.log(LogStatus.PASS, "get the value from textbox", "value retrieved from textbox successfuly");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(), "value is not retrieved from textbox");
		}
		return value;
	}

	/**
	 * Method to select the option from dropdown by value
	 */
	public static void selectByValue(WebElement element, String value) {
		try {
			Select obj_select = new Select(element);
			obj_select.selectByValue(value);
			AppTest.logger.log(LogStatus.PASS, "select the option from dropdown", value + "selected from dropdown ");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(),
					value + "is not selected from dropdown");
		}
	}

	/**
	 * Method to select the option from dropdown by visible text
	 */
	public static void selectByText(WebElement element, String text) {
		try {
			Select obj_select = new Select(element);
			obj_select.selectByVisibleText(text);
			AppTest.logger.log(LogStatus.PASS, "select the option from dropdown",
					text + "Option selected from successfully");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(),
					text + "Option is not selected from ");
		}
	}

	/**
	 * Method to select the option from dropdown by index
	 */
	public static void selectByIndex(WebElement element, int index) {
		try {
			Select obj_select = new Select(element);
			obj_select.selectByIndex(index);
			AppTest.logger.log(LogStatus.PASS, "select the option from dropdown", "Option selected from dropdown successfully");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(), "Option is not selected from dropdown");
		}
	}


	/**
	 * To pause execution until get expected elements visibility
	 * 
	 * @param element
	 */
	public static void waitForElementVisibility(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitForElementClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitForElementClickableone(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	/**
	 * To pause the execution @throws
	 */
	public static void pause(int milliSeconds) throws InterruptedException {
		Thread.sleep(milliSeconds);
	}

	
	/**
	 * Method to get the available option from dropdown
	 * 
	 * @return
	 */
	public static List<String> getOptionFromDropDown(WebElement element) {
		List<String> AvailableOptions = new ArrayList<String>();
		try {

			Select obj_select = new Select(element);
			List<WebElement> optionElements = obj_select.getOptions();
			for (int i = 0; i < optionElements.size(); i++) {
				AvailableOptions.add(optionElements.get(i).getText());
			}
			AppTest.logger.log(LogStatus.PASS, "get available from dropdown is success");

		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, "get available from dropdown is failed", e.toString());
		}
		return AvailableOptions;
	}

	

	public void mouseOver(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
			AppTest.logger.log(LogStatus.PASS, "Mouseover to the element",
					"Mouseover action is performed successfully for");
		} catch (Exception e) {
			AppTest.logger.log(LogStatus.FAIL, e.toString(), "Mouseover action is not perforemed for");
		}
	}
	
	
	public static void sliderMove(WebElement element,int size){
		    int width=element.getSize().getWidth();
		    Actions move = new Actions(driver);
		    try {
				move.moveToElement(element, ((width*size)/100), 0).click();
				move.build().perform();
				AppTest.logger.log(LogStatus.PASS, "Mouseover to the Slider",
						"Slider moved successfully");
			} catch (Exception e) {
				AppTest.logger.log(LogStatus.FAIL, "User is not able to move Slider",
						"Slider is not moved");
				
			}
		   
		}

	public String screenCapture(String imgLocation) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(imgLocation));
			AppTest.logger.log(LogStatus.PASS, "Screen capture",
					"Captured screen successfully");
		} catch (IOException e) {
			AppTest.logger.log(LogStatus.PASS, e.toString(), "Screen is not captured");
			}
		return imgLocation;
	}
	
	 public static String capture(WebDriver driver,String screenShotName) throws IOException
	    {
	        String dest="";
			try {
				TakesScreenshot ts = (TakesScreenshot)driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
				dest = System.getProperty("user.dir") +"\\ErrorScreenshots\\"+screenShotName+".png";
				File destination = new File(dest);
				FileUtils.copyFile(source, destination);
				AppTest.logger.log(LogStatus.PASS, "Screen capture",
						"Captured screen successfully");
			} catch (WebDriverException e) {
				AppTest.logger.log(LogStatus.PASS, e.toString(), "Screen is not captured");
			}        
	                     
	        return dest;
	    }

}

