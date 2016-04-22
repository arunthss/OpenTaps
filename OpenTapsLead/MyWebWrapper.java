package openTapsTestNG;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class MyWebWrapper extends ExcelHandler
{
	RemoteWebDriver driver;
	WebElement element;
	String parentWindow = "";
	Alert a;
	
	public boolean launchBrower(String browserName)
	{
		try
		{
			switch(browserName.toLowerCase())
			{
				case "chrome":
				{
					System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
					driver = new ChromeDriver();
					break;
				}
				case "firefox":
				default:
				{
					driver = new FirefoxDriver();
					break;
				}
			}
			driver.manage().window().maximize();
			if(driver!=null)
			{
				System.out.println(browserName + " broswer launched successfully");
				return true;
			}
				
			else
				return false;
		}
		catch(Exception e)
		{
			System.out.println("Unable to Launch "+browserName+" Broswer");
			return false;
		}
		
	}
	public void openURL(String URL)
	{
		try
		{
			driver.get(URL);
		}
		catch(Exception e)
		{
			System.out.println("Unable to Open Requested URL");
		}	
	}
	public void textEnter(String findBy, String locator, String text)
	{
		try
		{
			element = getReference(findBy, locator);
			element.clear();
			element.sendKeys(text);
		}
		catch(NullPointerException e)
		{
			System.out.println("Requested Element Unavailable");
		}
		catch(StaleElementReferenceException e) 
		{
			System.out.println("Requested Element Currently Unavailable");
		}
	}
	public void clickByElement(String findBy,String locator)
	{
		try
		{
			element = getReference(findBy, locator);
			element.click();
		}
		catch(NoSuchElementException e)
		{
			System.out.println(findBy+" "+locator+" Requested Element Unavailable");
		}
		catch(NullPointerException e)
		{
			System.out.println(findBy+" "+locator+" Requested Element Unavailable");
		}
		catch(StaleElementReferenceException e) 
		{
			System.out.println("Requested Element Currently Unavailable");
		}
	}
	public void selectDropDown(String findBy, String locator, String type, String value)
	{
		try
		{
			element = getReference(findBy, locator);
			Select mySelect = new Select(element);
			
			switch(type)
			{
				case "index":
				{
					mySelect.selectByIndex(Integer.parseInt(value));
					break;
				}
				case "value":
				{
					mySelect.selectByValue(value);
					break;
				}
				case "text":
				{
					mySelect.selectByVisibleText(value);
					break;
				}
				default:
				{
					System.out.println("Invalid Selector Type");
					break;
				}
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Dropdown unavailable");
		}
	}
	public boolean verifyText(String findBy, String locator, String expected)
	{
		try
		{
			element = getReference(findBy, locator);
				System.out.println("Selected Item is "+element.getText());
			if(element.getText().trim().equalsIgnoreCase(expected))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(NullPointerException e)
		{
			System.out.println(findBy+" "+locator+" Requested Element Unavailable");
			return false;
		}
		catch(StaleElementReferenceException e) 
		{
			System.out.println("Requested Element Currently Unavailable");
			return false;
		}
		
		
		
	}
	public boolean verifyTitle(String expected)
	{
		try
		{
			if(driver.getTitle().equalsIgnoreCase(expected))
			{
				//System.out.println(expected +" Title's Match");
				return true;
			}
			else
			{
				System.out.println(expected+" Title's Did NOT Match");
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
			return false;
		}
	}
	public void implicitWait(int seconds)
	{
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}	
	public void explicitWait(int seconds)
	{
		try
		{
			Thread.sleep(seconds);
		}
		catch(InterruptedException e)
		{
			System.out.println("Unable to make thread wait");
		}
	}
	private WebElement getReference(String findBy, String locator)
	{
		try
		{
			switch(findBy.toLowerCase())
			{
				case "id":
				{
					return driver.findElementById(locator);
				}
				case "name":
				{
					return driver.findElementByName(locator);
				}
				case "classname":
				{
					return driver.findElementByClassName(locator);
				}
				case "linktext":
				{
					return driver.findElementByLinkText(locator);
				}
				case "tagname":
				{
					return driver.findElementByTagName(locator);
				}
				case "partialtext":
				{
					return driver.findElementByPartialLinkText(locator);
				}
				case "xpath":
				{
					return driver.findElementByXPath(locator);
				}
				default:
				{
					System.out.println("Invalid Finby Type");
					return null;
				}
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println(findBy+" "+locator+" Requested Element Unavailable");
			return null;
		}
	}
	public void closeBrowser()
	{
		try
		{
			driver.close();
			System.out.println("Broswer Closed");
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
	public String getText(String findBy, String locator)
	{
		try
		{
			element = getReference(findBy, locator);
			return element.getText();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Unable to Find Requested Element");
			return null;
		}
	}
	public boolean verifyElement(String findBy, String locator)
	{
		try
		{
			element = getReference(findBy, locator);
			return element.isDisplayed();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Element Not Found for verification");
			return false;
		}
		catch (StaleElementReferenceException e) {
			System.out.println("Element Currently Not Found for verification");
			return false;
		}
		catch(NullPointerException e)
		{
			System.out.println("Element Not Found for verification");
			return false;
		}
	}
	public boolean isDisplayed(String findBy, String locator)
	{
		try
		{
			element = getReference(findBy, locator);
			if(element.isDisplayed())
			{
				System.out.println("Element Found");
				return true;
			}
			else
			{
				System.out.println("Element Not Found");
				return false;
			}
		}
		catch(NullPointerException e)
		{
			System.out.println("Unable to find element "+locator);
			return false;
		}
	}
	public String getParentWindow()
	{
		try
		{
			parentWindow = driver.getWindowHandle();
			return parentWindow;
		}
		catch(Exception e)
		{
			System.out.println("Window Err");
			return null;
		}
	}
	public void switchToLastWindow()
	{
		try
		{
			Set <String> windows = driver.getWindowHandles();
			for (String window : windows) 
			{
				System.out.println(window);
				driver.switchTo().window(window);
			}
			System.out.println("Control switched to last window");
		}
		catch(Exception e)
		{
			System.out.println("Unable to Open last Window");
		}
	}
	public void switchToParentWindow()
	{
		try
		{
			driver.switchTo().window(parentWindow);
			System.out.println("Control Switched to Parent Window");
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
	public void acceptAlert()
	{
		try
		{
			a = driver.switchTo().alert();
			a.accept();
		}
		catch(Exception e)
		{
			System.out.println("Alert Exception Fired");
		}
	}
}

