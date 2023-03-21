package com.quiz.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.Models.Categories;
import com.quiz.Models.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
	Optional<Quiz> findByTitle(String title);
	
	List<Quiz> findByActive(boolean b);
	
	List<Quiz> findByCategoryAndActive(Categories category, boolean active);

}
