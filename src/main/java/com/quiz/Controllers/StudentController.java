package com.quiz.Controllers;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.hibernate.loader.entity.NaturalIdEntityJoinWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.quiz.Models.User;
import com.quiz.Repository.StudentRepository;
import com.quiz.Services.FileService;
import com.quiz.Services.StudentServices;
import com.quiz.dtos.UserDto;
import com.quiz.helper.imageUploder;

@Controller
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class StudentController {

	private StudentServices studentServices;

	private StudentRepository studentRepository;

	private FileService fileService;
	
	@Autowired
	public StudentController(StudentServices studentServices, StudentRepository studentRepository,
			FileService fileService) {
		this.studentServices = studentServices;
		this.studentRepository = studentRepository;
		this.fileService = fileService;
	}

	private String path;
	@Value("${project.image}")
	private String proppath;

	@GetMapping("/student/{stuId}")
	public ResponseEntity<UserDto> getStudent(@PathVariable("stuId") Integer stuId) {
		UserDto user = this.studentServices.getStudentById(stuId);
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}

	@PostMapping("/student")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		UserDto studentDto = this.studentServices.createStudent(userDto);

		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student/{id}")
				.buildAndExpand(studentDto.getId()).toUriString());

		return ResponseEntity.created(uri).body(studentDto);
	}

	@GetMapping("/student")
	public ResponseEntity<List<UserDto>> getStudents() {
		List<UserDto> students = this.studentServices.getAllStudents();
		return new ResponseEntity<List<UserDto>>(students, HttpStatus.OK);
	}

	@PutMapping(path = "/student")
	public ResponseEntity<?> updateStudent(@RequestBody UserDto user) {
		UserDto updateStudent = this.studentServices.updateStudent(user);
		return ResponseEntity.status(HttpStatus.OK).body(updateStudent);
	}

	@PostMapping(path = "/student/profile")
	public ResponseEntity<?> uploadProfile(@RequestPart("user") String userString,@RequestPart("profile") MultipartFile file) {
		try {

			UserDto userDto = studentServices.getJson(userString);
			System.out.println(userDto);
			if (!file.isEmpty()) {

//				path = new ClassPathResource("static/images").getFile().getAbsolutePath();
				path = new ClassPathResource("static/images").getPath();
				final String UPLOAD_DIR = path + File.separator + "profile";

				String uploadImage = fileService.uploadImage(UPLOAD_DIR, file);

				userDto.setProfile(uploadImage);

					String dowloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/student/profile/image/")
							.path(uploadImage).toUriString();
				System.out.println(dowloadUrl);
				userDto.setProfileUrl(dowloadUrl);
			}

			UserDto updateStudent = studentServices.updateStudent(userDto);
			return ResponseEntity.ok(updateStudent);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Something went wrong, try again");
	}

	@GetMapping(value = "/student/profile/image/{imageName}", produces = { MediaType.IMAGE_JPEG_VALUE })
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {

		String path = new ClassPathResource("static/images").getFile().getAbsolutePath();
		path = path + File.separator + "profile";

		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

	@DeleteMapping("student/{stuId}")
	public ResponseEntity<?> deleteStudent(@PathVariable("stuId") Integer stuId) {
		this.studentServices.deleteStudent(stuId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/current-user")
	public ResponseEntity<User> getCurrentUser(Principal principal) {
		User user = studentRepository.findByEmail(principal.getName());
		return ResponseEntity.ok(user);
	}

}
