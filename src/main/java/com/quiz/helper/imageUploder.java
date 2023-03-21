package com.quiz.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;

@Component
public class imageUploder {

	public boolean uploadImage(InputStream fileStream, String path) {
		boolean f = false;
		try {
//			byte[] data = new byte[fileStream.available()];
//			
//			fileStream.read(data);
//			
//			FileOutputStream outputStream = new FileOutputStream(path);
//			outputStream.write(data);
//			
//			outputStream.flush();
//			outputStream.close();
//			
			Files.copy(fileStream, Paths.get(path), StandardCopyOption.REPLACE_EXISTING); // same work just in one line
			f = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}
}
