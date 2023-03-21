package com.quiz.Services;


import java.util.List;
import java.util.Set;

import com.quiz.Models.User;
import com.quiz.Models.UserRole;
import com.quiz.dtos.UserDto;

public interface StudentServices {

	UserDto createStudent(UserDto student);
	
	UserDto updateStudent (UserDto student, Integer studentid);
	
	void deleteStudent(Integer studentid);
	
	List<UserDto> getAllStudents();
	
	UserDto getStudentById(Integer studentid);
	
	UserDto getJson(String user);
	
	
}
