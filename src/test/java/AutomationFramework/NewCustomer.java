package AutomationFramework;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import Utility.ConfigReader;
import Utility.ReadExcel2;

public class NewCustomer {
	SignIn signin;
	WebDriver driver;
	ConfigReader reader;
	Object[][] obj;
	ReadExcel2 read;
	SoftAssert softAssert;
	public static Robot robot;
	By Customer_Name = By.xpath("//input[@name='name']");
	By Date_of_Birth = By.xpath("//input[@id='dob']");
	By Address = By.xpath("//textarea[@name='addr']");
	By City = By.xpath("//input[@name='city']");
	By State = By.xpath("//input[@name='state']");
	By Pin = By.xpath("//input[@name='pinno']");
	By Mobile = By.xpath("//input[@name='telephoneno']");
	By Email = By.xpath("//input[@name='emailid']");
	By Passowrd = By.xpath("//input[@name='password']");
	By Submit = By.xpath("//input[@type='submit' and @name='sub']");
	By Created_CustID = By.xpath("//*[@id='customer']/tbody/tr[4]/td[2]");
	By Created_CustName = By.xpath("//*[@id='customer']/tbody/tr[5]/td[2]");
	By reset = By.xpath("//input[@type='reset' and @name='res']");

	public static void use_Tab_fucntion() {
		if (robot == null)
			try {
				robot = new Robot();
			} catch (AWTException e) {
				System.out.println(e.getMessage());
			}
		robot.keyPress(KeyEvent.VK_TAB);
		// return dr;
	}

	public static void use_Clear_fucntion(By ele, WebDriver dr) {
		dr.findElement(ele).clear();
	}

	// ************************************************************************************************************

	@BeforeSuite
	public void startBrowser() {
		
		reader = new ConfigReader();
		signin = new SignIn();
		driver = signin.setup();
	}
	// ************************************************************************************************************

	@BeforeTest
	public void LoginTest() {
				
		signin.Login(driver, reader.getusername(), reader.getpassword());
	}
	// ************************************************************************************************************

	@DataProvider(name = "Exceldata")
	public Object[][] testDataInExcel() {
		read = new ReadExcel2();
		read.setUpExcelFile();
		obj = read.getData();
		return obj;
	}

	@SuppressWarnings("unused")
	public void Feild_Value(Object value, By element, WebDriver dr) {

		if (value == null)
			driver.findElement(element).sendKeys("");
		else
			driver.findElement(element).sendKeys(value.toString());
	}

	// **********************************************New_Customer_Resgistration**************************************************************

	@Test(dependsOnGroups = { "New_Customer_TAB" }, dataProvider = "Exceldata", groups = { "cc" }, enabled = true)
	public void New_Customer_Resgistration(Object custname, Object gender, Object DOB, Object address, Object city,
			Object state, Object pin, Object mob, Object email, Object password) {
		{
			NewCustomer.use_Clear_fucntion(Customer_Name, driver);
			this.Feild_Value(custname.toString(), Customer_Name, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLcustname),
					driver.findElement(Customer_Name).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message']")).getAttribute("innerHTML"));

			if (gender.toString().equalsIgnoreCase("male"))
				driver.findElement(By.xpath("//input[@type='radio' and @value='m']")).click();
			else
				driver.findElement(By.xpath("//input[@type='radio' and @value='f']")).click();

			this.Feild_Value(DOB.toString(), Date_of_Birth, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLdob),
					driver.findElement(Date_of_Birth).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message24']")).getAttribute("innerHTML"));

			NewCustomer.use_Clear_fucntion(Address, driver);
			this.Feild_Value(address, Address, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLaddress),
					driver.findElement(Address).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message3']")).getAttribute("innerHTML"));

			NewCustomer.use_Clear_fucntion(City, driver);
			this.Feild_Value(city, City, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLcity),
					driver.findElement(City).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message4']")).getAttribute("innerHTML"));

			NewCustomer.use_Clear_fucntion(State, driver);
			this.Feild_Value(state, State, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLstate),
					driver.findElement(State).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message5']")).getAttribute("innerHTML"));

			NewCustomer.use_Clear_fucntion(Pin, driver);
			this.Feild_Value(pin, Pin, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLpin),
					driver.findElement(Pin).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message6']")).getAttribute("innerHTML"));

			NewCustomer.use_Clear_fucntion(Mobile, driver);
			this.Feild_Value(mob, Mobile, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLmob),
					driver.findElement(Mobile).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message7']")).getAttribute("innerHTML"));

			NewCustomer.use_Clear_fucntion(Email, driver);
			this.Feild_Value(email, Email, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLemail),
					driver.findElement(Email).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message9']")).getAttribute("innerHTML"));

			NewCustomer.use_Clear_fucntion(Passowrd, driver);
			this.Feild_Value(password, Passowrd, driver);
			use_Tab_fucntion();
			read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLpassword),
					driver.findElement(Passowrd).getAttribute("value"),
					driver.findElement(By.xpath("//label[@id='message18']")).getAttribute("innerHTML"));

