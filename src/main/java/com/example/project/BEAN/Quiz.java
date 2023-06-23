package com.example.project.BEAN;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
	private String quizName;
	private List<Question> listQuestion= new ArrayList<Question>();
	
	public List<Question> getListQuestion() {
		return listQuestion;
	}

	public void setListQuestion(List<Question> listQuestion) {
		this.listQuestion = listQuestion;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public Quiz(String quizName) {
		super();
		this.quizName = quizName;
	}
	public Quiz() {
		
	}
}
