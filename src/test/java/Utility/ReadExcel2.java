package Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel2 {
	ConfigReader reader;
	File src;
	FileInputStream fis;
	XSSFWorkbook wb;
	XSSFSheet sheetNo;
	Object[][] OO;
	FileOutputStream fout;
	public static final int COLcustname = 0;
	public static final int COLgender = 1;
	public static final int COLdob = 2;
	public static final int COLaddress = 3;
	public static final int COLcity = 4;
	public static final int COLstate = 5;
	public static final int COLpin = 6;
	public static final int COLmob = 7;
	public static final int COLemail = 8;
	public static final int COLpassword = 9;
	public static final int COLerrorMsg = 10;

	public String return_Excel_ColumnName(int a) {
		return sheetNo.getRow(0).getCell(a).toString();
	}

	public void setUpExcelFile() {
		reader = new ConfigReader();

		try {
			src = new File(reader.getExcelFilePath());

			fis = new FileInputStream(src);
			wb = new XSSFWorkbook(src);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sheetNo = wb.getSheetAt(0);
	}

	public int getRowCount() {
		return (sheetNo.getLastRowNum());
	}

	// ***********************************************Initialize Object //
	// Array*********************************
	public Object[][] getData() {
		int row = getRowCount();
		OO = new Object[row][10];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < 10; j++) {
				OO[i][j] = sheetNo.getRow(i + 1).getCell(j);
			}
		}
		return OO;
	}

	// ******************************************Display Object //
	// Array*************************************

	public void displayData(Object[][] ob) {
		int row = getRowCount();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < 10; j++) {
				if (ob[i][j] == null)
					System.out.print("            :  ");

				else
					System.out.print(ob[i][j].toString() + "  :  ");
			}
			System.out.println("\n");
		}
	}

	// **********************************Write Object Array to //
	// Excel********************************************

	public void write_Erros_To_Excel(Object[][] ob, int columnNo, String value, String errorMsg) throws IOException {
		int i;
		for (i = 0; i < getRowCount(); i++) {
			if (ob[i][columnNo] == null && value == null)
				break;
			if (ob[i][columnNo] == null && value != null)
				continue;

			System.out.print(" columnNo]==null && value==null :  ");

			if (ob[i][columnNo] != null) {
				String nm = ob[i][columnNo].toString();
				if (nm.equals(value))
					break;
			}
		}
		System.out.println("value of i : " + (i + 1));

		sheetNo.getRow(i + 1).createCell(10).setCellValue(errorMsg);
		fis.close();

		System.out.println("value of i : " + (i + 1));
		if (fout == null)
			fout = new FileOutputStream(src);
		wb.write(fout);
		fout.close();
		System.out.println("value of i : " + (i + 1));

		System.out.println("value of i : " + (i + 1));
	}

	// ***********************************************************************************************************

	public void write_Erros_To_TxtFile(String feildName, String value, String errorMsg) {
		System.out.println(feildName + "  :  " + value + "  :  " + errorMsg);
		// The name of the file to open.
		String fileName = reader.getTxtFilePath();

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName, true);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.newLine();
			bufferedWriter.write(
					"*********************************************************************************************");
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			bufferedWriter.write(feildName + "  :  " + value + "  :  " + errorMsg);

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}

	// *************************************************write_CustID_CustName_to_TxtFile*******
	public void write_CustID_CustName_TxtFile(String CustID, String CustName) {
		System.out.println("Customer ID       :  " + CustID + "\n" + "Customer Name     :  " + CustName);
		// The name of the file to open.
		String fileName1 = reader.getCustID_CustName_filePath();

		try {
			// Assume default encoding.
			FileWriter fileWriter1 = new FileWriter(fileName1, true);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);

			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter1.newLine();
			bufferedWriter1.write("----------------------------------------------------------------------");
			bufferedWriter1.newLine();
			bufferedWriter1.write("Customer ID       :  " + CustID);
			bufferedWriter1.newLine();
			bufferedWriter1.write("Customer Name     :  " + CustName);

			// Always close files.
			bufferedWriter1.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName1 + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}
	// *************************************************write_MiniStatement_To_TxtFile***********************************************************

	public void write_Statement_To_TxtFile(int which_Statement, String data) {

		// The name of the file to open.
		String fileName;

		if (which_Statement == 1)
			fileName = "E:\\Eclipse\\Eclipse Neon\\Eclipseworkspace\\Guru99Bank\\TestData\\MiniStatement.txt";

		else if (which_Statement == 2)
			fileName = "E:\\Eclipse\\Eclipse Neon\\Eclipseworkspace\\Guru99Bank\\TestData\\CustomisedStatement.txt";

		else if (which_Statement == 3)
			fileName = "E:\\Eclipse\\Eclipse Neon\\Eclipseworkspace\\Guru99Bank\\TestData\\Deposit.txt";
		else
			fileName = "E:\\Eclipse\\Eclipse Neon\\Eclipseworkspace\\Guru99Bank\\TestData\\Withdrawal.txt";

		// reader.getMiniStatement_filePath();
		System.out.print(data + "      ");
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			if (data.isEmpty())
				bufferedWriter.newLine();

			else if (which_Statement == 1 || which_Statement == 2) {
				if (data.equalsIgnoreCase("Transaction ID"))

					bufferedWriter.write(data + "|");

				else if (data.equalsIgnoreCase("Amount"))
					bufferedWriter.write("      " + data + "        | ");

				else if (data.equalsIgnoreCase("Transaction Type"))
					bufferedWriter.write(data + " | ");

				else if (data.equalsIgnoreCase("Date of Transaction"))
					bufferedWriter.write(data + "       |        ");

				else if (data.equalsIgnoreCase("Description"))
					bufferedWriter.write(data + "     |    ");
				else
					bufferedWriter.write(data + "         |        ");
			} else
				bufferedWriter.write(data + "         |");

			bufferedWriter.close();
		} catch (Exception e) {
			System.out.println("Error writing to file '" + fileName + "'");
			System.out.println(e.getMessage());
		}
	}

	// *************************************************Delete
	// Files************************************************
	public void Delete_Txt_File(int which_Statement) {
		String fileName;
		if (which_Statement == 1)
			fileName = "E:\\Eclipse\\Eclipse Neon\\Eclipseworkspace\\Guru99Bank\\TestData\\MiniStatement.txt";
		else if (which_Statement == 2)
			fileName = "E:\\Eclipse\\Eclipse Neon\\Eclipseworkspace\\Guru99Bank\\TestData\\CustomisedStatement.txt";
		else if (which_Statement == 3)
			fileName = "E:\\Eclipse\\Eclipse Neon\\Eclipseworkspace\\Guru99Bank\\TestData\\Deposit.txt";
		else
			fileName = "E:\\Eclipse\\Eclipse Neon\\Eclipseworkspace\\Guru99Bank\\TestData\\Withdrawal.txt";

		try {
			File file = new File(fileName);
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// *************************************************Main
	// Function*************************************************

	public static void main(String[] args) {

		ReadExcel2 read = new ReadExcel2();
		read.setUpExcelFile();
		Object[][] obj = read.getData();
		read.displayData(obj);
		System.out.println(ReadExcel2.COLcustname);
		System.out.println(read.reader.getMiniStatement_filePath());
		System.out.println(read.reader.getCustID_CustName_filePath());
		read.write_Erros_To_TxtFile(read.return_Excel_ColumnName(ReadExcel2.COLcustname), "Haridwar#",
				"Special Chars not allowred");
		/*
		 * try { read.writeAllMsgInExcel(obj, COLcustname, "Rakesh Manilal",
		 * "special Chars not allowed"); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

}
