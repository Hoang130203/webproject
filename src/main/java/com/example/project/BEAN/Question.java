package com.example.project.BEAN;

import java.util.ArrayList;

public class Question {
	private String questionID;
	private String questionContent;
	private ArrayList<String> listChoice;
	private String answer;
	private String imageContent;
	private String imageChoice1;
	private String imageChoice2;
	private String imageChoice3;
	private String imageChoice4;
	private String imageChoice5;
	
	public String getImageContent() {
		return imageContent;
	}
	public void setImageChoice(int i, String image) {
		if(i==1) {
			this.imageChoice1 = image;
		}else if(i==2) {
			this.imageChoice2 = image;
		}else if(i==3) {
			this.imageChoice3 = image;
		}else if(i==4) {
			this.imageChoice4 = image;
		}else if(i==5) {
			this.imageChoice5 = image;
		}
	}
	public String getImageChoice(int i) {
		if(i==1) {
			return this.imageChoice1;
		}else if(i==2) {
			return this.imageChoice2;
		}else if(i==3) {
			return this.imageChoice3;
		}else if(i==4) {
			return this.imageChoice4;
		}else {
			return this.imageChoice5;
		}
	}
	public void setImageContent(String imageContent) {
		this.imageContent = imageContent;
	}
	public String getImageChoice1() {
		return imageChoice1;
	}
	public void setImageChoice1(String imageChoice1) {
		this.imageChoice1 = imageChoice1;
	}
	public String getImageChoice2() {
		return imageChoice2;
	}
	public void setImageChoice2(String imageChoice2) {
		this.imageChoice2 = imageChoice2;
	}
	public String getImageChoice3() {
		return imageChoice3;
	}
	public void setImageChoice3(String imageChoice3) {
		this.imageChoice3 = imageChoice3;
	}
	public String getImageChoice4() {
		return imageChoice4;
	}
	public void setImageChoice4(String imageChoice4) {
		this.imageChoice4 = imageChoice4;
	}
	public String getImageChoice5() {
		return imageChoice5;
	}
	public void setImageChoice5(String imageChoice5) {
		this.imageChoice5 = imageChoice5;
	}
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
