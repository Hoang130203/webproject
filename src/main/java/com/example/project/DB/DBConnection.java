package com.example.project.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection CreateConnection()
	{
		Connection conn = null;;
		
		String url = "jdbc:mysql://127.0.0.1:3306/project?useUnicode=true&characterEncoding=utf8";
		
		String username = "root";
		String password = "";
		try 
		{
			//load driver
			Class.forName("com.mysql.jdbc.Driver");
			//create connection
			conn = DriverManager.getConnection(url,username,password);
			
		} 
		catch (ClassNotFoundException e) 
		{

			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}	
		return conn;
	}
}
