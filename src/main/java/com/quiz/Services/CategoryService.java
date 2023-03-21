package com.quiz.Services;

import java.util.List;

import com.quiz.Models.Categories;

public interface CategoryService {
	
	Categories addCategory(Categories category);
	
	Categories getCategoryById(Integer catId);
	
	Categories updateCategory(Categories category, Integer catId);
	
	void deleteCategory(Integer catId);
	
	List<Categories> getAllCategories();
	
	

}
