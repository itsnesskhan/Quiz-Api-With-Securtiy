package com.quiz.Controllers;

import java.security.Principal;
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

import com.quiz.Models.Question;
import com.quiz.Models.Quiz;
import com.quiz.Models.Quiz_Result;
import com.quiz.Models.User;
import com.quiz.Repository.ResultRepository;
import com.quiz.Repository.StudentRepository;
import com.quiz.Services.QuizService;
import com.quiz.helper.ApiResponse;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ResultRepository resultRepository;

	@GetMapping("/{quizId}")
	public ResponseEntity<Quiz> getQuiz(@PathVariable Integer quizId) {
		Quiz quiz = quizService.getQuiz(quizId);
		return new ResponseEntity<Quiz>(quiz, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<Quiz>> getQuizes() {
		List<Quiz> allQuizs = quizService.getAllQuizs();
		return new ResponseEntity<List<Quiz>>(allQuizs, HttpStatus.OK);
	}

	@GetMapping("/active")
	public ResponseEntity<List<Quiz>> getActiveQuizes() {
		List<Quiz> allQuizs = quizService.getActiveQuiz();
		return new ResponseEntity<List<Quiz>>(allQuizs, HttpStatus.OK);
	}

	@GetMapping("/category/{cid}")
	public ResponseEntity<List<Quiz>> getQuizes(@PathVariable Integer cid) {
		List<Quiz> allQuizs = quizService.getActiveQuizbyCategoryId(cid);
		return new ResponseEntity<List<Quiz>>(allQuizs, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quizDto) {
		Quiz quiz = quizService.addQuiz(quizDto);
		return new ResponseEntity<Quiz>(quiz, HttpStatus.CREATED);
	}

	@DeleteMapping("/{quizId}")
	public ResponseEntity<?> deleteQuiz(@PathVariable Integer quizId) {
		quizService.deleteQuiz(quizId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Quiz Deleted Successfully!", true), HttpStatus.OK);
	}

	@PutMapping("/{quizId}")
	public ResponseEntity<Quiz> UpdateQuiz(@RequestBody Quiz quizDto, @PathVariable Integer quizId) {
		Quiz quiz = quizService.updateQuiz(quizDto, quizId);
		return new ResponseEntity<Quiz>(quiz, HttpStatus.OK);
	}

	@PostMapping("/result")
	public ResponseEntity<?> evaluateMarks(@RequestBody List<Question> questions,Principal principal) {
		int marksGot = 0;
		int attempted = 0;
		int correctAns = 0;
		int total_marks = Integer.parseInt(questions.get(1).getQuiz().getMaxMarks());
		int marksPeQues = Integer.parseInt(questions.get(1).getQuiz().getMaxMarks())
				/ Integer.parseInt(questions.get(0).getQuiz().getNumOfQuestion());
		System.out.println(marksPeQues);
		System.out.println(total_marks);
		for (Question question : questions) {
			if (question.getGiven_answer() != null) {
				attempted++;
			}
			
			if (question.getAnswer().equals(question.getGiven_answer())) {
				correctAns++;
				marksGot+=marksPeQues;
			}
		}
		
		User student = studentRepository.findByEmail(principal.getName());
		Quiz quiz = questions.get(1).getQuiz();
		
		Quiz_Result quiz_Result = new Quiz_Result(marksGot, attempted,correctAns,student,quiz);
		resultRepository.save(quiz_Result);
		
		return ResponseEntity.ok(quiz_Result);
	}
	
	@GetMapping("/students-result")
	public ResponseEntity<List<Quiz_Result>> getQuizResults(){
		List<Quiz_Result> findAll = resultRepository.findAll();
		return ResponseEntity.ok(findAll);
	}

}
