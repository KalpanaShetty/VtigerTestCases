package com.crm.comcast.campaignTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
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
import org.openqa.selenium.interactions.Actions;

public class CreateCampaignwithProduct2 {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		//step a: get the java representation of the object for physical file(properties file)
      FileInputStream fis=new FileInputStream("./Data/commondata.properties");
						
	 //step b: create a object to property class to load all the keys
		Properties prop = new Properties();
		prop.load(fis);
						
		//step c: read the value using getProperty("key")
		String Browser = prop.getProperty("browser");
		String Url = prop.getProperty("url");
		String Username = prop.getProperty("username");
		String Password = prop.getProperty("password");
						
		// to launch the browsers

		WebDriver driver=null;  //global variable
		if(Browser.equalsIgnoreCase("chrome"))
		{
           driver=new ChromeDriver();
		}
		else if(Browser.equalsIgnoreCase("firefox")) 
		{
				driver=new FirefoxDriver();}
				else{
				driver=new ChromeDriver();
				}
					
			// to wait untill webelement are available and gets loaded
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
						
						//step1-----login to vtiger app
			driver.get(Url);
		    driver.findElement(By.name("user_name")).sendKeys(Username);
			driver.findElement(By.name("user_password")).sendKeys(Password);
			driver.findElement(By.cssSelector("input#submitButton")).click();

			           //step c:click on products link to create product
						driver.findElement(By.linkText("Products")).click();
						driver.findElement(By.xpath("//img[@alt='Create Product...']")).click();
						
						 
						//step d: Generate random number to avoid duplicates
					    Random ran=new Random();
					    int randomNumber=ran.nextInt(1000);

					    //step e: read test case data of product name from excel file to achieve D.D.T
					    FileInputStream fisx=new FileInputStream("./Data/TestData.xlsx");
					    Workbook wb = WorkbookFactory.create(fisx);
					    Sheet sh = wb.getSheet("Products");
					    Row row = sh.getRow(1);
					    Cell cell = row.getCell(0);
					    String  productName= cell.getStringCellValue()+randomNumber; 
					    System.out.println(productName);
					    
					    Sheet sh2 = wb.getSheet("Products");
					    Row row2 = sh2.getRow(2);
					   Cell cell2 = row2.getCell(0);
					   String childWindowTitle = cell2.getStringCellValue();
					   System.out.println(childWindowTitle);
					   
					   
					   
					   Sheet sh3 = wb.getSheet("Campaigns");
					    Row row3 = sh3.getRow(2);
					   Cell cell3 = row3.getCell(0);
					   String parentWindowTitle = cell3.getStringCellValue();
					   System.out.println(childWindowTitle);
					    //step f:pass the product name fetched from excel sheet inside product name textbox and save
					    driver.findElement(By.name("productname")).sendKeys(productName);
					    driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
					    
					    //step g:create a campaign for obtained product
			            driver.findElement(By.xpath("//img[@src='themes/softed/images/menuDnArrow.gif']")).click();
					    driver.findElement(By.linkText("Campaigns")).click();
					    driver.findElement(By.xpath("//img[@alt='Create Campaign...']")).click();
					   
					  //step h: read test case data of campaign name  from excel file to achieve D.D.T
					    FileInputStream fisa=new FileInputStream("./Data/TestData.xlsx");
					    Workbook wb1 = WorkbookFactory.create(fisa);
					    Sheet sh1 = wb.getSheet("Campaign");
					    Row row1 = sh.getRow(1);
					    Cell cell1 = row.getCell(0);
					    String  campaignName= cell.getStringCellValue()+randomNumber; 
					    System.out.println(campaignName);
					    
					    //step i:pass the campaign name fetched from excel sheet inside campaign name textbox.
					    driver.findElement(By.name("campaignname")).sendKeys(campaignName);
					    driver.findElement(By.xpath("//img[@src='themes/softed/images/select.gif']")).click();
				    
					    //step j:since it is integrated test case we need to select the required product wrt to camapign
					             //so we need to switch to the window from campaign webpage to child window to slect the 
					             //required product name which has already been created.
					      //campaign webpage session id
					    String parentTitle = driver.getTitle();

					    Set<String> set = driver.getWindowHandles();  //child windows session id are stored in allid
					   	   Iterator<String> it = set.iterator();
					   	   while(it.hasNext()) {
					   		   String wId = it.next();
					   		   driver.switchTo().window(wId);
					   		   String currentWindowTitle = driver.getTitle();
					   		   
					   	   if(currentWindowTitle.contains(childWindowTitle)) {
					   		 break;  
					   	   }
					   	   }
					    //step k:now driver is focussing on child window to perform required actions at child window
					    driver.findElement(By.name("search_text")).sendKeys(productName);
					    driver.findElement(By.name("search")).click();
					    driver.findElement(By.linkText(productName)).click();
					    
				    
					    //step L:Once the task is done in child window again we need to switch back to parent window ie campaign page or mainId 
					    Set<String> set2 = driver.getWindowHandles();  //child windows session id are stored in allid
					   	   Iterator<String> it2 = set2.iterator();
					    while(it2.hasNext()) {
					   		   String wId = it2.next();
					   		   driver.switchTo().window(wId);
					   		   String currentWindowTitle = driver.getTitle();
					   		   
					   	   if(currentWindowTitle.contains(parentWindowTitle)) {
					   		 break;  
					   	   }
					   	   }
					    driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
					    
					    //step 7: logout
						WebElement element = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
						Actions act=new Actions(driver);
						act.moveToElement(element).perform();
						driver.findElement(By.linkText("Sign Out")).click();
						driver.close();
	}

}
