package openTapsTestNG;
import org.testng.annotations.*;
public class CreateLeadEnh extends OpenTapsTest
{
	String leadId = "";
	String leadList[] = new String[20];
	String username = "";
	String password = "";
	int i,j;
	int inputCount = 0;
	String workBook = "D:\\NewMavenPro\\SelMarch\\data\\OpenTapsEnh.xlsx";
	static int index = 1;
	
	@BeforeMethod
	public void start()
	{
		launchBrower("firefox");
		login(username, password);
		openCRM();
		openLeads();
		createLeads();
	}
	
	@Test(dataProvider="pullInputData")
	public void enterData(String companyName,
						String firstName,
						String lastName,
						String sourceIndex,
						String marketIndex,
						String phoneNum,
						String emailId
						)
	{
		
		textEnter("id", "createLeadForm_companyName", companyName);
		textEnter("id", "createLeadForm_firstName", firstName);
		textEnter("id", "createLeadForm_lastName", lastName);
		selectDropDown("id", "createLeadForm_dataSourceId", "index", sourceIndex);
		selectDropDown("id", "createLeadForm_marketingCampaignId", "index", marketIndex);
		textEnter("id", "createLeadForm_primaryPhoneNumber", phoneNum);
		textEnter("id", "createLeadForm_primaryEmail", emailId);
		
		clickByElement("classname", "smallSubmit");
		if(verifyTitle("View Lead | opentaps CRM"))
		{
			System.out.println("Lead Created Successfully");
			leadId = getText("id", "viewLead_companyName_sp");
			leadId = leadId.substring(leadId.indexOf("(")+1, leadId.length()-1);
			System.out.println("Lead Id "+leadId);
			leadList[index] = leadId;
			System.out.println(index + " "+ leadList[index]);
			index++;
		}
		else
		{
			System.out.println("Unable to Create Lead");
		}
	}
	
	@BeforeClass
	public void pullExcelLoginData()
	{
		openExistingWorkBook(workBook, "Login");
		username = getData(1, 0);
		password = getData(1, 1);
		closeWorkBook();
	}
	
	@AfterMethod
	public void closeAndExit()
	{
		closeBrowser();
	}
	
	@AfterClass
	public void saveAndClose()
	{
		appendSheet(workBook, "CreateLead");
		while(index>0)
		{
			putData(index, 7, leadList[index]);
			index--;
		}
		closeWorkBook();
	}
	
	@DataProvider
	public Object[][] pullInputData()
	{
		openExistingWorkBook(workBook, "CreateLead");
		inputCount = getLastRow();
		System.out.println("Number of Rows "+inputCount);
		Object[][] obj = new Object[inputCount][7];
		
		for(i=0;i<inputCount;i++)
		{
			for(j=0;j<7;j++)
			{
				obj[i][j] = getData(i+1, j);
			}
		}
		closeWorkBook();
		return obj;
	}
}
