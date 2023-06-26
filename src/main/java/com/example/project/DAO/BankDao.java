package com.example.project.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.project.BEAN.Bank;
import com.example.project.BEAN.Question;

public class BankDao {
	public static List<Bank> listBank(Connection conn){
		List<Bank> list= new ArrayList<>();
		String sql="select * from bank";
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			
			ResultSet rs = ptmt.executeQuery();
			while (rs.next())
			{
				Bank bank = new Bank(rs.getString("bankname"),rs.getString("parent"));
				list.add(bank);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public static void addBank(Connection conn,Bank bank) {
		if(bank.getParent()==null || bank.getParent().isEmpty()) {
			String sql="insert into bank values('"+bank.getBankName()+"',null)";
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				
				ptmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			String sql="insert into bank values('"+bank.getBankName()+"','"+bank.getParent()+"')";
			try {
				PreparedStatement ptmt = conn.prepareStatement(sql);
				
				ptmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	public static List<String> DisplayBank(Connection conn)
	{
		List<String> listS= new ArrayList<String>();
		List<Bank> list = new ArrayList<Bank>();
		
		String sql = "select * from bank";
		try 
		{
			PreparedStatement ptmt = conn.prepareStatement(sql);
			
			ResultSet rs = ptmt.executeQuery();
			
			while (rs.next())
			{
				Bank bank = new Bank(rs.getString("bankname"),rs.getString("parent"));
				list.add(bank);
			}
			
			Map<String, Bank> bankMap = new HashMap<>();
	        for (Bank bank : list) {
	            bankMap.put(bank.getBankName(), bank);
	        }
			List<Bank> treeList = new ArrayList<>();
	        for (Bank bank : list) {
	            String parentName = bank.getParent();
	            if (parentName == null || parentName=="") {
	                treeList.add(bank); // Gốc cây
	            } else {
	                Bank parentBank = bankMap.get(parentName);
	                parentBank.addChild(bank);
	            }
	        }

	        // In cây danh sách
	      listS= printTree(treeList, 0);
			
			
		} 
		catch (SQLException e) 
		{


			e.printStackTrace();
		}
		
		return listS;
	}

	public static List<String> printTree(List<Bank> banks, int level) {
	    List<String> lis = new ArrayList<>();
	    for (Bank bank : banks) {
	        String s = "";
	        for (int i = 0; i < level; i++) {
	           // s += "&nbsp;&nbsp;"; // In khoảng cách để thể hiện cấp độ
	            s += "&#8203;&nbsp;";
	        }
	        s += bank.getBankName();
	        lis.add(s);
	        if (bank.getChildren() != null && !bank.getChildren().isEmpty()) {
	            List<String> childList = printTree(bank.getChildren(), level + 1); // Đệ quy để in các nút con
	            lis.addAll(childList); // Thêm tất cả các nút con vào danh sách
	        }
	    }
	    return lis;
	}
	public static List<Question> listQuestion(Connection conn, String bank){
		List<Question> list= new ArrayList<>();
		String sql= "SELECT * FROM project.question q join bank_question bq where bq.bankname='" +bank+"' and bq.questionid=q.idquestion";
		try {
			PreparedStatement ptmt= conn.prepareStatement(sql);
			ResultSet rs= ptmt.executeQuery();
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
