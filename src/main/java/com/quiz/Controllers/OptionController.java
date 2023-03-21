package com.quiz.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.Models.Options;
import com.quiz.Models.Question;
import com.quiz.Repository.QuestionRepository;
import com.quiz.Services.OptionService;
import com.quiz.Services.QuestionService;
import com.quiz.helper.ApiResponse;

@RestController
@RequestMapping("/option")
public class OptionController {

	@Autowired
	private OptionService optionService;
	
	@GetMapping("q/{qid}")
	public ResponseEntity<?> getOptionsByQuestionId(@PathVariable("qid") Integer qid) {
		System.out.println(qid);
		List<Options> list = optionService.getOptionsByQuestionId(qid);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@PostMapping("")
	public ResponseEntity<Options> addQuestion(@RequestBody Options optionDto) {
		Options options = optionService.addOption(optionDto);
		return new ResponseEntity<Options>(options, HttpStatus.CREATED);
	}

	@PutMapping("/{opId}")
	public ResponseEntity<Options> updateQuestion(@RequestBody Options optionDto,
			@PathVariable("opId") Integer opId) {
		Options options = optionService.updateOption(optionDto, opId);
		return new ResponseEntity<Options>(options, HttpStatus.OK);
	}

	@DeleteMapping("/{opId}")
	public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable("opId") Integer opId) {
		optionService.deleteOption(opId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Option Deleted Successfully", true), HttpStatus.OK);
	}
}
