package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class Sample_UnitTesting {

	public static void main(String[] args) throws Throwable {
		String firstName= "kalpana";
		Connection connection=null;
		try {
			  Driver driverRef= new Driver();
		      DriverManager.registerDriver(driverRef);
		      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "root");
		      Statement statement = connection.createStatement();
		      String query="select * from students_info";
		      ResultSet resultSet = statement.executeQuery(query);
		      while(resultSet.next()) {
		    	String actualFirstName = resultSet.getString(2); 
		    	
		    	if(actualFirstName.equalsIgnoreCase(firstName)) {
		    		System.out.println("validation succesful");
		      }else {
		    	  System.out.println("validation unsuccesfull");
		      }
		}}catch (Exception e) {
			// TODO: handle exception
		}finally {
			connection.close();	
		}
    
      
		

	
	}
}
