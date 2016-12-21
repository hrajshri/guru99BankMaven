package AutomationFramework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Utility.ConfigReader;

public class SignIn {
	WebDriver driver = null;
	Alert alert;

	public WebDriver setup() {
		ConfigReader config = new ConfigReader();
		System.setProperty(config.getBrowser(), config.getChromePath());
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(config.getApplicationURL());
		return driver;
	}

	// ******************************************************find_Alert_Value**************************************

	public Alert find_Alert_Value(WebDriver DB) {
		this.alert = null;
		try {
			WebDriverWait wait = new WebDriverWait(DB, 2);
			alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println(alert.getText());

		} catch (Exception E) {
			// System.out.println(E.getMessage());
		}
		return alert;
	}

	// ******************************************************Login**************************************
	public void Login(WebDriver dr, String username, String password) {

		dr.findElement(By.xpath("//input[@name='uid']")).sendKeys(username);
		System.out.println(dr.findElement(By.xpath("//input[@name='uid']")).getAttribute("value"));
		dr.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		dr.findElement(By.xpath("//input[@name='btnLogin']")).click();

		// Verification of Login validation

		if (find_Alert_Value(dr) != null) {
			// System.out.println(alert.getText() + " LOGIN FAILED..");
			try {
				Checking_Assert(alert.getText(), "User or Password is not valid",
						"User cannot login , Invalid credentails..");
			} catch (Exception e) {
			} finally {
				alert.accept();
			}
		} else {
			dr.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			System.out.println(
					"LOGIN SUCESSFULL....\n" + dr.findElement(By.xpath("//*[contains(text(),'mngr51327')]")).getText()
							+ " is currentyl logged in..");
		}
	}

	// ***************************************************logOut_closeBrowser*****************************************
	public void logOut_closeBrowser(WebDriver dr) {

		try {
			if (this.alert != null) {
			} else {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath("//a[@href='Logout.php']")));
				driver.findElement(By.xpath("//a[@href='Logout.php']")).click();

				if (find_Alert_Value(dr) != null) {
					try {
						Checking_Assert(alert.getText(), "You Have Succesfully Logged Out!!",
								"User is not Logged out ...");
					} catch (Exception e) {
					} finally {
						alert.accept();
					}
					alert.accept();
				}

			}
		} catch (Exception e) {
			// System.out.println(e.getMessage()); JavascriptExecutor js =
		} finally {
			dr.close();
			dr.quit();
		}
	}

	// *****************************************************Checking_Assert**************************************
	void Checking_Assert(String Actual, String Expected, String ErrorMsg) {
		SoftAssert AL = new SoftAssert();
		Actual = Actual.toLowerCase();
		Expected = Expected.toLowerCase();
		AL.assertEquals(Actual, Expected, ErrorMsg);
		System.out.println("Actual : " + Actual + "    ,    Expected : " + Expected);

		AL.assertAll();
	}

	// ****************************************************mainTest3****************************************
	@Test
	public void mainTest3() {
		SignIn signin = new SignIn();
		WebDriver dd = signin.setup();
		signin.Login(dd, "mngr51327", "EzejEqU");
		signin.logOut_closeBrowser(dd);
	}

}
