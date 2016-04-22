package openTapsTestNG;
import org.testng.annotations.*;
public class DeleteLead extends OpenTapsTest
{
	String username = "";
	String password = "";
	String workBook = "D:\\NewMavenPro\\SelMarch\\data\\OpenTapsEnh.xlsx";
	String leadId = "";
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
	public void editPage(String type, String value)
	{
		if(openFindLeads(type, value))
		{
			leadId = getText("id", "viewLead_companyName_sp");
			leadId = leadId.substring(leadId.indexOf("(")+1, leadId.length()-1);
			System.out.println("Lead Id "+leadId);
			clickByElement("xpath", "(//div[@class='frameSectionExtra'])[2]/a[4]");
			explicitWait(5);
			
			System.out.println("Searching with Lead Id "+leadId);
			if(!openFindLeads("leadid", leadId))
			{
				System.out.println("Lead "+value + " Deleted");
			}
			else
			{
				System.out.println("Unable to Delete");
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
		openExistingWorkBook(workBook, "DeleteLead");
		inputCount = getLastRow();
		System.out.println("Number of Rows "+inputCount);
		Object[][] obj = new Object[inputCount][2];
		
		for(i=0;i<inputCount;i++)
		{
			for(j=0;j<2;j++)
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
