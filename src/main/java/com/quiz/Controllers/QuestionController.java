package com.quiz.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.Models.Options;
import com.quiz.Models.Question;
import com.quiz.Models.Quiz;
import com.quiz.Repository.QuestionRepository;
import com.quiz.Services.QuestionService;
import com.quiz.helper.ApiResponse;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("")
	public ResponseEntity<List<Question>> getQuestions() {
		List<Question> allQuestions = questionService.getAllQuestions();
		return new ResponseEntity<List<Question>>(allQuestions, HttpStatus.OK);
	}

	@GetMapping("/{quesId}")
	public ResponseEntity<Question> getQuestion(@PathVariable("quesId") Integer quesId) {
		Question question = questionService.getQuestionById(quesId);
		return new ResponseEntity<Question>(question, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Question> addQuestion(@RequestBody Question questionDto) {
		Question question = questionService.addQuestion(questionDto);
		return new ResponseEntity<Question>(question, HttpStatus.CREATED);
	}

	@PutMapping("/")
	public ResponseEntity<Question> updateQuestion(@RequestBody Question questionDto) {
		Question question = questionService.updateQuestion(questionDto);
		return new ResponseEntity<Question>(question, HttpStatus.OK);
	}

	@DeleteMapping("/{quesId}")
	public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable("quesId") Integer quesId) {
		questionService.deleteQuestion(quesId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Quesiton Deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/op/{qid}")
	public ResponseEntity<?> getOptionsByQuestionId(@PathVariable("qid") Integer qid) {
		System.out.println(qid);
		List<Options> list = questionService.getOptionsOfQuestion(qid);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/quiz/{qid}")
	public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Integer qid) {
		Quiz quiz = new Quiz();
		quiz.setQid(qid);
		List<Question> list = questionService.getQuestionsOfQuiz(quiz);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
