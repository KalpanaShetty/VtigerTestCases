package com.crm.comcast.organiazationTest;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class CreateOrganizationTest {

	public static void main(String[] args) throws Throwable, Throwable {
		//step 1:read common data from properties file
		//step a: get the java representation of the object for physical file(properties file)
	FileInputStream fis=new FileInputStream("./TestData/PropertyFile.properties");
	
	//step b: create a object to property class to load all the keys
	Properties prop = new Properties();
	prop.load(fis);
	
	//step c: read the value using getProperty("key")
	String Browser = prop.getProperty("browser");
	
	String Url = prop.getProperty("url");
	String Username = prop.getProperty("username");
	String Password = prop.getProperty("password");

	//step d: Get random number to avoid duplicates
    Random ran=new Random();
    int randomNumber=ran.nextInt(1000);

    //step e: read test case data from excel file to achieve D.D.T
    FileInputStream fisx=new FileInputStream("./TestData/testData.xlsx");
   Workbook wb = WorkbookFactory.create(fisx);
     Sheet sh = wb.getSheet("Organizations");
    Row row = sh.getRow(1);
     Cell cell = row.getCell(0);
    String organizationName = cell.getStringCellValue()+randomNumber;    
    System.out.println(organizationName);
    
    //step f: launch the browser
    WebDriver driver=null;
    if(Browser.equals("firefox"))
    {
    	driver=new FirefoxDriver();
    }
    else if(Browser.equals("ie"))
    {
    	driver=new InternetExplorerDriver();
    }
    else {
    	driver=new ChromeDriver();
    }
    //step 2:Login
    driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
    driver.get(Url);
	driver.findElement(By.name("user_name")).sendKeys(Username);
	driver.findElement(By.name("user_password")).sendKeys(Password);
	driver.findElement(By.id("submitButton")).click();
	
	//step 3:Navigate to organization module
	driver.findElement(By.linkText("Organizations")).click();
	
	//step 4:click on create organization button
	driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();
	driver.manage().window().maximize();
	
	//step 5:enter all the details and create organization
	driver.findElement(By.name("accountname")).sendKeys(organizationName);
	driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
	
	//step 6:verify organization name in header of the message
	String actualSuccesfulMsg = driver.findElement(By.className("dvHeaderText")).getText();
	if(actualSuccesfulMsg.contains(organizationName))
	{
		System.out.println("Organization name is succesfully created::PASS");
	}
	else
	{
		System.out.println("Organization name is not created::FAIL");
	}
	
    //step 7: logout
	 WebElement element = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
	Actions act=new Actions(driver);
	act.moveToElement(element).perform();
	driver.findElement(By.linkText("Sign Out")).click();
	driver.close();

	}

}
