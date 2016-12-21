package AutomationFramework;

import org.testng.annotations.Test;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import Utility.ConfigReader;
import Utility.ReadExcel2;

public class Withdraw_Money {
	ConfigReader reader;
	ReadExcel2 read;
	SignIn signin;
	WebDriver driver;
	By Withdrawal_Click = By.xpath("//a[@href='WithdrawalInput.php' and text()='Withdrawal']");
	By Account_No = By.xpath("//input[@name='accountno']");
	By Account_No_error_Msg = By.xpath("//label[@id='message2']");
	By Amount = By.xpath("//input[@name='ammount']");
	By Desc = By.xpath("//input[@name='desc']");
	By Submit_Click = By.xpath("//input[@type='submit' and @name='AccSubmit']");
	By StateMent_Table = By.xpath("//table[@id='ministmt']/tbody/tr");

	@BeforeSuite
	public void initialization() {
		reader = new ConfigReader();
		read = new ReadExcel2();
		signin = new SignIn();

	}
	// *********************************************************LoginTest***************************************************

	@BeforeTest
	public void beforeTest() {
		driver = signin.setup();
		signin.Login(driver, reader.getusername(), reader.getpassword());
	}

		// *******************************************************Deposit_Click_Test**********************************

	@Test(groups = { "Withdrawal" })
	public void Deposit_Click_Test() {

		driver.findElement(Withdrawal_Click).click();
		try {
			signin.Checking_Assert(
					driver.findElement(By.xpath("//p[text()='Amount Withdrawal Form']")).getAttribute("innerHTML"),
					"Amount Withdrawal Form", "Withdrawal Form is not loaded");
		} catch (Exception e) {
		}

	}

	// ************************************************Deposit_Form_Test************************************

	@Test(enabled = true, dependsOnGroups = "Withdrawal", priority = 1)
	public void Deposit_Form_Test() {

		String account_no = "18616";
		String amount = "400";
		String desc = "Grocery";
		String Expected="Transaction details of Withdrawal for Account "+account_no;

		driver.findElement(Account_No).sendKeys(account_no);
		driver.findElement(Amount).sendKeys(amount);
		driver.findElement(Desc).sendKeys(desc);
		driver.findElement(Submit_Click).click();
		try {
			signin.Checking_Assert(
					driver.findElement(By.xpath("//p[starts-with(text(),'Transaction details of Withdrawal for Account ')]"))
							.getAttribute("innerHTML"),
					Expected, "Withdrawal Form entries are not loaded for account no :" + account_no);
		} catch (Exception e) {
		}

		// *********Not Deleting Existing file in order to retain entries 
		// this run************
		//read.Delete_Txt_File(3);

		read.write_Statement_To_TxtFile(4, "");
		read.write_Statement_To_TxtFile(4, Expected);
		read.write_Statement_To_TxtFile(4, "");
	}
	

	// ***********************write_Deposit_Amount_Log_To_TxtFile(Expected+account_no)************************************
	@Test(enabled = true, dependsOnGroups = "Withdrawal", priority = 2)
	public void write_Deposit_Amount_Log_To_TxtFile() {
		List<WebElement> labels= driver.findElements(By.xpath("//table[@id='withdraw']/tbody/tr[*]/td"));
		//int col = driver.findElements(By.xpath("//*[@id='Withdrawal']/tbody/tr[6]/td")).size();

		// *************Reading table tr/td , displaying and writingtr/td to txt file*********************

		for (int i = 1; i < labels.size()-1; i=i+2) {
					read.write_Statement_To_TxtFile(4,labels.get(i).getText());
					read.write_Statement_To_TxtFile(4,labels.get(i+1).getAttribute("innerHTML"));
					
			System.out.println();
			read.write_Statement_To_TxtFile(4, "");
		}
		read.write_Statement_To_TxtFile(4,"*********************************************************************");
	}
	// **************************************************Logout_exitBrowser****************************************************

	@AfterSuite
	public void Logout_exitBrowser() {

		signin.logOut_closeBrowser(driver);
	}
}
