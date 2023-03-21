package com.quiz.Services;

import java.util.List;

import com.quiz.Models.Quiz;

public interface QuizService {
	
	Quiz addQuiz(Quiz quiz);
	
	Quiz getQuiz(Integer quizId);
	
	Quiz updateQuiz(Quiz quiz, Integer quizId);
	
	void deleteQuiz(Integer quizId);
	
	List<Quiz> getAllQuizs();
	
	List<Quiz> getActiveQuiz();

	List<Quiz> getActiveQuizbyCategoryId(Integer cid);
	
	

}
