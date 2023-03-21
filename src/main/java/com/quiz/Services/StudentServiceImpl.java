package com.quiz.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.Exceptions.ResourceNotFoundException;
import com.quiz.Exceptions.UserAlreadyExistException;
import com.quiz.Models.Name;
import com.quiz.Models.User;
import com.quiz.Models.UserRole;
import com.quiz.Repository.StudentRepository;
import com.quiz.Repository.UserRolesRepository;
import com.quiz.config.PasswordEncoder;
import com.quiz.dtos.UserDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
//@Transactional
public class StudentServiceImpl implements StudentServices {

	private static String sourceName = "Student";
	private static String fieldName = "id";

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRolesRepository userRolesRepository;

	@Override
	public UserDto getJson(String user) {
		UserDto userJson = new UserDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			userJson = mapper.readValue(user, UserDto.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return userJson;
	}

	@Override
	public UserDto createStudent(UserDto studentDto) {
		User student = this.dtoToStudent(studentDto);
		User user = studentRepository.findByEmail(studentDto.getEmail());
		if (user !=null) {
			throw  new UserAlreadyExistException(studentDto.getEmail());
		}
		HashSet<UserRole> set = new HashSet<>();
		UserRole userRole = new UserRole(1001, "STUDENT");
		UserRole role = userRolesRepository.save(userRole);
		set.add(role);
		student.setRoles(set);
		student.setProfile("default.jpg");
		student.setActive(true);
		student.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(studentDto.getPassword()));
		user = studentRepository.save(student);	
		return this.studentToDto(user);
	}

	@Override
	public UserDto updateStudent(UserDto studentDto, Integer studentid) {
		User student = this.studentRepository.findById(studentid)
				.orElseThrow(() -> new ResourceNotFoundException(sourceName, fieldName, studentid));
		student.setName(studentDto.getName());
		student.setEmail(studentDto.getEmail());
		if (studentDto.getProfile()!=null) {
			student.setProfile(studentDto.getProfile());			
			student.setProfileUrl(studentDto.getProfileUrl());
		}
		User user = this.studentRepository.save(student);
		return this.studentToDto(user);
	}

	@Override
	public void deleteStudent(Integer studentid) {
		User student = this.studentRepository.findById(studentid)
				.orElseThrow(() -> new ResourceNotFoundException(sourceName, fieldName, studentid));
		this.studentRepository.delete(student);
	}

	@Override
	public List<UserDto> getAllStudents() {
		log.info("Fetching all users");
		List<User> list = this.studentRepository.findAll();
		return list.stream().map(user-> studentToDto(user)).collect(Collectors.toList());
		
	}

	@Override
	public UserDto getStudentById(Integer studentid) {
		User user = this.studentRepository.findById(studentid)
				.orElseThrow(() -> new ResourceNotFoundException(sourceName, fieldName, studentid));
		return this.studentToDto(user);
	}

	private User dtoToStudent(UserDto userDto) {
		return this.modelMapper.map(userDto, User.class);
	}

	private UserDto studentToDto(User user) {
		return this.modelMapper.map(user, UserDto.class);
	}

}
