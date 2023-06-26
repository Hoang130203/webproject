package com.example.project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.project.BEAN.Question;
import com.example.project.BEAN.Quiz;
import com.example.project.DB.DBConnection;

public class Quiz_QuestionService {
	public static void addQuestion(String quizname,String idquestion ) {
		 try {
	            Connection conn = DBConnection.CreateConnection();
	            String sql = "INSERT INTO service VALUES (?, ?);";
	            PreparedStatement ptmt = conn.prepareStatement(sql);
	            ptmt.setString(1, quizname);
	            ptmt.setString(2, idquestion);
	            ptmt.executeUpdate();
	            ptmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
