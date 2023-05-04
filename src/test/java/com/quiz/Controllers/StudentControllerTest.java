package com.quiz.Controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.Exceptions.ResourceNotFoundException;
import com.quiz.Models.Name;
import com.quiz.Models.UserRole;
import com.quiz.Repository.StudentRepository;
import com.quiz.Services.FileService;
import com.quiz.Services.StudentServices;
import com.quiz.dtos.UserDto;


@SpringBootTest
public class StudentControllerTest {

	
	private MockMvc mockMvc;

	@MockBean
	private StudentServices studentServices;

	@Mock
	private StudentRepository studentRepository;

	@Mock
	private FileService fileService;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;

	private List<UserDto> userslist = new ArrayList<>();
	
	private StudentController studentController;

	@BeforeEach
	private void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		studentController = new StudentController(studentServices, studentRepository, fileService);
		

		HashSet<UserRole> roles = new HashSet<>();
		UserRole role = UserRole.builder().id(101).name("STUDENT").build();
		roles.add(role);

		UserDto user1 = UserDto.builder().id(1).name(Name.builder().fname("Nasser").lname("Khan").build())
				.email("itsnesskhan@gmail.com").password("iness").roles(roles).build();

		userslist.add(user1);

		UserDto user2 = UserDto.builder().id(2).name(Name.builder().fname("Mohit").lname("Malve").build())
				.email("mohitmalve@gmail.com").password("iammohit").roles(roles).build();

		userslist.add(user2);
	}

	@DisplayName("test_Get_All_Students")
	@Test
	void testGetStudents() throws Exception {
		
		when(studentServices.getAllStudents()).thenReturn(List.of(userslist.get(0),userslist.get(1)));
		
		
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/student")
							.accept(MediaType.APPLICATION_JSON);
		
		String contentAsString = mockMvc.perform(request)
				.andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value("itsnesskhan@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].password").value("iness"))
                .andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		
		System.out.println(contentAsString);
		
	}

	@DisplayName("test_save_Student")
	@Test
	void testSaveStudent() throws Exception {

		String jsonRequest = mapper.writeValueAsString(userslist.get(0));

		when(studentServices.createStudent(ArgumentMatchers.any())).thenReturn(userslist.get(0));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
								.post("/api/v1/student")
								.content(jsonRequest)
								.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.equalTo("itsnesskhan@gmail.com")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)))
				.andExpect(status().isCreated())
				.andReturn();

	}

	@DisplayName("test_get_student_by_id_exist")
	@Test
	void testgetStudentByIdExist() throws Exception {
		
		when(studentServices.getStudentById(ArgumentMatchers.any())).thenReturn(userslist.get(0));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/student/1")
												.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.equalTo("itsnesskhan@gmail.com")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)))
				.andExpect(status().isOk())
				.andReturn();
		
		
	}

	@DisplayName("test_get_student_by_id_not_exist")
	@Test
	void testgetStudentByIdNotExist() throws Exception {
		
		when(studentServices.getStudentById(ArgumentMatchers.any())).thenThrow(new ResourceNotFoundException("student", "id", 3));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/student/3")
												.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isNotFound())
				.andReturn();
	}
	
	@DisplayName("test_update_student")
	@Test
	void updateStudentById() throws Exception {
		
		UserDto userDto = userslist.get(0);
		
		UserDto updatedUser = UserDto.builder()
			.name(Name.builder().fname("Nasir").lname("Khan").build())
			.email("mnk56250@gmail.com").build();
		
		String jsonRequest = mapper.writeValueAsString(userslist.get(0));

		when(studentServices.updateStudent(ArgumentMatchers.any())).thenReturn(updatedUser);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/v1/student")
												.content(jsonRequest)
												.contentType(MediaType.APPLICATION_JSON)
												.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.equalTo("mnk56250@gmail.com")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name.fname", Matchers.equalTo("Nasir")))
				.andExpect(status().isOk())
				.andReturn();
		
	}
	
	@DisplayName("test_update_student_profile_mockito")
	@Test
	void updateStudentProfile() throws Exception {
		
			UserDto userDto = UserDto.builder()
				.id(1)
				.name(Name.builder().fname("Nasir").lname("Khan").build())
				.email("mnk56250@gmail.com").build();
		    String userJson = mapper.writeValueAsString(userDto);
		    
	        MultipartFile file = Mockito.mock(MultipartFile.class);

		   
	        Mockito.when(studentServices.getJson(anyString())).thenReturn(userDto);
	        Mockito.when(fileService.uploadImage(Mockito.anyString(), Mockito.any(MultipartFile.class)))
	               .thenReturn("user_test.jpg");
	        Mockito.when(studentServices.updateStudent(userDto)).thenReturn(userDto);

	        ResponseEntity<?> response = studentController.uploadProfile(userJson, file);

	        assertNotNull(response);
	        assertEquals(response.getStatusCode(),HttpStatus.OK);
	        assertEquals(userDto, response.getBody());

	}
	
	@DisplayName("test_update_student_profile_mockmvc")
	@Test
    public void testUploadProfile() throws Exception {
//
//        String userString = "{\r\n"
//        		+ "    \"id\": 1,\r\n"
//        		+ "    \"name\": {\r\n"
//        		+ "        \"fname\": \"Nasser\",\r\n"
//        		+ "        \"lname\": \"Khan\"\r\n"
//        		+ "    },\r\n"
//        		+ "    \"email\": \"itsnesskhan@gmail.com\",\r\n"
//        		+ "}";
//
//        MockMultipartFile file = new MockMultipartFile("profile", "profile.jpg", MediaType.IMAGE_JPEG_VALUE,
//                "profile".getBytes());
//        
//        when(fileService.uploadImage(anyString(), any(MultipartFile.class))).thenReturn("profile.jpg");
//        when(studentServices.getJson(userString)).thenReturn(userslist.get(0));
//        when(studentServices.updateStudent(userslist.get(0))).thenReturn(userslist.get(0));
//
//        MvcResult mvcResult = mockMvc.perform(multipart("/api/v1/student/profile")
//                .file(file)
//                .file("user", userString.getBytes()))
//        		.andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseBody = mvcResult.getResponse().getContentAsString();
//        UserDto updatedUserDto = mapper.readValue(responseBody, UserDto.class);
//        System.out.println("Updated user: " + updatedUserDto);
//
//        assertThat(updatedUserDto.getEmail()).isEqualTo(userslist.get(0).getEmail());
//        assertThat(updatedUserDto.getProfileUrl()).isNotNull();
//        assertThat(updatedUserDto.getProfileUrl()).contains("student/profile/image/");

    }

	
	
	@DisplayName("test_delete_student_if_student_exist")
	@Test
	void deleteStudentById() throws Exception {
		
		doNothing().when(studentServices).deleteStudent(ArgumentMatchers.anyInt());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/student/1")
												.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		verify(studentServices,times(1)).deleteStudent(1);
	}
	
	@DisplayName("test_get_student_by_id_does_not_exist")
	@Test
	void testdeleteStudentByIdNotExist() throws Exception {
		
		doThrow(new ResourceNotFoundException("student", "id", 3)).when(studentServices).deleteStudent(3);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/student/3")
												.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request)
				.andExpect(status().isNotFound())
				.andReturn();
		
		verify(studentServices,times(1)).deleteStudent(3);
	}

}
