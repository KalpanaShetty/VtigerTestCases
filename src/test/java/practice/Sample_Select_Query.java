package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class Sample_Select_Query {

	public static void main(String[] args) throws Throwable {
		Connection connection=null;
		try {
			  Driver driverRef= new Driver();
		      DriverManager.registerDriver(driverRef);
		      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "root");
		      Statement statement = connection.createStatement();
		      String query="select * from students_info";
		      ResultSet resultSet = statement.executeQuery(query);
		      while(resultSet.next()) {
		    	System.out.println(resultSet.getInt(1)+"\t"+resultSet.getString(2));  
		      }
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			connection.close();	
		}
    
      
	}

}
