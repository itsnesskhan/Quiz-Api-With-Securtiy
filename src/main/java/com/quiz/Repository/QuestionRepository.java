package com.quiz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.Models.Question;
import com.quiz.Models.Quiz;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	List<Question> findByQuiz(Quiz quiz);

}
