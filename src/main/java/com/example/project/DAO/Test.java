package com.example.project.DAO;

import java.sql.Connection;
import java.util.List;

import javax.swing.JOptionPane;

import com.example.project.DB.DBConnection;

public class Test {
	public static void main(String[] args) {
		System.out.println("212r4");
		try {
			Connection conn = DBConnection.CreateConnection();
			if(conn==null) {
				JOptionPane.showMessageDialog(null,"NULLL");

//				String name= request.getParameter("username");
				
			}else {
				System.out.println("212r4");
				List<String> list = BankDao.DisplayBank(conn);
				System.out.println("212r4");
				for(String s:list) {
					System.out.println(s);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
