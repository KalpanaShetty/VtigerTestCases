package com.crm.comcast.contactTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class CreateAndDeleteContactTest {

	public static void main(String[] args) throws Throwable, Throwable {
		FileInputStream fis=new FileInputStream("./TestData/PropertyFile.properties");
		Properties pro=new Properties();
		pro.load(fis);
	
		String Browser = pro.getProperty("browser");
		String Url=pro.getProperty("url");
	    String Username = pro.getProperty("username");
	    String Password = pro.getProperty("password");
	    
		//step d: Get random number to avoid duplicates
	    Random ran=new Random();
	    int randomNumber=ran.nextInt(1000);

	    //step e: read test case data from excel file to achieve D.D.T
	    FileInputStream fisx=new FileInputStream("./TestData/testdata.xlsx");
	     Workbook wb = WorkbookFactory.create(fisx);
	    Sheet sh = wb.getSheet("Contacts");
	     Row row = sh.getRow(1);
	     Cell cell = row.getCell(0);
	    String  LastName= cell.getStringCellValue()+randomNumber; 
	    System.out.println(LastName);
	    
	    WebDriver driver=null;
	    if(Browser.equalsIgnoreCase("chrome"))
	    {
	    	
			driver=new ChromeDriver();
		}
	    else if(Browser.equalsIgnoreCase("firefox"))
	    {
	    	
	    	driver=new FirefoxDriver();
	    }
	    else
	    {
	    	driver=new ChromeDriver();
	    }
	    driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	    
		//step1-----login to vtiger app
		driver.get(Url);
		driver.findElement(By.name("user_name")).sendKeys(Username);
		driver.findElement(By.name("user_password")).sendKeys(Password);
		driver.findElement(By.cssSelector("input#submitButton")).click();
		
		//step2-----click on contacts
		driver.findElement(By.linkText("Contacts")).click();
		
		//step3----create contacts
		driver.findElement(By.xpath("//img[@alt='Create Contact...']")).click();
		
		driver.findElement(By.name("lastname")).sendKeys(LastName);
        driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
        
       String actData = driver.findElement(By.className("dvHeaderText")).getText();
       if(actData.contains(LastName))
       {
			System.out.println("Contacts name is succesfully created::PASS");
		}
		else
		{
			System.out.println("Contacts name is not created::FAIL");
		}
       driver.findElement(By.name("Delete")).click();
       Alert alt=driver.switchTo().alert();
       alt.accept();
       
       driver.findElement(By.name("search_text")).sendKeys(LastName);
       WebElement dropdown = driver.findElement(By.name("search_field"));
       dropdown.click();
       Select sel=new Select(dropdown);
       sel.selectByValue("lastname");
       driver.findElement(By.name("submit")).click();
       
       String msg = driver.findElement(By.cssSelector("span.genHeaderSmall")).getText();
       if(msg.contains(LastName))
       {
    	   System.out.println(LastName + "IS NOT DELETED");
       }
       else
       {
    	   System.out.println(LastName +" IS DELETED");
       }
       //step 7: logout
	WebElement element = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
	Actions act=new Actions(driver);
	act.moveToElement(element).perform();
	driver.findElement(By.linkText("Sign Out")).click();
	driver.close();

	}

	

}
