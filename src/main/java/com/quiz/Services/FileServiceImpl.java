package com.quiz.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		String filename = file.getOriginalFilename();
		
		String randomuid = UUID.randomUUID().toString();
	
		String newfileName = randomuid.concat(filename.substring(filename.lastIndexOf(".")));
		
		String filePath = path+File.separator+newfileName;
		
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(filePath),StandardCopyOption.REPLACE_EXISTING);
		
		return newfileName;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
	     String fullpathString =	path+File.separator+filename;
	     FileInputStream inputStream= new FileInputStream(fullpathString);
		return inputStream;
	}

}
