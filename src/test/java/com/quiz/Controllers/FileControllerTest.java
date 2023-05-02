package com.quiz.Controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.quiz.Services.FileService;
import com.quiz.Services.StudentServices;
import com.quiz.dtos.UserDto;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

	@InjectMocks
	StudentController studentController;

	@Mock
	StudentServices studentServices;

	@Mock
	UserDto userDto;

	@Mock
	FileService fileService;

	MockMultipartFile file = new MockMultipartFile("profile", "test.jpeg", MediaType.IMAGE_JPEG_VALUE,
			"test image".getBytes());

	@Mock
	ServletUriComponentsBuilder builder;

	@Mock
	UriComponentsBuilder builder2;

	@Test
	public void updateUserProfile() throws IOException {
//		String uploadimage = "image.jpeg";
// 
//		Mockito.when(fileService.uploadImage(Mockito.anyString(), Mockito.any())).thenReturn(uploadimage);
//		Mockito.when(studentServices.getJson(Mockito.anyString())).thenReturn(userDto);
//		Mockito.when(studentServices.updateStudent(userDto)).thenReturn(userDto);
//		ResponseEntity<?> uploadProfile = studentController.uploadProfile(Mockito.anyString(), file);
//        assertNotNull(uploadProfile);
//        assertEquals(uploadProfile.getStatusCode(),HttpStatus.OK);
//        assertEquals(uploadProfile.getBody(),userDto);
        
	}

}
