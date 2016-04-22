package openTapsTestNG;
import org.testng.annotations.*;
public class MergeLead extends OpenTapsTest
{
	String username = "";
	String password = "";
	String workBook = "D:\\NewMavenPro\\SelMarch\\data\\OpenTapsEnh.xlsx";

	String fromId = "";
	String toId = "";
	
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
	public void mergePage(String type1, String value1, String type2, String value2)
	{
		mergeLead();
		
		getParentWindow();
		
		clickByElement("xpath", "//input[@id='partyIdFrom']/following::a");
		explicitWait(10);
		switchToLastWindow();
		fillFindData(type1, value1);
		closeBrowser();
		switchToParentWindow();
		fromId = myLeadId;
		
		clickByElement("xpath", "//input[@id='partyIdTo']/following::a");
		explicitWait(5);
		switchToLastWindow();
		fillFindData(type2, value2);
		closeBrowser();
		switchToParentWindow();
		toId = myLeadId;
		
		System.out.println("From Id "+fromId);
		System.out.println("To Id "+toId);
		
		if(fromId!="" && toId!="")
		{
			clickByElement("classname", "buttonDangerous");
			acceptAlert();
		}
		else
		{
			System.out.println("Invalid from or to Id");
		}
		
		if(!openFindLeads("leadid", fromId))
		{
			System.out.println("Merge Successful");
		}
		else
		{
			System.out.println("Merge Unsuccessful");
		}
		
	}
	@DataProvider
	public Object[][] pullInputData()
	{
		openExistingWorkBook(workBook, "MergeLead");
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