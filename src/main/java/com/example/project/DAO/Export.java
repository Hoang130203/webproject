package com.example.project.DAO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import com.example.project.BEAN.Question;
import com.example.project.BEAN.Quiz;
import com.example.project.DB.DBConnection;

public class Export {
	String name;
	String password;
	public Export(String name)	
	{
		this.name=name;
	}
	public Export(String name, String password)
	{
	this.name=name;
	this.password=password;
	}
	public void expordPdf()
	{
		String s="";
		Quiz quiz = null;
		Connection conn= null;
		try {
			conn= DBConnection.CreateConnection();
			List<Quiz> listquiz= new ArrayList<Quiz>();
			
			listquiz= QuizService.listQuiz(conn);
			for(Quiz q:listquiz) {
				if(q.getQuizName().trim().equals(name.trim())) {
					quiz= q;
				}
			}
		} catch (Exception e1) {
		}
		for(Question ques:QuizService.listQuestion(conn, quiz.getQuizName()))
		{
			s=s+ques.getQuestionID()+": "+ques.getQuestionContent().replaceAll("<br>", "\n")+"\n";
			for(String choice:ques.getListChoice())
			{
				s=s+choice+"\n";
			}
			s=s+"\n";
			try {
				String path = "D:\\"+name+".pdf"; 
				Document doc = new Document();
				
				PdfWriter.getInstance(doc, new FileOutputStream(path));
				doc.open();
				BaseFont bf = BaseFont.createFont("src/main/resources/static/font/UVNTinTuc_R.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				Font font = new Font(bf, 12f, Font.NORMAL, BaseColor.BLACK);
				Paragraph para = new Paragraph();
				para.setFont(font);
				para.add(s);
				doc.add(para);
				doc.close();
			}
		 catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DocumentException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	public void expordPdfwithPass()
	{
		String s="";
		Quiz quiz = null;
		Connection conn= null;
		try {
			conn= DBConnection.CreateConnection();
			List<Quiz> listquiz= new ArrayList<Quiz>();
			
			listquiz= QuizService.listQuiz(conn);
			for(Quiz q:listquiz) {
				if(q.getQuizName().trim().equals(name.trim())) {
					quiz= q;
				}
			}
		} catch (Exception e1) {
		}
		for(Question ques:QuizService.listQuestion(conn, quiz.getQuizName()))
		{
			s=s+ques.getQuestionID()+": "+ques.getQuestionContent().replaceAll("<br>", "\n")+"\n";
			for(String choice:ques.getListChoice())
			{
				s=s+choice+"\n";
			}
			s=s+"\n";
			try {
				String path="D:\\"+name+".pdf"; 
				Document doc = new Document();
				PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
				pdfWriter.setEncryption(password.getBytes(),
						null, 
			                  PdfWriter.ALLOW_PRINTING, 
			                   PdfWriter.ENCRYPTION_AES_256);
				doc.open();
				BaseFont bf = BaseFont.createFont("src/main/resources/static/font/UVNTinTuc_R.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				Font font = new Font(bf, 12f, Font.NORMAL, BaseColor.BLACK);
				Paragraph para = new Paragraph();
				para.setFont(font);
				para.add(s);
				doc.add(para);
				doc.close();
			}
		 catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DocumentException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
