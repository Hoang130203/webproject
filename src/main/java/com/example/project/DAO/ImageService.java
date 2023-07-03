package com.example.project.DAO;

import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;

import org.springframework.web.multipart.MultipartFile;

import com.example.project.BEAN.CustomMultipartFile;


public class ImageService {
	public static String saveImageContent(MultipartFile file,String questionId) {
		String url="";
		 try {
			 
			
		        String targetDirectory = "src/main/resources/static/images"; // Đường dẫn đến thư mục mục tiêu
		        String originalFileName = file.getOriginalFilename(); // Tên file gốc
		        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // Phần mở rộng (extension) của file
		        String newFileName = questionId + "content"+ fileExtension; // Tạo tên tệp mới ngẫu nhiên
		        Path targetFilePath = Path.of(targetDirectory, newFileName); // Đường dẫn đầy đủ tới tệp mới	
		        
		       url="/images/"+questionId +"content"+ fileExtension;
		       
                Files.copy(file.getInputStream(), targetFilePath , StandardCopyOption.REPLACE_EXISTING);

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		 return url;
	}
	public static String saveImageChoice(int i,MultipartFile file,String questionId) {
		String url="";
		 try {
			 
			
		        String targetDirectory = "src/main/resources/static/images"; // Đường dẫn đến thư mục mục tiêu
		        String originalFileName = file.getOriginalFilename(); // Tên file gốc
		        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // Phần mở rộng (extension) của file
		        String newFileName = questionId + "choice"+i+ fileExtension; 
		        Path targetFilePath = Path.of(targetDirectory, newFileName); // Đường dẫn đầy đủ tới tệp mới	
		        
		       url="/images/"+questionId +"choice"+i+ fileExtension;
		       
               Files.copy(file.getInputStream(), targetFilePath , StandardCopyOption.REPLACE_EXISTING);

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		 return url;
	}
	public static int countblank(ArrayList<String> choicesgoc,int i) {
		int k=0;
		for(int j=0;j<=i-1;j++) {
			if(choicesgoc.get(j).isBlank()) {
				k++;
			}
		}
		return k;
	}
	public static MultipartFile createMultipartFileFromPath(String imagePath) throws IOException {
		   File file = new File(imagePath);

		   MultipartFile multipartFile = new CustomMultipartFile(file);
		    
		    return multipartFile;
	}
	public static String convertMultipartFileToBase64(MultipartFile multipartFile) throws IOException {
	    byte[] fileBytes = multipartFile.getBytes();
	    byte[] base64Bytes = Base64.encodeBase64(fileBytes);
	    String base64String = new String(base64Bytes, StandardCharsets.UTF_8);
	    return base64String;
	}
	public static boolean isImageFileExists(String imagePath) {
	    File file = new File(imagePath);
	    return file.exists() && file.isFile();
	}
	
}
