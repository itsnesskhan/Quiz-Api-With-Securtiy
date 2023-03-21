package com.quiz.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.Models.Categories;
import com.quiz.Services.CategoryService;
import com.quiz.helper.ApiResponse;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("")
	public ResponseEntity<?> getCategories(){
		List<Categories> allCategories = this.categoryService.getAllCategories();
		return new ResponseEntity<List<Categories>>(allCategories, HttpStatus.OK);
	}
	
	@GetMapping("/{cid}")
	public ResponseEntity<Categories> getCatogory(@PathVariable("cid") Integer cid){
		Categories Category = this.categoryService.getCategoryById(cid);
		return new ResponseEntity<Categories>(Category, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<Categories> addCategory(@RequestBody Categories categories){
		Categories Category = this.categoryService.addCategory(categories);
		return new ResponseEntity<Categories>(Category, HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<Categories> updateCategory(@RequestBody Categories categories, @PathVariable("catId") Integer catId){
		Categories Category = this.categoryService.updateCategory(categories, catId);
		return new ResponseEntity<Categories>(Category, HttpStatus.OK);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Integer catId){
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);
	}
	


}
