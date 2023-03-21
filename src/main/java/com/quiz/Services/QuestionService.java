package com.quiz.Services;

import java.util.List;

import com.quiz.Models.Options;
import com.quiz.Models.Question;
import com.quiz.Models.Quiz;

public interface QuestionService {
	
	Question addQuestion(Question question);
	
	Question getQuestionById(Integer quesId);
	
	Question updateQuestion(Question option);
	
	void deleteQuestion(Integer opId);
	
	List<Question> getAllQuestions();
	
	List<Options> getOptionsOfQuestion(Integer qid);
	
	List<Question> getQuestionsOfQuiz(Quiz quiz);
	
	

}
