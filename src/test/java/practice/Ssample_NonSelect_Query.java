package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class Ssample_NonSelect_Query {

	public static void main(String[] args) throws Throwable {
		
		Connection connection=null;
		int result=0;
		try {
			  Driver driverRef= new Driver();
		      DriverManager.registerDriver(driverRef);
		      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "root");
		      Statement statement = connection.createStatement();
		      String query="insert into students_info (regno, firstname, middlename, lastname) values('05', 'Kalpana','banglore', '5')";
		       result=statement.executeUpdate(query);
				//System.out.println(result);
	}catch (Exception e) {
         //System.out.println("handle the Exception");
	}finally {
		if(result==1) {
			System.out.println("added succesfully");
		}else {
			System.out.println("not added");
		}
		/*step 5 : close the Connection*/
		 connection.close();
		 //System.out.println("Close");
    
      
	}
	}

}
