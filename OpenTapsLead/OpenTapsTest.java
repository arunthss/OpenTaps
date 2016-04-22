package openTapsTestNG;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class OpenTapsTest extends MyWebWrapper
{
	String myLeadId = "";
	WebElement element = null;
	public boolean login(String username, String password)
	{
		openURL("http://demo1.opentaps.org/");
		if(verifyTitle("Opentaps Open Source ERP + CRM"))
		{
			System.out.println("Open Taps URL Opened");
			textEnter("id", "username", username);
			textEnter("id", "password", password);
			clickByElement("classname", "decorativeSubmit");
			implicitWait(10);
			
			if(verifyText("xpath", "(//div[@id='label'])[1]", "CRM/SFA"))
			{
				System.out.println("Login Successfull");
				return true;
			}
			else
			{
				System.out.println("Login Falied");
				return false;
			}
		}	
		else
		{
			System.out.println("Unable to Open Opentaps URL");
			return false;
		}		
	}
	public boolean openCRM()
	{
		try 
		{
			clickByElement("xpath", "(//div[@id='label'])[1]");
			if(verifyTitle("My Home | opentaps CRM"))
			{
				System.out.println("My Home Opened");
				return true;
			}	
			else
			{
				System.out.println("Unable to My Home Page");
				return false;
			}
		} 
		catch (NoSuchElementException e) 
		{
			System.out.println("CRM/SFA Link unavailable");
			return false;
		}
	}
	public boolean openLeads()
	{
		try
		{	if(!verifyTitle("My Home | opentaps CRM"))
			{
				System.out.println("Not in Home Page, Redirecting");
				clickByElement("xpath", "//a[@href='/opentaps/']");
				implicitWait(30);
			}
		
			clickByElement("linktext", "Leads");
			implicitWait(30);
		
			if(verifyTitle("My Leads | opentaps CRM"))
			{
				System.out.println("My Leads Opened");
				return true;
			}
			else
			{
				System.out.println("Unable to Open My Leads Opened");
				return false;
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Unable to locate Leads Page");
			return false;
		}
	}
	public boolean createLeads()
	{
		try
		{
			if(!verifyTitle("My Leads | opentaps CRM"))
			{
				System.out.println("Not in Home Page, Redirecting");
				clickByElement("xpath", "//a[@href='/opentaps/']");
				implicitWait(30);
				openCRM();
				openLeads();
			}
			clickByElement("linktext", "Create Lead");
			implicitWait(30);
			if(verifyTitle("Create Lead | opentaps CRM"))
			{
				System.out.println("Create Lead Page Opened");
				return true;
			}
			else
			{
				System.out.println("Unable to Open Create Lead Page");
				return false;
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Unable to find Create Lead Link");
			return false;
		}
	}
	public boolean openFindLeads(String searchType, String searchValue)
	{
		try
		{
			if(!verifyText("xpath", "//div[@class='x-panel-bwrap']/div/div/div/div/div[@class='x-panel-tc']/div/span", "Find by"))
			{
				System.out.println("Not in Home Page, Redirecting");
				clickByElement("xpath", "//a[@href='/opentaps/']");
				implicitWait(30);
				openCRM();
				openLeads();
			}
			
			clickByElement("linktext", "Find Leads");
			implicitWait(30);
			
			if(verifyTitle("Find Leads | opentaps CRM"))
			{
				System.out.println("Find Leads Page Opened");
				if(!fillFindData(searchType, searchValue))
					return false;
			}
			else
			{
				System.out.println("Unable to Open Find Leads Page");
				return false;
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Unable to Open Find Leads Link");
			return false;
		}
		return true;
	}
	public boolean fillFindData(String searchType, String searchValue)
	{
		try
		{
			switch(searchType.toLowerCase())
			{
				case "leadid":
				{
					textEnter("xpath", "(//div[@class='x-panel-bwrap']/div/div/div/input)[1]", searchValue);
					break;
				}
				case "firstname":
				{
					textEnter("xpath", "(//div[@class='x-panel-bwrap']/div/div/div/input)[2]", searchValue);
					break;
				}
				case "lastname":
				{
					textEnter("xpath", "(//div[@class='x-panel-bwrap']/div/div/div/input)[3]", searchValue);
					break;
				}
				case "phonenum":
				{
					clickByElement("xpath","//span[contains(text(),'Phone')]");
					textEnter("xpath", "//input[@name='phoneNumber']", searchValue);
					break;
				}
				case "emailid":
				{
					clickByElement("xpath","//span[contains(text(),'Email')]");
					textEnter("xpath", "//input[@name='emailAddress']", searchValue);
					break;
				}
				default:
				{
					System.out.println("Invalid Search Type");
					return false;
				}
			}
			
			clickByElement("xpath", "//button[contains(text(),'Find Leads')]");
			explicitWait(10);
			
			if(verifyText("xpath", "//div[@class='x-panel-bbar']/div/div", "No records to display"))
			{
				System.out.println("No Records to Display");
				return false;
			}
			else
			{
				myLeadId = getText("xpath", "//table[@class='x-grid3-row-table']/tbody/tr/td/div/a[1]");
				clickByElement("xpath", "//table[@class='x-grid3-row-table']/tbody/tr/td/div/a[1]");
				implicitWait(10);
				
				if(verifyTitle("View Lead | opentaps CRM"))
				{
					System.out.println("Records Found");
					System.out.println("View Lead Page Opeend");
					return true;
				}
				else
				{
					System.out.println("No Records Found");
					//System.out.println("Unable to Open View Lead Page");
					return false;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
			return false;
		}
	}
	
	
	public void mergeLead()
	{
		try
		{
			if(!verifyTitle("My Leads | opentaps CRM"))
			{
				System.out.println("Not in Home Page, Redirecting");
				clickByElement("xpath", "//a[@href='/opentaps/']");
				implicitWait(30);
				openCRM();
				openLeads();
			}
			
			clickByElement("linktext", "Merge Leads");
			implicitWait(30);
			
			if(verifyText("id", "sectionHeaderTitle_leads", "Merge Leads"))
			{
				System.out.println("Merge Leads Page Opened");
				
			}
			else
			{
				System.out.println("Unable to Open Merge Leads Page");
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Unable to Locate Merge Leads Page");
		}
	}	
}
