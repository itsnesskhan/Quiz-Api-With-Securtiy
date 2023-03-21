package com.quiz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.Models.Categories;

@Repository
public interface CategoryRepository extends JpaRepository<Categories,Integer> {
	
	Categories findByName(String catName);

}
