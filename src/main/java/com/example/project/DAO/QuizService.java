package com.example.project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.project.BEAN.Question;
import com.example.project.BEAN.Quiz;
import com.example.project.DB.DBConnection;

public class QuizService {
	public static List<Question> listQuestion(Connection conn,String quiz){
		List<Question> list= new ArrayList<>();
		String sql="select qu.* from question qu join service where service.quizname='"+quiz+"' and service.idquestion=qu.idquestion ";
		try {
			PreparedStatement ptmt= conn.prepareStatement(sql);
			ResultSet rs= ptmt.executeQuery(sql);
			while(rs.next()) {
				String id=rs.getString("idquestion");
				String content= rs.getString("questioncontent");
				ArrayList<String> choices= new ArrayList<>();
				for(int i=1;i<=5;i++) {
					if(!rs.getString("choice"+i).isEmpty() && !rs.getString("choice"+i).isBlank()) {
						choices.add(rs.getString("choice"+i));
					}
					
				}
				String ans= rs.getString("answer");
				Question question= new Question(id, content, choices, ans);
				if(rs.getString(9)!=null&& !rs.getString(9).isEmpty()) {
					question.setImageContent(rs.getString(9));
					
				}
				if(rs.getString(10)!=null && !rs.getString(10).isEmpty()) {
					question.setImageChoice1(rs.getString(10));
				}
				if(rs.getString(11)!=null && !rs.getString(11).isEmpty()) {
					question.setImageChoice2(rs.getString(11));				
				}
				if(rs.getString(12)!=null && !rs.getString(12).isEmpty()) {
					question.setImageChoice3(rs.getString(12));
				}
				if(rs.getString(13)!=null && !rs.getString(13).isEmpty()) {
					question.setImageChoice4(rs.getString(13));
				}
				if(rs.getString(14)!=null && !rs.getString(14).isEmpty()) {
					question.setImageChoice5(rs.getString(14));
				}
				
				
				
				
				
				list.add(question);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
		
	}
	public static void addQuiz(Connection conn, Quiz quiz) {
		String sql= "insert into quiz values('"+quiz.getQuizName()+"','"+quiz.getTimeLimit()+"');";
		try {
			PreparedStatement ptmt= conn.prepareStatement(sql);
			ptmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static List<Quiz> listQuiz(Connection conn){
		List<Quiz> list= new ArrayList<>();
		String sql="select * from quiz";
		try {
			PreparedStatement ptmt= conn.prepareStatement(sql);
			ResultSet rs= ptmt.executeQuery();
			while(rs.next()) {
				Quiz quiz= new Quiz(rs.getString("quizname"),rs.getInt("timelimit"));
				
				list.add(quiz);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
		
	}
	public static void deleteQuestionInQuiz(String quiz,String question) {
		String sql="SET FOREIGN_KEY_CHECKS = 0;";
		String sql1="delete from service where quizname='"+quiz+"' and idquestion='"+question+"';";
		try {
			Connection conn= DBConnection.CreateConnection();
			PreparedStatement ptmt= conn.prepareStatement(sql);
			ptmt.executeUpdate();
			ptmt=conn.prepareStatement(sql1);
			ptmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
