package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcel {
	ConfigReader reader;
	 File src;
	 FileInputStream fis;
	 XSSFWorkbook wb;
	 XSSFSheet sheetNo;
	 Object[][] OO;
	 FileOutputStream fout;
	static final int COLcustname=0,COLgender=1,COLdob=2,COLaddress=3,COLcity=4,COLstate=5,COLpin=6,COLmob=7,COLemail=8,COLpassword=9,COLerrorMsg=10;

	public void setUpExcelFile() {
		reader = new ConfigReader();
		
		try {
			src = new File(reader.getExcelFilePath());
			fis = new FileInputStream(src);
			wb = new XSSFWorkbook(src);
			} 
			catch (Exception e) {
					System.out.println(e.getMessage());
			}
			sheetNo = wb.getSheetAt(0);
		}

	public int getRowCount() {
		return (sheetNo.getLastRowNum());
	}

	public Object[][] getData() {
		int row = getRowCount();
		OO = new Object[row][1];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j <1; j++) {
				OO[i][j] = sheetNo.getRow(i+1).getCell(j);
				
			}

		}
		return OO;
	}
	
	public void displayData(Object[][] ob) {
		int row = getRowCount();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j <1; j++) {
				if(ob[i][j]==null)
					System.out.print("            :  ");
				
				else System.out.print(ob[i][j].toString()+"  :  ");
			}
			System.out.println("\n");
		}
	}
	
	/*public static void writeAllMsgInExcel(Object[][] ob,int columnName,String value,String errorMsg)
	{
		for (int i = 0; i < row; i++) {
			for (int j = 0; j <columnName; j++) {
				if()
			}
			System.out.println("\n");
		}
		
		sheetNo.getRow(i).createCell(10).setCellValue(errorMsg);
		i=i+1;
		try {
			fout=new FileOutputStream(src);
			wb.write(fout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/

	public static void main(String[] args) {
		
			ReadExcel read = new ReadExcel();
			read.setUpExcelFile();
			Object[][] obj=read.getData();
			read.displayData(obj);
			//ReadExcel.writeAllMsgInExcel(obj,COLcustname,"Haridwar#","special Chars not allowed");
	}

	

}
