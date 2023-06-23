package com.example.project.controller;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.BEAN.Question;
import com.example.project.BEAN.Quiz;
import com.example.project.DAO.BankDao;
import com.example.project.DAO.HomeDao;
import com.example.project.DAO.QuestionService;
import com.example.project.DAO.QuizService;
import com.example.project.DB.DBConnection;


@Controller
public class Control {
	
	
	@GetMapping("/")
	public String page1(Model model) {
		try {
			Connection conn = DBConnection.CreateConnection();
			if(conn==null) {
				JOptionPane.showMessageDialog(null,"NULLL");

//				String name= request.getParameter("username");
				
			}else {
				List<Quiz> list = HomeDao.DisplayQuiz(conn);
				
				model.addAttribute("list",list);
				
				return "page1_1";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "page1_1";
	}
	@PostMapping("/TurnEditing")
	public String page2(){
		return "TurnEditing";
	}
	@GetMapping("/page2_1")
	public String page2_1(Model model) {
		try {
			Connection conn = DBConnection.CreateConnection();
			if(conn==null) {
				JOptionPane.showMessageDialog(null,"NULLL");

//				String name= request.getParameter("username");
				
			}else {
				List<String> list = BankDao.DisplayBank(conn);
				
				model.addAttribute("list",list);
				
				return "page2_1";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "page2_1";
		
	}
	@GetMapping("/page3_2")
	public String page3_2() {
		return "page3_2";
	}
	@GetMapping("/{quizname}")
	public String page6_1(@PathVariable("quizname") String quizname, Model model) {
		model.addAttribute("quizname",quizname);
		return "page6_1";
	}
	@GetMapping("/{quiz}/Editquiz")
	public String page6_2a(@PathVariable("quiz") String quiz,Model model) {
		model.addAttribute("quiz", quiz);
		List<Question> list= new ArrayList<>();
		try {
			Connection conn = DBConnection.CreateConnection();
			if(conn==null) {
				JOptionPane.showMessageDialog(null,"NULLL");

//				String name= request.getParameter("username");
				
			}else {
				list = QuizService.listQuestion(conn,quiz);
				model.addAttribute("list", list);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "page6_2a";
	}
	@GetMapping("/{quiz}/addfrombank")
	public String page6_3(@PathVariable("quiz") String quiz,Model model) {
		model.addAttribute("quiz", quiz);
		try {
			Connection conn = DBConnection.CreateConnection();
			if(conn==null) {
				JOptionPane.showMessageDialog(null,"NULLL");

//				String name= request.getParameter("username");
				
			}else {
				List<String> list = BankDao.DisplayBank(conn);
				
				model.addAttribute("list",list);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "page6_3a";
	}
	@GetMapping("/getQuestions")
	public String page6_3_1(@RequestParam("option") String bank,Model model) {
		
		
		List<Question> listquestion= new ArrayList<>();
		
		try {
			Connection conn= DBConnection.CreateConnection();
			if(conn==null) {
				
			}else {
				listquestion= BankDao.listQuestion(conn, bank);
				model.addAttribute("questionList",listquestion);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "page6_3a :: questionList";
	}
	@GetMapping("/{quiz}/addrandomquestion")
	public String page6_5(@PathVariable("quiz") String quiz,Model model) {
		
		model.addAttribute("quiz",quiz);
		return "page6_5";
	}
	@GetMapping("/{quizname}/preview")
	public String page7_3(@PathVariable("quizname") String quizname,Model model) {
		
	model.addAttribute("quizname", quizname);
	return "page7_3";
	}
	@GetMapping("/{quizname}/xoa")
	public String delete(@PathVariable("quizname") String quizname,Model model) {
		try {
			Connection conn = DBConnection.CreateConnection();
			if(conn==null) {
				JOptionPane.showMessageDialog(null,"NULLL");

//				String name= request.getParameter("username");
				
			}else {
				HomeDao.DeleteQuiz(conn, quizname);
				List<Quiz> list = HomeDao.DisplayQuiz(conn);
				for(Quiz q:list) {
					if(q.getQuizName().equalsIgnoreCase(quizname)) {
						
					}
				}
				model.addAttribute("list",list);
				int i=1;
				model.addAttribute("i", i);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "redirect:/";
	}
	//nhan file
	@PostMapping("/import")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) throws SQLException {
		if (!file.isEmpty()) {
            try {
            	Connection conn= DBConnection.CreateConnection();
            	if(conn==null) {
            		
            	}else {
            		 byte[] fileBytes = file.getBytes();

                     // Xử lý nội dung của tệp hoặc lưu vào biến khác
                     String fileContent = new String(fileBytes);
                     ArrayList<Question> listquestion= QuestionService.format(fileContent);
                     
                     QuestionService.saveQuestion(conn, listquestion);
            	}
               
                // Tiếp tục xử lý hoặc thực hiện các thao tác khác với nội dung tệp

               
            } catch (IOException e) {
                // Xử lý lỗi nếu có
                
            }
        }
        return "page6_1";
    }
	
}
