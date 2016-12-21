package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;

public class ConfigReader {
	Properties prop = null;

	public ConfigReader() {

		try {
			prop = new Properties();
			File src = new File("./Configuration/Config.property");
			FileInputStream fis = new FileInputStream(src);
			prop.load(fis);

		} catch (Exception e) {
			System.out.println("Exceptoin is :" + e.getMessage());
			e.printStackTrace();
		}
	}

	public String getBrowser() {
		return prop.getProperty("setBrowser");
	}

	public String getChromePath() {
		return prop.getProperty("chromePath");
	}

	public String getIEPath() {
		return prop.getProperty("IEPath");
	}

	public String getApplicationURL() {
		return prop.getProperty("URL");
	}

	public String getusername() {
		return prop.getProperty("username");
	}

	public String getpassword() {
		return prop.getProperty("password");
	}

	public String getExcelFilePath() {
		return prop.getProperty("testDataInExcel");
	}

	public String getTxtFilePath() {
		return prop.getProperty("errorMsgInTxtFile");
	}
	
	public String getCustID_CustName_filePath() {
		return prop.getProperty("CustID_CustName");
	}
	
	public String getMiniStatement_filePath() {
		return prop.getProperty("Mini_Statement");
	}
	
	public WebDriver startBrowser() {
		WebDriver dr;
		System.setProperty(getBrowser(), getChromePath());
		dr = new ChromeDriver();
		dr.get(getApplicationURL());
		System.out.println(getMiniStatement_filePath());
		dr.manage().window().maximize();
		return dr;
	}
	public static void main(String[] arg)
	{
		ConfigReader reader=new ConfigReader();
		System.out.println(reader.getMiniStatement_filePath());
		System.out.println(reader.getExcelFilePath());
	}
}
