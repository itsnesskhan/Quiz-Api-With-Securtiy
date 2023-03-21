package com.quiz.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.Exceptions.ResourceNotFoundException;
import com.quiz.Models.Categories;
import com.quiz.Repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Categories addCategory(Categories category) {
		return this.categoryRepository.save(category);
	}

	@Override
	public Categories getCategoryById(Integer cid) {
		return this.categoryRepository.findById(cid).orElseThrow(
				()->new ResourceNotFoundException("category","", cid));
	}

	@Override
	public Categories updateCategory(Categories category, Integer catId) {
		Categories categories = this.categoryRepository.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", category.getName(), catId));
		categories.setName(category.getName());
		categories = this.categoryRepository.save(categories);
		return categories;
	}

	@Override
	public void deleteCategory(Integer catId) {
		Categories categories = this.categoryRepository.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId));
		this.categoryRepository.delete(categories);

	}

	@Override
	public List<Categories> getAllCategories() {
		return this.categoryRepository.findAll();
		
	}

}
