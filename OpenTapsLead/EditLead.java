package openTapsTestNG;
import org.testng.annotations.*;

public class EditLead extends OpenTapsTest
{
	String username = "";
	String password = "";
	String workBook = "D:\\NewMavenPro\\SelMarch\\data\\OpenTapsEnh.xlsx";
	String sourceValue = "";
	String marketValue = "";
	
	String verifiedSource = "";
	String verifiedMarket = "";
	int inputCount = 0;
	int i,j;
	
	@BeforeMethod
	public void start()
	{
		launchBrower("firefox");
		login(username, password);
		openCRM();
		openLeads();
	}
	
	@Test(dataProvider="pullInputData")
	public void editPage(String type, String value, String sourceId, String marketId)
	{
		if(openFindLeads(type, value))
		{
			clickByElement("xpath", "(//div[@class='frameSectionExtra'])[2]/a[3]");
			if(verifyText("id", "sectionHeaderTitle_leads", "Edit Lead"))
			{
				System.out.println("-----------------------------------------------------------------------");
				System.out.println("Edit Lead Page Opened");
				
				selectDropDown("id", "addDataSourceForm_dataSourceId", "index", sourceId);
				
				sourceValue = getText("xpath", "//select[@id='addDataSourceForm_dataSourceId']"+"/option["+(Integer.parseInt(sourceId)+1)+"]");
				System.out.println("Source Value is "+sourceValue);
				clickByElement("xpath", "(//div[@class='fieldgroup-body'])[2]/table/tbody/tr[2]/td[2]/input");
				explicitWait(5);
				
				selectDropDown("id", "addMarketingCampaignForm_marketingCampaignId", "index", marketId);
				marketValue = getText("xpath", "//select[@id='addMarketingCampaignForm_marketingCampaignId']"+"/option["+(Integer.parseInt(marketId)+1)+"]");
				System.out.println("Market Value is "+marketValue);
				clickByElement("xpath", "(//div[@class='fieldgroup-body'])[3]/table/tbody/tr[2]/td[2]/input");
				explicitWait(5);
				
				System.out.println("-------------------------------------------------------------------------");
				
				verifiedSource = getText("xpath", "//table[@class='crmsfaListTable']/tbody/tr[2]/td/span[1]");
					System.out.println("Verify Source is "+verifiedSource);
				verifiedMarket = getText("xpath", "(//table[@class='crmsfaListTable'])[2]/tbody/tr[2]/td[1]/a");
				System.out.println("Verify Market is "+verifiedMarket);
				
				if(verifiedSource.equalsIgnoreCase(sourceValue))
				{
					System.out.println("Source Value Updated Succesffuly");
				}
				else
				{
					System.out.println("Source Value NOT Updated Succesffuly");
				}
				
				if(verifiedMarket.contains(marketValue))
				{
					System.out.println("Market Value Updated Succesffuly");
				}
				else
				{
					System.out.println("Market Value NOT Updated Succesffuly");
				}
			}
			else
			{
				System.out.println("Unable to Open Edit Lead Page");
			}
		}
		else
		{
			System.out.println("Unable to Find Lead");
		}
	}
	
	
	@DataProvider
	public Object[][] pullInputData()
	{
		openExistingWorkBook(workBook, "EditLead");
		inputCount = getLastRow();
		System.out.println("Number of Rows "+inputCount);
		Object[][] obj = new Object[inputCount][4];
		
		for(i=0;i<inputCount;i++)
		{
			for(j=0;j<4;j++)
			{
				obj[i][j] = getData(i+1, j);
			}
		}
		closeWorkBook();
		return obj;
	}
	
	
	
	@AfterMethod(enabled=false)
	public void closeAndExit()
	{
		closeBrowser();
	}
	
	@BeforeClass
	public void pullExcelLoginData()
	{
		System.out.println("Pulling Login Information");
		openExistingWorkBook(workBook, "Login");
		username = getData(1, 0);
		password = getData(1, 1);
		closeWorkBook();
	}
}
