package AutomationFramework;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utility.ConfigReader;
import Utility.ReadExcel2;

public class Customised_Statement {

	SignIn signin;
	ReadExcel2 read;
	ConfigReader reader;
	WebDriver driver;
	By Cust_Statement_Click = By.xpath("//a[@href='CustomisedStatementInput.php' and text()='Customised Statement']");
	By Account_No = By.xpath("//input[@name='accountno']");
	By Account_No_error_Msg = By.xpath("//label[@id='message2']");
	By From_Date = By.xpath("//input[@name='fdate']");
	By To_Date = By.xpath("//input[@name='tdate']");
	By Submit_Click = By.xpath("//input[@type='submit' and @name='AccSubmit']");
	By StateMent_Table = By.xpath("//table[@id='ministmt']/tbody/tr");

	@BeforeSuite
	public void startBrowser() {
		read = new ReadExcel2();
		reader = new ConfigReader();
		signin = new SignIn();
		driver = signin.setup();
	}
	// *********************************************************LoginTest***************************************************

	@BeforeTest
	public void LoginTest() {
		signin.Login(driver, reader.getusername(), reader.getpassword());

	}
	// *******************************************************Customised_Statement_Click_Test**********************************

	@Test(groups = { "custom" })
	public void Customised_Statement_Click_Test() {

		driver.findElement(Cust_Statement_Click).click();
		try {
			signin.Checking_Assert(
					driver.findElement(By.xpath("//p[text()='Customized Statement Form']")).getAttribute("innerHTML"),
					"Customized Statement Form", "Customised Statement Form is not loaded");
		} catch (Exception e) {
		}

	}

	// ************************************************Customised_Statement_Get_Entries_Form_Test************************************

	@Test(enabled = true, dependsOnGroups = "custom", priority = 1)
	public void Customised_Statement_Get_Entries_Form_Test() {

		String From = "10/01/2016";
		String To = "10/31/2016";
		String date1 = null, date2 = null;
		try {
			DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
			Date date = userDateFormat.parse(From);
			date1 = dateFormatNeeded.format(date);
			date = userDateFormat.parse(To);
			date2 = dateFormatNeeded.format(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String account_no = "18616";
		String Expected = "Transaction Details for Account No: " + account_no + " from Date: " + date1 + " to: "
				+ date2;

		driver.findElement(Account_No).sendKeys(account_no);
		driver.findElement(From_Date).sendKeys(From);
		driver.findElement(To_Date).sendKeys(To);
		driver.findElement(Submit_Click).click();
		try {
			signin.Checking_Assert(
					driver.findElement(By.xpath("//p[starts-with(text(),'Transaction Details for Account No: ')]"))
							.getAttribute("innerHTML"),
					Expected, "Customised Statement entries are not loaded for account no :" + account_no);
		} catch (Exception e) {
		}

		// *********Deleting Existing file in order to get fresh entries only  this run************
		read.Delete_Txt_File(2);

		read.write_Statement_To_TxtFile(2, "");
		read.write_Statement_To_TxtFile(2, Expected);
		read.write_Statement_To_TxtFile(2, "");
	}

	// ***********************read.write_Customised_Statement_To_TxtFile(Expected+account_no)************************************
	@Test(enabled = true, dependsOnGroups = "custom", priority = 2)
	public void Customised_Statement_Get_Entries_Test() {
		int row = driver.findElements(By.xpath("//table[@id='customstmt']/tbody/tr[*]")).size();
		int col = driver.findElements(By.xpath("//table[@id='customstmt']/tbody/tr[2]/td")).size();

		// *************Reading table tr/td , displaying and writingtr/td to txt
		// file*********************

		for (int i = 1; i < row; i++) {
			for (int j = 1; j <= col; j++) {
				if (i == 1)
					read.write_Statement_To_TxtFile(2,
							driver.findElement(By.xpath("//table[@id='customstmt']/tbody/tr[" + i + "]/th[" + j + "]"))
									.getAttribute("innerHTML"));
				else
					read.write_Statement_To_TxtFile(2,
							driver.findElement(By.xpath("//table[@id='customstmt']/tbody/tr[" + i + "]/td[" + j + "]"))
									.getAttribute("innerHTML"));
			}
			System.out.println();
			read.write_Statement_To_TxtFile(2, "");
		}
	}
	// **************************************************Logout_exitBrowser****************************************************

	@AfterSuite
	public void Logout_exitBrowser() {

		signin.logOut_closeBrowser(driver);
	}
}
