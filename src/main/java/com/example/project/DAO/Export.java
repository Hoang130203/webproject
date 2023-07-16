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
import com.itextpdf.text.Image;
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
		
		List<String> lists= new ArrayList<>();
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
			lists.add(ques.getQuestionID()+": "+ques.getQuestionContent().replaceAll("<br>", "\n")+"\n");
			if(ques.getImageContent()!=null && !ques.getImageContent().isEmpty()) {
				lists.add("src/main/resources/static"+ques.getImageContent());
				lists.add("\n");
			}
			int i=1;
			for(String choice:ques.getListChoice())
			{
				lists.add(choice+"\n");
				if(ques.getImageChoice(i)!=null && !ques.getImageChoice(i).isEmpty()) {
					if(ques.getImageChoice(i).endsWith(".jpg") || ques.getImageChoice(i).endsWith(".png") ||ques.getImageChoice(i).endsWith(".jpeg")) {
						
						lists.add("src/main/resources/static"+ques.getImageChoice(i));
					}
				}
				i++;
			}
			lists.add("\n");
			try {
				String path = "D:\\"+name+".pdf"; 
				Document doc = new Document();
				
				PdfWriter.getInstance(doc, new FileOutputStream(path));
				doc.open();
				BaseFont bf = BaseFont.createFont("src/main/resources/static/font/UVNTinTuc_R.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				Font font = new Font(bf, 12f, Font.NORMAL, BaseColor.BLACK);
				Paragraph para = new Paragraph();
				para.setFont(font);
				for(String text:lists) {
					
					if(text.startsWith("src/main/resources")) {
						try {
						Image image = Image.getInstance(text);
						float maxHeight = 100f; // Chiều cao tối đa (ví dụ: 150 pixels)
					    float width = image.getWidth();
					    float height = image.getHeight();
					    
					    if (height > maxHeight) {
					        float scaleFactor = maxHeight / height;
					        float newWidth = width * scaleFactor;
					        
					        // Đặt lại kích thước ảnh theo chiều cao tối đa và tỷ lệ
					        image.scaleToFit(newWidth, maxHeight);
					    }
			            doc.add(image);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}else {
						
						para.add(text);
						doc.add(para);
						para.remove(0);
					}
				}
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
		
		Quiz quiz = null;
		List<String> lists= new ArrayList<>();

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
			lists.add(ques.getQuestionID()+": "+ques.getQuestionContent().replaceAll("<br>", "\n")+"\n");
			if(ques.getImageContent()!=null && !ques.getImageContent().isEmpty()) {
				lists.add("src/main/resources/static"+ques.getImageContent());
				lists.add("\n");
			}
			int i=1;
			for(String choice:ques.getListChoice())
			{
				lists.add(choice+"\n");
				if(ques.getImageChoice(i)!=null && !ques.getImageChoice(i).isEmpty()) {
					if(ques.getImageChoice(i).endsWith(".jpg") || ques.getImageChoice(i).endsWith(".png") ||ques.getImageChoice(i).endsWith(".jpeg")) {
						
						lists.add("src/main/resources/static"+ques.getImageChoice(i));
					}
				}
				i++;
			}
			lists.add("\n");
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
				for(String text:lists) {
					if(text.startsWith("src/main/resources")) {
						try {
						Image image = Image.getInstance(text);
						float maxHeight = 100f; // Chiều cao tối đa (ví dụ: 150 pixels)
					    float width = image.getWidth();
					    float height = image.getHeight();
					    
					    if (height > maxHeight) {
					        float scaleFactor = maxHeight / height;
					        float newWidth = width * scaleFactor;
					        
					        // Đặt lại kích thước ảnh theo chiều cao tối đa và tỷ lệ
					        image.scaleToFit(newWidth, maxHeight);
					    }
			            doc.add(image);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}else {
						
						para.add(text);
						doc.add(para);
						para.remove(0);
					}
				}
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
