package com.quiz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.Models.Quiz_Result;

@Repository
public interface ResultRepository extends JpaRepository<Quiz_Result, Integer> {
	
	

}
