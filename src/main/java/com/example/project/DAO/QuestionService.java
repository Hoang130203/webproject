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
	public static ArrayList<Question> format(String text) throws StringIndexOutOfBoundsException,Exception{

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
	    	
	    	  while(!line.toUpperCase().contains("ANSWER: A") &&!line.toUpperCase().contains("ANSWER: B") &&!line.toUpperCase().contains("ANSWER: C") 
	    			  &&!line.toUpperCase().contains("ANSWER: D") &&!line.toUpperCase().contains("ANSWER: E")){
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
      	String name;
      	String cauHoi;
      	if(line.indexOf(':')>0) {
      		name= line.substring(0,line.indexOf(':'));
      		cauHoi= line.substring(line.indexOf(':')+1).trim();
      		
      	}else {
      		name= null;
      		cauHoi= line.trim();
      	}
      	line=sc.nextLine();
      	demdong++;
      	while(line.isEmpty() ||line.isBlank()) {
    		demdong++;
    		line=sc.nextLine();
    	}
      
      	while((line.trim().charAt(0)!='A' && line.trim().charAt(0)!='a'
      			&&line.trim().charAt(0)!='B' && line.trim().charAt(0)!='b'
	    					  &&line.trim().charAt(0)!='C' && line.trim().charAt(0)!='c'
	    					  &&line.trim().charAt(0)!='D' && line.trim().charAt(0)!='d'
	    					  &&line.trim().charAt(0)!='E' && line.trim().charAt(0)!='e')||(line.trim().charAt(1)!='.')) {
      		if(line.trim().isEmpty()) {
      			demdong++;
      			sc.nextLine();
      		}else {
      			cauHoi=cauHoi+"<br>"+line;
          		line=sc.nextLine();
          		demdong++;
      		}
      		
      	}
    	
      	ArrayList<String> choices= new ArrayList<>();
      	while(!line.trim().toUpperCase().contains("ANSWER: A") &&!line.trim().toUpperCase().contains("ANSWER: B") &&!line.trim().toUpperCase().contains("ANSWER: C") 
	    			  &&!line.trim().toUpperCase().contains("ANSWER: D") &&!line.trim().toUpperCase().contains("ANSWER: E") ){
      		if(!line.isEmpty() && (line.contains("A.") ||line.contains("B.")||line.contains("C.")||line.contains("D.")||line.contains("E."))) {
      			choices.add(line.trim());
          		line= sc.nextLine();
      		}else if(!choices.isEmpty()) {

      				choices.get(choices.size()-1).concat(line);
      				line= sc.nextLine();

      		}
      		else {
      			line= sc.nextLine();
      		}
      		
      		demdong++;
      	}
      	while(line.isEmpty()) {
      		line=sc.nextLine();
      	}
      	if(line.length()>9) {
      		line=line.substring(0, 9);
      	}
      	String answer= line;
      	
      	if(name==null || cauHoi==null || cauHoi.equalsIgnoreCase("") || choices==null ||choices.size()<=2 || answer==null || answer.equalsIgnoreCase("") || choices.size()>5 || choices.size()<2
      			|| !answer.substring(0,6).equalsIgnoreCase("ANSWER")||choices.get(0).charAt(1)!='.') {
      		loi+=demdong+" ";
      		continue;
      	}else {
      		Question ques= new Question(name, cauHoi, choices, answer);
      		System.out.println(ques.getQuestionID());
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
	public static String loi(String text) throws StringIndexOutOfBoundsException,Exception{

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
	    	  while(!line.toUpperCase().contains("ANSWER: A") &&!line.toUpperCase().contains("ANSWER: B") &&!line.toUpperCase().contains("ANSWER: C") 
	    			  &&!line.toUpperCase().contains("ANSWER: D") &&!line.toUpperCase().contains("ANSWER: E")){
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
    	String name;
      	String cauHoi;
      	if(line.indexOf(':')>0) {
      		name= line.substring(0,line.indexOf(':'));
      		cauHoi= line.substring(line.indexOf(':')+1).trim();
      		
      	}else {
      		name= null;
      		cauHoi= line.trim();
      	}
    	line=sc.nextLine();
    	demdong++;
    	while(line.isEmpty() ||line.isBlank()) {
    		demdong++;
    		line=sc.nextLine();
    	}
    	
    	while((line.trim().charAt(0)!='A' && line.trim().charAt(0)!='a'
    			&&line.trim().charAt(0)!='B' && line.trim().charAt(0)!='b'
	    					  &&line.trim().charAt(0)!='C' && line.trim().charAt(0)!='c'
	    					  &&line.trim().charAt(0)!='D' && line.trim().charAt(0)!='d'
	    					  &&line.trim().charAt(0)!='E' && line.trim().charAt(0)!='e')||(line.trim().charAt(1)!='.')) {
    		if(line.trim().isEmpty()) {
      		
      			sc.nextLine();
      		}else {
      			cauHoi=cauHoi+"<br>"+line;
          		line=sc.nextLine();
          		
      		}
    		demdong++;
    	}
    	
    	ArrayList<String> choices= new ArrayList<>();
    	while(!line.trim().toUpperCase().contains("ANSWER: A") &&!line.trim().toUpperCase().contains("ANSWER: B") &&!line.trim().toUpperCase().contains("ANSWER: C") 
	    			  &&!line.trim().toUpperCase().contains("ANSWER: D") &&!line.trim().toUpperCase().contains("ANSWER: E") ){
    		if(!line.isEmpty() && (line.contains("A.") ||line.contains("B.")||line.contains("C.")||line.contains("D.")||line.contains("E."))) {
      			choices.add(line.trim());
          		line= sc.nextLine();
      		}else if(!choices.isEmpty()) {
      				choices.get(choices.size()-1).concat(line);
      				line= sc.nextLine();

      		}
    		else {
      			line= sc.nextLine();
      		}
      		
    		demdong++;
    	}
    	while(line.isEmpty()) {
      		line=sc.nextLine();
      	}
    	if(line.length()>9) {
      		line=line.substring(0, 9);
      	}
      	String answer= line;
      	
      	if(name==null || cauHoi==null || cauHoi.equalsIgnoreCase("") || choices==null ||choices.size()<=2 || answer==null || answer.equalsIgnoreCase("") || choices.size()>5 || choices.size()<2
      			|| !answer.substring(0,6).equalsIgnoreCase("ANSWER")||choices.get(0).charAt(1)!='.') {
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
               	String sql1="insert into bank_question values('"+bank+"','"+question.getQuestionID()+"');";
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
                   	ptmt= conn.prepareStatement(sql1);
                   	ptmt.executeUpdate();
    				
    				
    				
    			
				} catch (Exception e) {
					// TODO: handle exception
				}
              
            
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static String saveQuestion(Connection conn,ArrayList<Question> listquestion,String text) throws StringIndexOutOfBoundsException, Exception{
		
		
		try {
			List<Bank> listBank= new ArrayList<>();
			listBank= BankDao.listBank(conn);
			if(listBank==null || listBank.isEmpty()) {
				String sql = "INSERT INTO bank VALUES('bankgoc',null)";
				PreparedStatement ptmt= conn.prepareStatement(sql);
				ptmt.executeUpdate();
				listBank= BankDao.listBank(conn);
			}
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
	public static void addRandomQWithSubCate(int number,String quiz,String bank) {
		List<Question> list= BankDao.getListWithSubCate(bank);
		List<Question> subrandom= new ArrayList<>();
		
		
		try {
			if(list.size()>number) {
				subrandom=getRandomElements(list, number);
				for(Question q:subrandom) {
					Quiz_QuestionService.addQuestion(quiz, q.getQuestionID());
				}
			}else {
				subrandom=getRandomElements(list, list.size());
				for(Question q:subrandom) {
					Quiz_QuestionService.addQuestion(quiz, q.getQuestionID());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static List<Question> getRandomElements(List<Question> originalList, int numElements) {
        List<Question> randomElements = new ArrayList<>();

        // Sao chép danh sách gốc để không làm thay đổi danh sách ban đầu
        List<Question> tempList = new ArrayList<>(originalList);

        // Sử dụng đối tượng Random để sinh số ngẫu nhiên
        Random random = new Random();

        // Lấy ra numElements phần tử ngẫu nhiên từ danh sách gốc
        for (int i = 0; i < numElements; i++) {
            // Kiểm tra nếu danh sách tạm thời rỗng, thoát khỏi vòng lặp
            if (tempList.isEmpty()) {
                break;
            }

            // Sinh ra một chỉ số ngẫu nhiên từ 0 đến kích thước danh sách tạm thời - 1
            int randomIndex = random.nextInt(tempList.size());

            // Lấy phần tử tại chỉ số ngẫu nhiên từ danh sách tạm thời
            Question randomElement = tempList.get(randomIndex);

            // Thêm phần tử vào danh sách kết quả
            randomElements.add(randomElement);

            // Loại bỏ phần tử đã được chọn ra khỏi danh sách tạm thời
            tempList.remove(randomIndex);
        }

        return randomElements;
    }
	public static Question searchQuestion(String name) {
		
		Question question = null;
		try {
			Connection conn= DBConnection.CreateConnection();
			String sql= "select * from question where idquestion='"+name+"';";
			PreparedStatement ptmt= conn.prepareStatement(sql);
			ResultSet rs=ptmt.executeQuery();
			
			if(rs.next()) {
				
				ArrayList<String> listchoice= new ArrayList<>();
				if(rs.getString("idquestion").equals(name)) {
					
					for(int i=3;i<=7;i++) {
						if(rs.getString(i)!=null && !rs.getString(i).isBlank())
						 listchoice.add(rs.getString(i));
							
					}
					question= new Question(rs.getString(1), rs.getString(2), listchoice, rs.getString(8));
				
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return question;
	}
	public static void removequestion(String question) {
		try {
			Connection conn= DBConnection.CreateConnection();
			String sql= "SET FOREIGN_KEY_CHECKS = 0;";
			String sql1="delete from question where question.idquestion='"+question+"';";
			PreparedStatement ptmt= conn.prepareStatement(sql);
			String sql2="delete from bank_question where bank_question.questionid='"+question+"';";
			ptmt.executeUpdate();
			ptmt= conn.prepareStatement(sql1);
			ptmt.executeUpdate();
			ptmt= conn.prepareStatement(sql2);
			ptmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static String searchBank(String question) {
		String sql="select bankname from bank_question where questionid='"+question+"' limit 1;";
		String bank="j";
		
		try {
			Connection conn= DBConnection.CreateConnection();
			PreparedStatement ptmt= conn.prepareStatement(sql);
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				
				bank=rs.getString(1);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(bank);
		return bank;
	}
}
