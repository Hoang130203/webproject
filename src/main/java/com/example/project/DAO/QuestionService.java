package com.example.project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.project.BEAN.Bank;
import com.example.project.BEAN.Question;
import com.example.project.DB.DBConnection;

import java.util.Scanner;


public class QuestionService {
	public static ArrayList<Question> format(String text){

	      ArrayList<Question> questions = new ArrayList<>();
	      Scanner scanner= new Scanner(text);
	      ArrayList<String> q= new ArrayList<>();
//	      int i=0;
	      while(scanner.hasNextLine()) {
	    	  String line=scanner.nextLine();
	    	  String tex="";
	    	  if(line==null) {
	    		  break;
	    	  }
	    	  if(line.isBlank() || line.isEmpty()) {
//	    		  line= scanner.nextLine();
	    		  continue;
	    	  }
	    	  while(!line.equalsIgnoreCase("ANSWER: A") &&!line.equalsIgnoreCase("ANSWER: B") &&!line.equalsIgnoreCase("ANSWER: C") 
	    			  &&!line.equalsIgnoreCase("ANSWER: D") &&!line.equalsIgnoreCase("ANSWER: E")){
	    		  tex= tex+line+"\n";
	    		  line=scanner.nextLine();
	    	  }
	    	  tex=tex+line;
	    	  if(!tex.isBlank() && !tex.isEmpty()) {
	    		  q.add(tex);
//	    		  i++;
	    	  }
	    	  
//	    	  line=scanner.nextLine();
	      }
	    int demdong=0;
	    String loi= "Error at: ";
      for(String qs: q) {
      	Scanner sc= new Scanner(qs);
      	String line= sc.nextLine();
      	demdong++;
      	String name= line.substring(0,line.indexOf(':'));
      	String cauHoi= line.substring(line.indexOf(':')+1).trim();
      	line=sc.nextLine();
      	demdong++;
      	while(line.trim().charAt(0)!='A' && line.trim().charAt(0)!='a'
      			&&line.trim().charAt(0)!='B' && line.trim().charAt(0)!='b'
	    					  &&line.trim().charAt(0)!='C' && line.trim().charAt(0)!='c'
	    					  &&line.trim().charAt(0)!='D' && line.trim().charAt(0)!='d'
	    					  &&line.trim().charAt(0)!='E' && line.trim().charAt(0)!='e') {
      		cauHoi=cauHoi+"<br>"+line;
      		line=sc.nextLine();
      		demdong++;
      	}
      	ArrayList<String> choices= new ArrayList<>();
      	while(!line.equalsIgnoreCase("ANSWER: A") &&!line.equalsIgnoreCase("ANSWER: B") &&!line.equalsIgnoreCase("ANSWER: C") 
	    			  &&!line.equalsIgnoreCase("ANSWER: D") &&!line.equalsIgnoreCase("ANSWER: E") ){
      		choices.add(line);
      		line= sc.nextLine();
      		demdong++;
      	}
      	String answer= line;
      	if(name==null || cauHoi==null || cauHoi.equalsIgnoreCase("") || choices==null ||choices.size()<=2 || answer==null || answer.equalsIgnoreCase("")) {
      		loi+=demdong+" ";
      		continue;
      	}else {
      		Question ques= new Question(name, cauHoi, choices, answer);
          	questions.add(ques);
      	}
      	sc.close();
      }
      if(loi.equals("Error at: ")) {
      	System.out.print("Không có lỗi nào!");
      }else {
    		System.out.print( loi);
      }
      scanner.close();
      return questions;
  }
	public static String loi(String text){

	      ArrayList<Question> questions = new ArrayList<>();
	      Scanner scanner= new Scanner(text);
	      ArrayList<String> q= new ArrayList<>();
//	      int i=0;
	      while(scanner.hasNextLine()) {
	    	  String line=scanner.nextLine();
	    	  String tex="";
	    	  if(line==null) {
	    		  break;
	    	  }
	    	  if(line.isBlank() || line.isEmpty()) {
//	    		  line= scanner.nextLine();
	    		  continue;
	    	  }
	    	  while(!line.equalsIgnoreCase("ANSWER: A") &&!line.equalsIgnoreCase("ANSWER: B") &&!line.equalsIgnoreCase("ANSWER: C") 
	    			  &&!line.equalsIgnoreCase("ANSWER: D") &&!line.equalsIgnoreCase("ANSWER: E")){
	    		  tex= tex+line+"\n";
	    		  line=scanner.nextLine();
	    	  }
	    	  tex=tex+line;
	    	  if(!tex.isBlank() && !tex.isEmpty()) {
	    		  q.add(tex);
//	    		  i++;
	    	  }
	    	  
//	    	  line=scanner.nextLine();
	      }
	    int demdong=0;
	    String loi= "Error at: ";
    for(String qs: q) {
    	Scanner sc= new Scanner(qs);
    	String line= sc.nextLine();
    	demdong++;
    	String name= line.substring(0,line.indexOf(':'));
    	String cauHoi= line.substring(line.indexOf(':')+1).trim();
    	line=sc.nextLine();
    	demdong++;
    	while(line.trim().charAt(0)!='A' && line.trim().charAt(0)!='a'
    			&&line.trim().charAt(0)!='B' && line.trim().charAt(0)!='b'
	    					  &&line.trim().charAt(0)!='C' && line.trim().charAt(0)!='c'
	    					  &&line.trim().charAt(0)!='D' && line.trim().charAt(0)!='d'
	    					  &&line.trim().charAt(0)!='E' && line.trim().charAt(0)!='e') {
    		cauHoi=cauHoi+"<br>"+line;
    		line=sc.nextLine();
    		demdong++;
    	}
    	ArrayList<String> choices= new ArrayList<>();
    	while(!line.equalsIgnoreCase("ANSWER: A") &&!line.equalsIgnoreCase("ANSWER: B") &&!line.equalsIgnoreCase("ANSWER: C") 
	    			  &&!line.equalsIgnoreCase("ANSWER: D") &&!line.equalsIgnoreCase("ANSWER: E") ){
    		choices.add(line);
    		line= sc.nextLine();
    		demdong++;
    	}
    	String answer= line;
    	if(name==null || cauHoi==null || cauHoi.equalsIgnoreCase("") || choices==null ||choices.size()<=2 || answer==null || answer.equalsIgnoreCase("")) {
    		loi+=demdong+" ";
    		continue;
    	}else {
    		Question ques= new Question(name, cauHoi, choices, answer);
        	questions.add(ques);
    	}
    	sc.close();
    }
    if(loi.equals("Error at: ")) {
    	System.out.print("Không có lỗi nào!");
    }else {
  		System.out.print( loi);
    }
    scanner.close();
    if(loi.equalsIgnoreCase("Error at: ")) {
    	return "không có lỗi nào!";
    }else {
    	
    	return loi;
    }
}
public static void savequestion(Connection conn,Question question,String bank) throws SQLException{
		
		
		try {

               	 
               	String sql = "INSERT INTO question VALUES(?,?,?,?,?,?,?,?)";
               	
               	 try {
                 	
                   	PreparedStatement ptmt = conn.prepareStatement(sql);
                   	ptmt.setString(1, question.getQuestionID());
                   	ptmt.setString(2, question.getQuestionContent());
                   	int i=0;
                   	for(String choice:question.getListChoice()) {
                   		if(choice != null && !choice.isEmpty()) {
                   			ptmt.setString(i+3, question.getListChoice().get(i));
                   			System.out.println(question.getQuestionID());
                   		}else {
                   			ptmt.setString(i+3, "");
                   		
                   		}
                   		i++;
                   		
                   	}
                   	while(i<5) {
                   		ptmt.setString(i+3, "");
                   		i++;
                   	}
                   
                   	
                   	ptmt.setString(8, question.getAnswer());
                   	ptmt.executeUpdate();
                   	System.out.println(sql);
    				
    				
    				
    			
				} catch (Exception e) {
					// TODO: handle exception
				}
              
            
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static String saveQuestion(Connection conn,ArrayList<Question> listquestion,String text) throws SQLException{
		
		
		try {
			List<Bank> listBank= new ArrayList<>();
			listBank= BankDao.listBank(conn);
			Random random = new Random();
	        int randomIndex = random.nextInt(listBank.size());
			Bank bank= listBank.get(randomIndex);
			System.out.println(bank.getBankName());
			for(Question question:listquestion) {
				
               	 System.out.println(question.getQuestionID());
               	 
               	String sql = "INSERT INTO question VALUES(?,?,?,?,?,?,?,?)";
               	
               	 try {
                 	
                   	PreparedStatement ptmt = conn.prepareStatement(sql);
                   	ptmt.setString(1, question.getQuestionID());
                   	ptmt.setString(2, question.getQuestionContent());
                   	int i=0;
                   	for(String choice:question.getListChoice()) {
                   		if(choice != null && !choice.isEmpty()) {
                   			ptmt.setString(i+3, question.getListChoice().get(i));
                   			System.out.println(question.getQuestionID());
                   		}else {
                   			ptmt.setString(i+3, "");
                   		
                   		}
                   		i++;
                   		
                   	}
                   	while(i<5) {
                   		ptmt.setString(i+3, "");
                   		i++;
                   	}
                   
                   	System.out.println(question.getQuestionID());
                   	ptmt.setString(8, question.getAnswer());
                   	ptmt.executeUpdate();
                   	System.out.println(sql);
    				
    				
    				
    				String sql2="insert into bank_question values('"+bank.getBankName()+"','"+question.getQuestionID()+"')";
    				PreparedStatement ptmt2= conn.prepareStatement(sql2);
    				ptmt2.executeUpdate();
    				
				} catch (Exception e) {
					// TODO: handle exception
				}
              
            }
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return QuestionService.loi(text);
	}
	public static void addrandomquestion(int number,String quiz,String bank) {
		try {
			Connection conn=DBConnection.CreateConnection();
			String sql1= "select count(*) from question,bank_question bq where bq.bankname='"+bank+"' and question.idquestion=bq.questionid";
			PreparedStatement ptmt= conn.prepareStatement(sql1);
			ResultSet rs= ptmt.executeQuery();
			int nrandom=0;
			if(rs.next()) {
				 nrandom=Integer.parseInt(rs.getString("Count(*)"));
			}
			String sql2="";
			if(nrandom>=number) {
				sql2="insert into service(quizname,idquestion) SELECT quiz.quizname,bank_question.questionid FROM bank_question,quiz"
						+ " where bank_question.bankname='"+bank+"' and quiz.quizname='"+quiz+"' ORDER BY RAND() LIMIT "+ number;
			}else {
				sql2="insert into service(quizname,idquestion) SELECT quiz.quizname,bank_question.questionid FROM bank_question,quiz"
						+ " where bank_question.bankname='"+bank+"' and quiz.quizname='"+quiz+"' ORDER BY RAND() LIMIT "+ nrandom;
			}
			//System.out.println(sql2);
			ptmt=conn.prepareStatement(sql2);
			ptmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