			driver.findElement(Submit).click();
			//
			// Alert alert = signin.find_Alert_Value(driver);
			/*
			 * try { WebDriverWait wait = new WebDriverWait(driver, 2); alert =
			 * wait.until(ExpectedConditions.alertIsPresent());
			 * driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			 * 
			 * } catch (Exception E) { // System.out.println(E.getMessage()); }
			 */
			if (signin.find_Alert_Value(driver) != null) {

				// System.out.println(signin.alert.getText() );
				try {
					if (signin.alert.getText().equalsIgnoreCase("Email Address Already Exist !!"))
						signin.Checking_Assert(signin.alert.getText(), "Email Address Already Exist1 !!",
								"Application has not prompted for Existing Email ID on Add New Customer Form");
					else
						signin.Checking_Assert(signin.alert.getText(), "please fill all fields",
								"Application has not prompted for Incomplete Fields on Add New Customer Form");
				} catch (Exception e) {

				} finally {
					signin.alert.accept();
				}

			} else {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions
						.visibilityOfAllElements(driver.findElements(By.xpath("//table[@id='customer']/tbody/tr[*]"))));
				read.write_CustID_CustName_TxtFile(driver.findElement(Created_CustID).getAttribute("innerHTML"),
						driver.findElement(Created_CustName).getAttribute("innerHTML"));
				driver.findElement(By.xpath(".//a[@href='Managerhomepage.php' and text()='Continue']")).click();
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				NewCustomerTab_ClickTest();
				softAssert.assertAll();
			}

		}
	}
	// **********************************************NewCustomerTab_ClickTest**************************************************************

	@Test(groups = { "New_Customer_TAB" }, enabled = true)
	public void NewCustomerTab_ClickTest() {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//li/a[@href='addcustomerpage.php']")).click();
		softAssert = new SoftAssert();
		System.out.println(driver.findElement(By.xpath("//p[text()='Add New Customer']")).getAttribute("innerHTML"));
		String actual = driver.findElement(By.xpath("//p[text()='Add New Customer']")).getAttribute("innerHTML");
		softAssert.assertEquals(actual, "Add New Customer", "Failed : Add New Customer form not loaded...");
		softAssert.assertAll();

	}
	// *********************************************Customer_Name_Field_ValueTest***************************************************************

	@Test(dependsOnMethods = "NewCustomerTab_ClickTest", dataProvider = "Exceldata", groups = { "cc" }, enabled = false)
	public void Customer_Name_Field_ValueTest(Object custname, Object gender, Object DOB, Object address, Object city,
			Object state, Object pin, Object mob, Object email, Object passowrd) {

		NewCustomerTab_ClickTest();
		NewCustomer.use_Clear_fucntion(Customer_Name, driver);

		this.Feild_Value(custname, Customer_Name, driver);
		use_Tab_fucntion();
		read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLcustname),
				driver.findElement(Customer_Name).getAttribute("value"),
				driver.findElement(By.xpath("//label[@id='message']")).getAttribute("innerHTML"));

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		softAssert.assertAll();
	}

	// *************************************************DOB_ValueTest***********************************************************

	@Test(dependsOnMethods = "NewCustomerTab_ClickTest", dataProvider = "Exceldata", groups = { "cc" }, enabled = false)
	public void DOB_ValueTest(Object custname, Object gender, Object DOB, Object address, Object city, Object state,
			Object pin, Object mob, Object email, Object passowrd) {

		NewCustomerTab_ClickTest();
		NewCustomer.use_Tab_fucntion();
		if (Date_of_Birth == null)
			driver.findElement(Date_of_Birth).sendKeys("");

		else
			driver.findElement(Date_of_Birth).sendKeys(DOB.toString());

		NewCustomer.use_Tab_fucntion();

		read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLdob), DOB.toString(),
				driver.findElement(By.xpath("//label[@id='message24']")).getAttribute("innerHTML"));

		// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		softAssert.assertAll();
	}

	// ************************************************************************************************************
	@AfterSuite
	public void Logout_exitBrowser() {

		signin.logOut_closeBrowser(driver);
	}
}
