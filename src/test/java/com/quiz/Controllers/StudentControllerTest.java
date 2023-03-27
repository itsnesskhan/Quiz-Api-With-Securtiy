package com.quiz.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.quiz.Models.Name;
import com.quiz.Models.UserRole;
import com.quiz.Repository.StudentRepository;
import com.quiz.Services.FileService;
import com.quiz.Services.StudentServices;
import com.quiz.config.MyUserDetailsService;
import com.quiz.dtos.UserDto;

//@SpringBootTest
@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
public class StudentControllerTest {
	

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentServices studentServices;
	
	@MockBean
	private StudentRepository studentRepository;
	
	@MockBean
	private FileService fileService;
	
	@MockBean
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private WebSecurityConfigurerAdapter webSecurityConfigurerAdapter;
	

	
	@DisplayName("test_Get_All_Students")
	@Test
	@WithMockUser(username = "user", roles = "USER")
	void testGetStudents() throws Exception {
		
		HashSet<UserRole> roles = new HashSet<>();
		UserRole role = UserRole.builder().id(101).name("STUDENT").build();
		
		roles.add(role);
		
		
		when(studentServices.getAllStudents()).thenReturn(List.of(
				
				UserDto.builder()
					.name(Name.builder().fname("Nasser").lname("Khan").build())
					.email("itsnesskhan@gmail.com")
					.password(passwordEncoder.encode("iness"))
					.roles(roles)
					.build()
					
				,
				
				UserDto.builder()
					.name(Name.builder().fname("Mohit").lname("Malve").build())
					.email("mohitmalve@gmail.com")
					.password(passwordEncoder.encode("iammohit"))
					.roles(roles)
					.build()
					
				));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/student")
							.accept(MediaType.APPLICATION_JSON);
		
		ResultActions result = mockMvc.perform(get("/student")
				.with(SecurityMockMvcRequestPostProcessors.user("user"))
				.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
		
	}

}
