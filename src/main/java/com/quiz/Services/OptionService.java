package com.quiz.Services;

import java.util.List;

import com.quiz.Models.Options;
import com.quiz.Models.Question;

public interface OptionService {
	
	Options addOption(Options option);
	
	Options updateOption(Options option, Integer opId);
	
	void deleteOption(Integer opId);
	
	List<Options> getOptionsByQuestionId(Integer qid);
	
	
	

}
