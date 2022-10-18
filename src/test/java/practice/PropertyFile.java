package practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {

	public static void main(String[] args) throws Throwable {
	FileInputStream fis= new FileInputStream("./TestData/PropertyFile.properties");
             Properties properties = new Properties();
             properties.load(fis);
             String URL = properties.getProperty("url");
             String BROWSER = properties.getProperty("browser");
             String USERNAME = properties.getProperty("username");
             String PASSWORD = properties.getProperty("password");
           System.out.println(URL);
           System.out.println(BROWSER);
           System.out.println(USERNAME);
           System.out.println(PASSWORD);
	}

}
