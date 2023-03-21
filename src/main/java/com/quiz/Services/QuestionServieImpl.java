package com.quiz.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.Exceptions.ResourceNotFoundException;
import com.quiz.Models.Categories;
import com.quiz.Models.Options;
import com.quiz.Models.Question;
import com.quiz.Models.Quiz;
import com.quiz.Repository.CategoryRepository;
import com.quiz.Repository.QuestionRepository;

@Service
public class QuestionServieImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Question addQuestion(Question question) {
		return this.questionRepository.save(question);
	}

	@Override
	public Question getQuestionById(Integer quesId) {
		Question question = this.questionRepository.findById(quesId).orElseThrow(
				()->new ResourceNotFoundException("Question", "id", quesId));
		int nes = 1005;
		String valueOf = String.valueOf(nes);
		char[] charArray = valueOf.toCharArray();
		int sum = 0;
		for (char c : charArray) {
			sum+=(int)c;
		}
		System.out.println(sum);
		return question ;
	}

	@Override
	public Question updateQuestion(Question option) {
		Question question = this.questionRepository.findById(option.getQuestion_id()).orElseThrow(
				()->new ResourceNotFoundException("Question", "id", option.getQuestion_id()));
		question.setContent(option.getContent());
		question.setOptions(option.getOptions());
		question.setAnswer(option.getAnswer());
		return this.questionRepository.save(question);
	}

	@Override
	public void deleteQuestion(Integer quesId) {
		Question question = this.questionRepository.findById(quesId).orElseThrow(
				()->new ResourceNotFoundException("Question", "id", quesId));
		questionRepository.delete(question);
	}
	@Override
	public List<Question> getAllQuestions(){
		return questionRepository.findAll();
	}

	@Override
	public List<Options> getOptionsOfQuestion(Integer qid) {
		Question question = questionRepository.findById(qid).get();
		List<Options> options = question.getOptions();
		return options;
	}

	@Override
	public List<Question> getQuestionsOfQuiz(Quiz quiz) {
		List<Question> list = questionRepository.findByQuiz(quiz);
		return list;
	}

}
