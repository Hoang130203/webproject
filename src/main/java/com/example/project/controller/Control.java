package com.example.project.controller;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project.BEAN.Bank;
import com.example.project.BEAN.Question;
import com.example.project.BEAN.Quiz;
import com.example.project.DAO.BankDao;
import com.example.project.DAO.HomeDao;
import com.example.project.DAO.QuestionService;
import com.example.project.DAO.QuizService;
import com.example.project.DAO.Quiz_QuestionService;
import com.example.project.DB.DBConnection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.JsonArray;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
	public String page3_2(Model model) {
		try {
			Connection conn= DBConnection.CreateConnection();
			List<String> list = BankDao.DisplayBank(conn);
			
			model.addAttribute("list",list);
		} catch (Exception e) {
			// TODO: handle exception
		}
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
		try {
			Connection conn= DBConnection.CreateConnection();
			List<String> list= BankDao.DisplayBank(conn);
			model.addAttribute("list", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "page6_5";
	}
	@GetMapping("/{quizname}/preview")
	public String page7_3(@PathVariable("quizname") String quizname,Model model) {
		try {
			Connection conn= DBConnection.CreateConnection();
			
			List<Question> list= QuizService.listQuestion(conn, quizname);
			model.addAttribute("list", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
	public String handleFileUpload(@RequestParam("file") MultipartFile file,RedirectAttributes model) throws SQLException {
		String error="";
		if (!file.isEmpty()) {
            try {
            	Connection conn= DBConnection.CreateConnection();
            	if(conn==null) {
            		
            	}else {
            		 byte[] fileBytes = file.getBytes();

                     // Xử lý nội dung của tệp hoặc lưu vào biến khác
                     String fileContent = new String(fileBytes);
                     ArrayList<Question> listquestion= QuestionService.format(fileContent);
                     
                     error=QuestionService.saveQuestion(conn, listquestion,fileContent);
                     if(error.equalsIgnoreCase("không có lỗi nào!")){
                    	 error="không có lỗi ";
                     }
            	}
               
                // Tiếp tục xử lý hoặc thực hiện các thao tác khác với nội dung tệp

               
            } catch (IOException e) {
                // Xử lý lỗi nếu có
                
            }
        }
		model.addFlashAttribute("errorfile", error);
        return "redirect:/page2_1";
    }
	@PostMapping("/createquiz")
	public String createQuiz(@RequestParam("name") String name,Model model) {
		int i=1;
		try {
			Connection conn= DBConnection.CreateConnection();
			List<Quiz> list= new ArrayList<>();
			list= QuizService.listQuiz(conn);
			for(Quiz quiz:list) {
				if(quiz.getQuizName().equalsIgnoreCase(name)) {
					i=0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(name.isEmpty()||name.isBlank()) {
			model.addAttribute("error","vui lòng nhập tên bài kiểm tra!");
			return "TurnEditing";
		} else if(i==0){
			model.addAttribute("error", "tên đã tồn tại");
			return "TurnEditing";
		}
		else {
			Quiz quiz= new Quiz(name);
			try {
				Connection conn= DBConnection.CreateConnection();
				QuizService.addQuiz(conn, quiz);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "redirect:/";
		}
		
	}
	@PostMapping("/addbank")
	public String addBank(@RequestParam("bankname") String bankname, @RequestParam("parent") String parent,RedirectAttributes redirectAttributes) {
	    try {
	        Connection conn = DBConnection.CreateConnection();
	        List<Bank> list = BankDao.listBank(conn);

	        for (Bank bank : list) {
	            if (bank.getBankName().equalsIgnoreCase(bankname.trim())) {
	            	redirectAttributes.addFlashAttribute("error","lỗi");
	                return "none";
	            }
	        }
	        
	        if (bankname.isBlank() || bankname.isEmpty()) {
	        	redirectAttributes.addFlashAttribute("error","lỗi");
	        	return "page2";
	        }
	        else if (parent.equalsIgnoreCase("Default")) {
	            Bank bank = new Bank(bankname, null);
	            BankDao.addBank(conn, bank);
	            return "page2_1";
	        } else {
	            Bank bank = new Bank(bankname, parent.trim());
	            BankDao.addBank(conn, bank);
	            return "page2_1";
	        }

	        
	    } catch (Exception e) {
	        // Xử lý ngoại lệ
	    	redirectAttributes.addFlashAttribute("error","lỗi");
	        return "page2_1";
	    }
	}
	@PostMapping("/processQuestions")
	public String processQuestion(@RequestParam("selectedQuestions") String encodedQuestions,@RequestParam("quiz") String quiz) {
	    try {
	        // Giải mã chuỗi URL-encoded
	    	
	        String decodedQuestions = URLDecoder.decode(encodedQuestions, "UTF-8");
	        System.out.println(encodedQuestions);
	        System.out.println(decodedQuestions);
	        // Chuyển đổi chuỗi giải mã thành một mảng JSON
	        JSONArray jsonArray = new JSONArray(decodedQuestions);

	        // Kiểm tra nếu mảng JSON không rỗng
	        if (jsonArray.length() > 0) {
	            for (int i = 0; i < jsonArray.length(); i++) {
	                // Kiểm tra nếu phần tử là một chuỗi
	                if (jsonArray.get(i) instanceof String) {
	                    String questionID = jsonArray.getString(i);
	                    System.out.println(questionID);
	                    Quiz_QuestionService.addQuestion(quiz, questionID);
	                } else {
	                    System.out.println("Invalid JSON element at index " + i);
	                }
	            }
	        } else {
	            System.out.println("Empty JSON array");
	        }
	    } catch (UnsupportedEncodingException | JSONException e) {
	        e.printStackTrace();
	    }

	    return "redirect:/";
	}


	@PostMapping("/addQuestionto")
	public String addquestiontobank(@RequestParam("list")String list, RedirectAttributes redirectAttributes){
		try {
			String decodedQuestions = URLDecoder.decode(list, "UTF-8");
			System.out.println(list);
			System.out.println(decodedQuestions);
			JSONArray array= new JSONArray(decodedQuestions);
			
			String questionid=array.getString(0);
			String questioncontent=array.getString(1);
			ArrayList<String> choices= new ArrayList<>();
			for(int i=2;i<=6;i++) {
				if(!array.getString(i).isBlank()) {
					choices.add(array.getString(i));
				}
			}
			String answer="ANSWER: "+array.getString(7);
			String bank= array.getString(8);
			if(bank.equalsIgnoreCase("default")) {
				return "page3";
			}
			else if(questionid.isBlank()) {
				redirectAttributes.addFlashAttribute("error", "nhập ít nhất 2 lựa chọn");
				return "page3";
			}else 
			if(questioncontent.isBlank()) {
				redirectAttributes.addFlashAttribute("error", "nhập ít nhất 2 lựa chọn");
				return "page3";
			}else 
			if(answer.equals("ANSWER: ")||choices.size()<2) {
				redirectAttributes.addFlashAttribute("error", "nhập ít nhất 2 lựa chọn");
				return "page3";
			}
			Connection conn= DBConnection.CreateConnection();
			List<Question> listquestion= BankDao.listQuestion(conn, bank);
			for(Question q:listquestion) {
				if(q.getQuestionID().equals(questionid)) {
					return "page3";
				}
			}
		    Question question= new Question(questionid, questioncontent, choices, answer);
		    
			QuestionService.savequestion(conn,question, bank)	;
			return "page3_2";
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
		return "page3_2";
	}

	@GetMapping("/getQuestion2_1")
	public String page2_1(@RequestParam("option") String bank,Model model) {
		
		
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
		return "page2_1 :: questionList";
	}
	@GetMapping("/getquestion6_5")
	public String page6_5Request(@RequestParam("option") String bank,
	                             @RequestParam(value = "page", required = false) Integer page,
	                             Model model) {
	    List<Question> listQuestion = new ArrayList<>();

	    try {
	        Connection conn = DBConnection.CreateConnection();
	        if (conn != null) {
	            listQuestion = BankDao.listQuestion(conn, bank);
	           
	            
	            // Phân trang
	            int pageSize = 10; // Số câu hỏi trên mỗi trang
	            int totalPages = (int) Math.ceil((double) listQuestion.size() / pageSize);

	            if (page == null || page < 1  ) {
	                page = 1; // Trang mặc định
	            }else if(page > totalPages) {
	            	 page= totalPages;
	            }

	            int startIndex = (page - 1) * pageSize;
	            int endIndex = Math.min(startIndex + pageSize, listQuestion.size());
	            List<Question> paginatedList = listQuestion.subList(startIndex, endIndex);
	       //     List<Question> paginatedList = listQuestion.subList(0,Math.min(0 + pageSize, listQuestion.size()));
	            model.addAttribute("questionList", paginatedList);
	            
	            model.addAttribute("currentPage", page.toString());
	            model.addAttribute("totalPages", totalPages);
	        }
	    } catch (Exception e) {
	        // Xử lý ngoại lệ
	    }
	    
	    return "page6_5 :: questionList";
	}

	@PostMapping("/addquestion6_5")
	public String addquestion6_5(@RequestParam("number") String number,@RequestParam("quiz")String quiz,
							@RequestParam("bankk") String bank)
	{
		System.out.println(quiz);
		System.out.println(bank);
		System.out.println(number);
		QuestionService.addrandomquestion(Integer.parseInt(number), quiz, bank);
		return "redirect:/";
	}
}
