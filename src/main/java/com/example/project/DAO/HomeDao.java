package com.example.project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.project.BEAN.Quiz;



public class HomeDao {
	public static List<Quiz> DisplayQuiz (Connection conn)
	{
		
		List<Quiz> list = new ArrayList<Quiz>();
		
		String sql = "select * from quiz";
		try 
		{
			PreparedStatement ptmt = conn.prepareStatement(sql);
			
			ResultSet rs = ptmt.executeQuery();
			
			while (rs.next())
			{
				Quiz quiz = new Quiz(rs.getString("quizname"));
				list.add(quiz);
			}
			
			
			
		} 
		catch (SQLException e) 
		{


			e.printStackTrace();
		}
				
		return list;
	}
	public static void DeleteQuiz (Connection conn,String quiz)
	{
		
		
		
		String sql="delete from quiz where quizname='"+quiz+"'";		
		try 
		{
			PreparedStatement ptmt = conn.prepareStatement(sql);
			
			ptmt.executeUpdate();
		
			
			
			
		} 
		catch (SQLException e) 
		{


			e.printStackTrace();
		}
				
		
	}
}
