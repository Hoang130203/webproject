package com.example.project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.project.BEAN.Question;

public class QuizService {
	public static List<Question> listQuestion(Connection conn,String quiz){
		List<Question> list= new ArrayList<>();
		String sql="select qu.* from question qu join service where service.quizname='"+quiz+"' and service.idquestionl=qu.idquestion ";
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
				list.add(question);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
		
	}
}
