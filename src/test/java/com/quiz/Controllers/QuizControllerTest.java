//package com.quiz.Controllers;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.CALLS_REAL_METHODS;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.quiz.Models.Categories;
//import com.quiz.Models.Quiz;
//import com.quiz.Repository.ResultRepository;
//import com.quiz.Repository.StudentRepository;
//import com.quiz.Services.QuizService;
//
////@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//class QuizControllerTest {
//
//
//	
//	@Mock
//	private QuizService quizService;
//
//	@InjectMocks
//	private QuizController quizController;
//
//	private List<Quiz> quizs;
//
//	@BeforeEach
//	void setUp() throws Exception {
//		Quiz quiz1 = Quiz.builder().qid(1).title("test Quiz 1").numOfQuestion("10").active(true)
//				.category(Categories.builder().cid(101).name("testcat").build()).build();
//		
//		Quiz quiz2 = Quiz.builder().qid(2).title("test Quiz 2").numOfQuestion("10").active(true)
//				.category(Categories.builder().cid(101).name("testcat").build()).build();
//
//		quizs = List.of(quiz1,quiz2);
//	}
//
//	@DisplayName("get All Quizs")
//	@Test
//	public void testGetAllQuiz() {
//
//		when(quizService.getAllQuizs()).thenReturn(quizs);
//		
//		ResponseEntity<List<Quiz>> allQuiz = quizController.getQuizes();
//		
//		assertNotNull(allQuiz);
//		assertEquals(HttpStatus.OK, allQuiz.getStatusCode());
//		assertEquals(2, allQuiz.getBody().size());
//	}
//	
//	@DisplayName("test Save Quizs")
//	@Test
//	public void testSaveQuiz() {
//
//		when(quizService.addQuiz(quizs.get(0))).thenReturn(quizs.get(0));
//		
//		ResponseEntity<Quiz> quiz = quizController.addQuiz(quizs.get(0));
//		
//		assertNotNull(quiz);
//		assertEquals(HttpStatus.CREATED, quiz.getStatusCode());
//		assertThat(quiz.getBody().getQid()).isGreaterThan(0);
//	}
//	
//	@DisplayName("test get by Id")
//	@Test
//	public void testGetById() {
//		when(quizService.getQuiz(anyInt())).thenReturn(quizs.get(0));
//		
//		ResponseEntity<Quiz> response = quizController.getQuiz(anyInt());
//		
//		assertNotNull(response.getBody());
//		assertEquals(quizs.get(0), response.getBody());
//		assertEquals(quizs.get(0).getQid(), response.getBody().getQid());
//	}
//	
//	@DisplayName("test delete quiz")
//	@Test
//	public void testDeleteQuiz() {
//		doNothing().when(quizService).deleteQuiz(anyInt());
//		
//		ResponseEntity<?> response = quizController.deleteQuiz(anyInt());
//		
//		assertEquals(HttpStatus.OK,response.getStatusCode());
//	}
//	
//	
//	@DisplayName("test update quiz")
//	@Test
//	public void testUpdateQuiz() {
//		
//		Quiz updatedquiz = quizs.get(0);
//		updatedquiz.setTitle("testing");
//		updatedquiz.setActive(true);
//		
//		when(quizService.updateQuiz(quizs.get(0), 1)).thenReturn(updatedquiz);
//		
//		ResponseEntity<Quiz> response = quizController.UpdateQuiz(quizs.get(0),1);
//		
//		assertEquals(HttpStatus.OK,response.getStatusCode());
//		assertNotNull(response);
//		assertEquals("testing", response.getBody().getTitle());
//		assertTrue(response.getBody().isActive());
//	}
//}
