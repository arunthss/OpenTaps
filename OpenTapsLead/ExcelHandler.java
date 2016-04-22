package openTapsTestNG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler
{
	private XSSFWorkbook wbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	
	private File file;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public void openExistingWorkBook(String workbook, String sheetName)
	{
		try
		{
			file = new File(workbook);
				//System.out.println("file Pointer "+file);
			fis = new FileInputStream(file);
				//System.out.println("fileIn Pointer "+fis);
			wbook = new XSSFWorkbook(fis);
				//System.out.println("wbook Pointer "+wbook);
			try 
			{
				//System.out.println("Opening Sheet "+sheetName);
				sheet = wbook.getSheet(sheetName);
				sheet.getSheetName();
			} 
			catch (NullPointerException e) 
			{
				sheet = wbook.createSheet(sheetName);
				System.out.println("Unable to Find Worksheet "+sheetName);
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Requested WorkBook "+workbook+" Not Found");
		}
		catch (IOException e) {
			System.out.println("Unable to perform IO Operations on "+workbook);
		}
	}
	public void createNewWorkBook(String workbook, String sheetName)
	{
		try
		{
			file = new File(workbook);
			fos = new FileOutputStream(file);
			wbook = new XSSFWorkbook();
			sheet = wbook.createSheet(sheetName);
			System.out.println("Created Workbook with Sheet Name "+sheetName);
		}
		catch(IOException e)
		{
			System.out.println("Unable to perform IO Operations on "+workbook);
		}
	}
	public void appendSheet(String workbook, String sheetName)
	{
		try
		{
			openExistingWorkBook(workbook, sheetName);
			//file = new File(workbook);
			fos = new FileOutputStream(file);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(workbook+" Not Found");
		}
	}
	public int getLastRow()
	{
		try
		{
			return sheet.getLastRowNum();
		}
		catch(NullPointerException e)
		{
			System.out.println("Unable to Operate on Rows");
			return -1;
		}
	}
	public String getData(int rowNum, int colNum)
	{
		try
		{
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);
			return cell.getStringCellValue();
		}
		catch(NullPointerException e)
		{
			System.out.println("Unable to Retrieve Data");
			return null;
		}
		catch (IllegalStateException e) 
		{
			System.out.println("Row "+rowNum+" Col "+colNum+" is Not a String");
			return null;
		}
	}
	public void putData(int rowNum, int colNum, String data)
	{
		try
		{
			if(sheet.getRow(rowNum)!=null)
			{
				row = sheet.getRow(rowNum);
			}
			else
			{
				row = sheet.createRow(rowNum);
			}
			
			cell = row.createCell(colNum);
			cell.setCellValue(data);
		}
		catch(NullPointerException e)
		{
			System.out.println("Unable to add data to the cell");
		}

	}
	public void closeWorkBook()
	{
		try 
		{
			if(fis!=null)
			{
				fis.close();
				System.out.println("File Input Stream Closed");
			}
				
			if(fos!=null)
			{
				wbook.write(fos);
				fos.close();
				System.out.println("File Output Stream Closed");
			}
				
			if(wbook!=null)
			{
				wbook.close();
				System.out.println("Work Book Closed");
			}
		}
		catch (NullPointerException e)
		{
			System.out.println("No Such File Present");
		}
		catch (IOException e) 
		{
			System.out.println("Unable to perform IO Operations");
		}
			
	}
	
}
