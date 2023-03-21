package com.quiz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quiz.Models.Options;
import com.quiz.Models.Question;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Integer> {
	
	@Query("Select o From Options as o join fetch o.question q where q.question_id =:id")
	List<Options> findByQuestionId(@Param("id") Integer qid);
	
	List<Options> findByQuestion(Question question);
	

}
