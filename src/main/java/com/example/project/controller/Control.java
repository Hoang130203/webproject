package com.example.project.controller;



import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project.BEAN.Bank;
import com.example.project.BEAN.CustomMultipartFile;
import com.example.project.BEAN.Question;
import com.example.project.BEAN.Quiz;
import com.example.project.DAO.BankDao;
import com.example.project.DAO.Export;
import com.example.project.DAO.HomeDao;
import com.example.project.DAO.ImageService;
import com.example.project.DAO.QuestionService;
import com.example.project.DAO.QuizService;
import com.example.project.DAO.Quiz_QuestionService;
import com.example.project.DB.DBConnection;

import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class Control {
	
    @GetMapping("/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        ClassPathResource imageResource = new ClassPathResource(filename);

        if (imageResource.exists()) {
            byte[] imageBytes = Files.readAllBytes(imageResource.getFile().toPath());
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = determineMediaType(filename);
            headers.setContentType(mediaType);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private MediaType determineMediaType(String filename) {
        String extension = FilenameUtils.getExtension(filename);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

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
	@GetMapping("/home/home")
	public String home() {
		return "home";
	}
	@GetMapping("/TurnEditing")
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
				List<String> numberquestion=BankDao.countQuesInBank(conn);
				model.addAttribute("numberquestion", numberquestion);
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
	@GetMapping("/{quizname}/quiz")
	public String page6_1(@PathVariable("quizname") String quizname, Model model
						) {
		model.addAttribute("quizname",quizname);
		try {
			Connection conn= DBConnection.CreateConnection();
			List<Quiz> list= QuizService.listQuiz(conn);
			int timelimit=60;
			for(Quiz q:list) {
				if(q.getQuizName().equals(quizname) && q.getTimeLimit()>0) {
					timelimit=q.getTimeLimit();
				}
			}
			model.addAttribute("timelimit", timelimit);
		} catch (Exception e) {
			// TODO: handle exception
		}
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
				List<String> numberquestion=BankDao.countQuesInBank(conn);
				model.addAttribute("numberquestion", numberquestion);
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
			List<String> numberquestion=BankDao.countQuesInBank(conn);
			model.addAttribute("numberquestion", numberquestion);
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
			List<Quiz> listQuiz= QuizService.listQuiz(conn);
			int timelimit=60;
			for(Quiz q:listQuiz) {
				if(q.getQuizName().equals(quizname)) {
				
					if(q.getTimeLimit()>0) {
						timelimit=q.getTimeLimit();
					}
				}
			}
			
			model.addAttribute("timelimit", timelimit);
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
	public String handleFileUpload(@RequestParam("file") MultipartFile file,RedirectAttributes model)   {
		String error="";
		String fileContent ="";
		ArrayList<Question> listquestion=new ArrayList<>();
		Connection conn=null;
		if (!file.isEmpty()) {
            try {
            	conn= DBConnection.CreateConnection();
            	if(conn==null) {
            		
            	}else {
            		String fileName=file.getOriginalFilename();
            		if(!fileName.toLowerCase().endsWith("docx")&& !fileName.toLowerCase().endsWith("txt")) {
            			error="chọn sai định dạng, hãy chọn tệp đuôi docx hoặc txt!";
            		}else {
            		if(fileName.toLowerCase().endsWith("docx")) {
            			InputStream inputStream = file.getInputStream();
                        XWPFDocument docx = new XWPFDocument(inputStream);
                        XWPFWordExtractor extractor = new XWPFWordExtractor(docx);

                        // Đọc các đoạn văn bản trong tệp DOCX
                        fileContent= extractor.getText();
                        extractor.close();
                        docx.close();
                        inputStream.close();
            		}else {
            			
            			byte[] fileBytes = file.getBytes();
            			
            			// Xử lý nội dung của tệp hoặc lưu vào biến khác
            			fileContent = new String(fileBytes);
            		}
                     
                     listquestion= QuestionService.format(fileContent);
                    System.out.println(listquestion.toString());
                     error=QuestionService.saveQuestion(conn, listquestion,fileContent);
                     if(error.equalsIgnoreCase("không có lỗi nào!")){
                    	 error="không có lỗi, tổng cộng "+listquestion.size()+" câu hỏi";
                     }
            	}
            	}
                // Tiếp tục xử lý hoặc thực hiện các thao tác khác với nội dung tệp

               
            } catch (Exception  e) {
                // Xử lý lỗi nếu có
            	error="error";
            	model.addFlashAttribute("errorfile", error);
                return "redirect:/page2_1";
            }
        }
		model.addFlashAttribute("errorfile", error);
        return "redirect:/page2_1";
    }
	@PostMapping("/createquiz")
	public String createQuiz(@RequestParam("name") String name,Model model,
							@RequestParam("timelimit") int timelimit) {
		int i=1;
		System.out.println(timelimit);
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
			Quiz quiz= new Quiz(name,timelimit);
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
	@PostMapping("/{quiz}/processQuestions")
	public String processQuestion(@RequestParam("selectedQuestions") String encodedQuestions,@RequestParam("quiz") String quiz,
				RedirectAttributes model) {
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
	        model.addFlashAttribute("message", "đã thêm "+jsonArray.length()+" câu hỏi vào bài thi");
	    } catch (UnsupportedEncodingException | JSONException e) {
	        e.printStackTrace();
	        model.addFlashAttribute("message", "không có câu nào được thêm");
	    }
	    
	    return "redirect:/{quiz}/Editquiz";
	}


	@PostMapping("/addQuestionto")
	public String addquestiontobank(@RequestParam("list")String list,
			@RequestParam(value = "imgcontent", required = false) MultipartFile imgcontent,
			@RequestParam(value = "imgchoice1", required = false) MultipartFile imgchoice1,
			@RequestParam(value = "imgchoice2", required = false) MultipartFile imgchoice2,
			@RequestParam(value = "imgchoice3", required = false) MultipartFile imgchoice3,
			@RequestParam(value = "imgchoice4", required = false) MultipartFile imgchoice4,
			@RequestParam(value = "imgchoice5", required = false) MultipartFile imgchoice5,
			RedirectAttributes redirectAttributes){
		try {
			String decodedQuestions = URLDecoder.decode(list, "UTF-8");
			System.out.println(list);
			System.out.println(decodedQuestions);
			JSONArray array= new JSONArray(decodedQuestions);
			
			String questionid=array.getString(0);
			String questioncontent=array.getString(1);
			ArrayList<String> choicesgoc= new ArrayList<>();
			ArrayList<String> choices= new ArrayList<>();
			for(int i=2;i<=6;i++) {
				choicesgoc.add(array.getString(i));
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
		    choicesgoc.add("none");
		    if(imgcontent!=null) {
		    	question.setImageContent(ImageService.saveImageContent(imgcontent, questionid));
		    }
		    if(imgchoice1!=null) {
		    	question.setImageChoice(1,ImageService.saveImageChoice(1, imgchoice1, questionid));
		    }
		    if(imgchoice2!=null) {
		    	question.setImageChoice(2-ImageService.countblank(choicesgoc, 2),ImageService.saveImageChoice(2-ImageService.countblank(choicesgoc, 2), imgchoice2, questionid));
		    }
		    if(imgchoice3!=null) {
		    	question.setImageChoice(3-ImageService.countblank(choicesgoc, 3),ImageService.saveImageChoice(3-ImageService.countblank(choicesgoc, 3), imgchoice3, questionid));
		    }
		    if(imgchoice4!=null) {
		    	question.setImageChoice(4-ImageService.countblank(choicesgoc, 4),ImageService.saveImageChoice(4-ImageService.countblank(choicesgoc, 4), imgchoice4, questionid));
		    }
		    if(imgchoice5!=null) {
		    	question.setImageChoice(5-ImageService.countblank(choicesgoc, 5),ImageService.saveImageChoice(5-ImageService.countblank(choicesgoc, 5), imgchoice5, questionid));
		    }
			QuestionService.savequestion(conn,question, bank)	;
			return "page3_2";
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
		return "page3";
	}

	@GetMapping("/getQuestion2_1")
	public String page2_1(@RequestParam("option") String bank,Model model,
				@RequestParam("check") int check) {
		
		
		List<Question> listquestion= new ArrayList<>();
		if(check==0) {
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
			
		}else {
			listquestion=BankDao.getListWithSubCate(bank);
			model.addAttribute("questionList",listquestion);
		}
		model.addAttribute("bankgoc",bank);
		return "page2_1 :: questionList";
	}
	@GetMapping("/getquestion6_5")
	public String page6_5Request(@RequestParam("option") String bank,
	                             @RequestParam(value = "page", required = false) Integer page,
	                             @RequestParam("check") Integer check,
	                             Model model) {
	    List<Question> listQuestion = new ArrayList<>();
	   
	    if(check==0) {
	    	
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
	    }else {
	    	try {
	    		Connection conn = DBConnection.CreateConnection();
	    		if (conn != null) {
	    			listQuestion =  BankDao.getListWithSubCate(bank);
	    			
	    			
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
	    }
	    	
	    
	    
	    return "page6_5 :: questionList";
	}

	@PostMapping("/{quiz}/addquestion6_5")
	public String addquestion6_5(@RequestParam("number") String number,@RequestParam("quiz")String quiz,
							@RequestParam("bankk") String bank,
							@RequestParam("checkk") Integer check,
							RedirectAttributes model)
	{
		if(check==0) {
			
			QuestionService.addrandomquestion(Integer.parseInt(number), quiz, bank);
			model.addFlashAttribute("message", "đã thêm ngẫu nhiên 1 số câu hỏi vào bài thi(không bao gồm cây con)");
		}else {
			QuestionService.addRandomQWithSubCate(Integer.parseInt(number), quiz, bank);
			model.addFlashAttribute("message",  "đã thêm ngẫu nhiên 1 số câu hỏi vào bài thi(bao gồm cây con)");
		}
		return "redirect:/{quiz}/Editquiz";
	}

	@GetMapping("/{quiz}/export/exportnopass")
	public String exportnopass(@PathVariable("quiz") String quiz, RedirectAttributes model) {
		Export export= new Export(quiz);
		export.expordPdf();
		model.addFlashAttribute("done","done");
		return "redirect:/{quiz}/quiz";
	}
	@GetMapping("/{quiz}/export/exportwithpass")
	public String exportpass(@PathVariable("quiz") String quiz, @RequestParam("pass") String pass,RedirectAttributes model) {
		Export export= new Export(quiz,pass);
		export.expordPdfwithPass();
		model.addFlashAttribute("done", "đã thêm file gắn mật khẩu!");
		return "redirect:/{quiz}/quiz";
	}
	@GetMapping("/{ques}/edit")
	public String editQuestion(@PathVariable("ques") String question,Model model,
					
			@RequestParam(value = "bankgoc", required = false) String bankgoc) {
	
		Question ques= QuestionService.searchQuestion(question);
		model.addAttribute("ques",ques);
		if(bankgoc==null||bankgoc.isEmpty()) {
			model.addAttribute("bankgoc", QuestionService.searchBank(question));
		}else {
			
			model.addAttribute("bankgoc",bankgoc);
		}
		try {
			Connection conn= DBConnection.CreateConnection();
			List<String> list = BankDao.DisplayBank(conn);
			
			model.addAttribute("list",list);
			if(ImageService.isImageFileExists("src/main/resources/static"+ques.getImageContent())) {
				model.addAttribute("imagecontent", ImageService.convertMultipartFileToBase64(new CustomMultipartFile(new File("src/main/resources/static"+ques.getImageContent()))));				
			}
			if(ImageService.isImageFileExists("src/main/resources/static"+ques.getImageChoice1())) {
				model.addAttribute("imagechoice1", ImageService.convertMultipartFileToBase64(new CustomMultipartFile(new File("src/main/resources/static"+ques.getImageChoice1()))));
				
			}
			if(ImageService.isImageFileExists("src/main/resources/static"+ques.getImageChoice2())) {
				model.addAttribute("imagechoice2", ImageService.convertMultipartFileToBase64(new CustomMultipartFile(new File("src/main/resources/static"+ques.getImageChoice2()))));
				
			}
			if(ImageService.isImageFileExists("src/main/resources/static"+ques.getImageChoice3())) {
				model.addAttribute("imagechoice3", ImageService.convertMultipartFileToBase64(new CustomMultipartFile(new File("src/main/resources/static"+ques.getImageChoice3()))));
				
			}
			if(ImageService.isImageFileExists("src/main/resources/static"+ques.getImageChoice4())) {
				
				model.addAttribute("imagechoice4", ImageService.convertMultipartFileToBase64(new CustomMultipartFile(new File("src/main/resources/static"+ques.getImageChoice4()))));
			}
			if(ImageService.isImageFileExists("src/main/resources/static"+ques.getImageChoice5())) {
				model.addAttribute("imagechoice5", ImageService.convertMultipartFileToBase64(new CustomMultipartFile(new File("src/main/resources/static"+ques.getImageChoice5()))));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "editquestion";
	} 
	@PostMapping("/editquestion")
	public String editQuestion(@RequestParam("bankgoc") String bankgoc,@RequestParam("list") String list,RedirectAttributes redirectAttributes,
		@RequestParam(value = "imgcontent", required = false) MultipartFile imgcontent,
			@RequestParam(value = "imgchoice1", required = false) MultipartFile imgchoice1,
			@RequestParam(value = "imgchoice2", required = false) MultipartFile imgchoice2,
			@RequestParam(value = "imgchoice3", required = false) MultipartFile imgchoice3,
			@RequestParam(value = "imgchoice4", required = false) MultipartFile imgchoice4,
			@RequestParam(value = "imgchoice5", required = false) MultipartFile imgchoice5
		) {
			
		try {
			Connection conn= DBConnection.CreateConnection();
			String decodedQuestions = URLDecoder.decode(list, "UTF-8");
			System.out.println(decodedQuestions);
			
			JSONArray array= new JSONArray(decodedQuestions);
			
			String questionid=array.getString(0);
			String questioncontent=array.getString(1);
			ArrayList<String> choices= new ArrayList<>();
			ArrayList<String> choicesgoc= new ArrayList<>();
			for(int i=2;i<=6;i++) {
				choicesgoc.add(array.getString(i));
				if(!array.getString(i).isBlank()) {
					choices.add(array.getString(i));
				}
			}
			String answer="ANSWER: "+array.getString(7);
			String bank= array.getString(8);
			Question ques= QuestionService.searchQuestion(questionid);
			String imgcon= ques.getImageContent();
			String imgch1= ques.getImageChoice1();
			String imgch2= ques.getImageChoice2();
			String imgch3= ques.getImageChoice3();
			String imgch4= ques.getImageChoice4();
			String imgch5= ques.getImageChoice5();
			QuestionService.removequestion(array.getString(9));
			System.out.println(array.getString(9));
			Question question= new Question(questionid, questioncontent, choices, answer);
			
		 if(imgcontent!=null) {
			    	question.setImageContent(ImageService.saveImageContent(imgcontent, questionid));
			    	
			    }else  {
			    	
			    	question.setImageContent(imgcon);
			    	
			    }
		 	    if(imgchoice1!=null) {
			    	question.setImageChoice(1-ImageService.countblank(choicesgoc, 1),ImageService.saveImageChoice(1-ImageService.countblank(choicesgoc, 1), imgchoice1, questionid));
			    	
		 	    }else {
			    	question.setImageChoice1(imgch1);
			    }
			    if(imgchoice2!=null) {
			    	question.setImageChoice(2-ImageService.countblank(choicesgoc, 2),ImageService.saveImageChoice(2-ImageService.countblank(choicesgoc, 2), imgchoice2, questionid));
			    	
			    }else {
			    	question.setImageChoice2(imgch2);
			    }
			    if(imgchoice3!=null) {
			    	question.setImageChoice(3-ImageService.countblank(choicesgoc, 3),ImageService.saveImageChoice(3-ImageService.countblank(choicesgoc, 3), imgchoice3, questionid));
			    	
			    }else {
			    	question.setImageChoice3(imgch3);
			    }
			    if(imgchoice4!=null) {
			    	question.setImageChoice(4-ImageService.countblank(choicesgoc, 4),ImageService.saveImageChoice(4-ImageService.countblank(choicesgoc, 4), imgchoice4, questionid));
			    	
			    }else {
			    	question.setImageChoice4(imgch4);
			    }
			    if(imgchoice5!=null) {
			    	question.setImageChoice(5-ImageService.countblank(choicesgoc, 5),ImageService.saveImageChoice(5-ImageService.countblank(choicesgoc, 5), imgchoice5, questionid));
			    	
			    } else {
			    	question.setImageChoice5(imgch5);
			    }
			QuestionService.savequestion(conn, question, bank);
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
			
			
			
	}catch (Exception e) {
		// TODO: handle exception
	}
		return "redirect:/editquestion";
	}
	@GetMapping("/{quiz}/{question}/delete")
	public String deleteQuestioninQuiz(@PathVariable("quiz") String quiz,
			@PathVariable("question")String question,Model redirectAttributes) {
	
		
		try {
			QuizService.deleteQuestionInQuiz(quiz, question);
			
			
			redirectAttributes.addAttribute("message","deleted");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/{quiz}/Editquiz";
	}
	
}
