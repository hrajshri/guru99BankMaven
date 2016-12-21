package AutomationFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utility.ConfigReader;
import Utility.ReadExcel2;

public class MiniStatement {

	SignIn signin;
	ReadExcel2 read;
	ConfigReader reader;
	WebDriver driver;
	By MiniStament_Click = By.xpath("//a[@href='MiniStatementInput.php' and text()='Mini Statement']");
	By Account_No = By.xpath("//input[@name='accountno']");
	By Account_No_error_Msg = By.xpath("//label[@id='message2']");
	By Submit_Click = By.xpath("//input[@name='AccSubmit' and @type='submit']");
	By StateMent_Table = By.xpath("//table[@id='ministmt']/tbody/tr");

	@BeforeSuite
	public void startBrowser() {
		reader = new ConfigReader();
		signin = new SignIn();
		driver = signin.setup();
	}
	// *********************************************************LoginTest***************************************************

	@BeforeTest
	public void LoginTest() {
		signin.Login(driver, reader.getusername(), reader.getpassword());
	}
	// *******************************************************MiniStatement_Click_Test*********************************************

	@Test
	public void MiniStatement_Click_Test() {

		driver.findElement(MiniStament_Click).click();
		try {
			signin.Checking_Assert(
					driver.findElement(By.xpath("//p[text()='Mini Statement Form']")).getAttribute("innerHTML"),
					"Mini Statement Form", "Mini Statement Form is not loaded");
		} catch (Exception e) {
		}
	}

	// ************************************************MiniStatement_Get_Entries_Form_Test****************************************************

	@Test(enabled = true, dependsOnMethods = "MiniStatement_Click_Test")
	public void MiniStatement_Get_Entries_Form_Test() {

		read = new ReadExcel2();
		String Expected = "Last Five Transaction Details for Account No: ";
		String account_no = "18616";
		driver.findElement(MiniStament_Click).click();
		driver.findElement(Account_No).sendKeys(account_no);
		driver.findElement(Submit_Click).click();
		try {
			signin.Checking_Assert(
					driver.findElement(By.xpath("//p[starts-with(text(),'Last Five Transaction ')]"))
							.getAttribute("innerHTML"),
					Expected + account_no, "Mini Statement entries are not loaded for account no :" + account_no);
		} catch (Exception e) {
		}
		
		//*********Deleting Existing file in order to get fresh entries only this run************
		read.Delete_Txt_File(1);
		
		read.write_Statement_To_TxtFile(1, "");
		read.write_Statement_To_TxtFile(1, Expected + account_no);
		read.write_Statement_To_TxtFile(1, "");
	}

	// ***********************read.write_Statement_To_TxtFile(Expected
	// +account_no)************************************
	@Test(enabled = true, dependsOnMethods = "MiniStatement_Click_Test")
	public void MiniStatement_Get_Entries_Test() {
		int row = driver.findElements(By.xpath("//table[@id='ministmt']/tbody/tr[*]")).size();
		int col = driver.findElements(By.xpath("//table[@id='ministmt']/tbody/tr[2]/td")).size();

		// *********************Reading table tr/td , displaying and writing
		// tr/td to txt file*********************

		for (int i = 1; i < row; i++) {
			for (int j = 1; j <= col; j++) {
				if (i == 1)
					read.write_Statement_To_TxtFile(1,
							driver.findElement(By.xpath("//table[@id='ministmt']/tbody/tr[" + i + "]/th[" + j + "]"))
									.getAttribute("innerHTML"));
				else
					read.write_Statement_To_TxtFile(1,
							driver.findElement(By.xpath("//table[@id='ministmt']/tbody/tr[" + i + "]/td[" + j + "]"))
									.getAttribute("innerHTML"));
			}
			System.out.println();
			read.write_Statement_To_TxtFile(1, "");
		}
	}
	// **************************************************Logout_exitBrowser****************************************************

	@AfterSuite
	public void Logout_exitBrowser() {

		signin.logOut_closeBrowser(driver);
	}
}
