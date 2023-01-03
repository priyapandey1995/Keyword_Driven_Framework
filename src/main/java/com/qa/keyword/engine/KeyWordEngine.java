package com.qa.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.keyword.base.Base;

public class KeyWordEngine {
	
	public WebDriver driver;
	public Properties prop;
	
	
	public static Workbook book;
	public static Sheet sheet;
	public Base base;
	
	public final String SCENARIO_SHEET_PATH ="C:\\Users\\pande\\eclipse-workspace\\keyboarddriven\\src\\main\\java\\com\\qa\\keyword\\scenarios\\keyboardlogin.xlsx";

	public void startExecution(String sheetName) throws FileNotFoundException {
		String locatorName =null;
		String locatorValue =null;
		FileInputStream file =null;
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		} 
		
		sheet = book.getSheet(sheetName);
			int k = 0;
			for(int i=0;i<sheet.getLastRowNum();i++) {
				try {
				String locatorColValue=sheet.getRow(i+1).getCell(k+1).toString().trim();//id=username
				if(!locatorColValue.equalsIgnoreCase("N/A")) {
					locatorColValue.split("=")[0].trim();
					locatorValue = locatorValue.split("=")[1].trim();
				}
			String action=sheet.getRow(i+1).getCell(k+2).toString().trim();
			String value=sheet.getRow(i+1).getCell(k+3).toString().trim();
			
			switch(action) {
			case "open browser":
				base = new Base();
				prop=base.init_properties();
				if(value.isEmpty() || value.equals("NA")) {
					driver = base.init_driver(prop.getProperty("browser"));
					Thread.sleep(5000);
				}else {
					driver =  base.init_driver(value);
					Thread.sleep(5000);
				}
				break;
			case "enter url":
				if(value.isEmpty() || value.equals("NA")) {
					driver.get(prop.getProperty("url"));
					Thread.sleep(5000);
				}else {
						driver.get(value);
					}
				break;
				
			case "quit":
				driver.quit();
				break;
			default:
				break;
				}
			
			switch(locatorName) {
			case "id":
				WebElement element =driver.findElement(By.id(locatorValue));
					if(action.equals("sendkeys")) {
						Thread.sleep(5000);
						element.sendKeys(value);
						
					}else if(action.equalsIgnoreCase("click")) {
						Thread.sleep(5000);
						element.click();
					}
					locatorName=null;
				break;
				
			case "xpath":
				WebElement elementTwo =driver.findElement(By.xpath(locatorValue));
				 if(action.equalsIgnoreCase("click")) {
					 Thread.sleep(5000);
					elementTwo.click();
					locatorName=null;
					break;
				 }
					
				default:
					break;
				 }
			}
			catch(Exception e) {
				
			}
			}
			}
	}
	

