package com.example.project.BEAN;

import java.util.ArrayList;

public class Question {
	private String questionID;
	private String questionContent;
	private ArrayList<String> listChoice;
	private String answer;
	public String getQuestionID() {
		return questionID;
	}
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public ArrayList<String> getListChoice() {
		return listChoice;
	}
	public void setListChoice(ArrayList<String> listChoice) {
		this.listChoice = listChoice;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Question(String questionID, String questionContent, ArrayList<String> listChoice, String answer) {
		super();
		this.questionID = questionID;
		this.questionContent = questionContent;
		this.listChoice = listChoice;
		this.answer = answer;
	}
	
	
}
