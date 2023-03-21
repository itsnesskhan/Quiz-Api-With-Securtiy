package com.quiz.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import org.yaml.snakeyaml.tokens.DocumentEndToken;

import com.quiz.Exceptions.ResourceNotFoundException;
import com.quiz.Models.Options;
import com.quiz.Models.Question;
import com.quiz.Models.Quiz;
import com.quiz.Repository.OptionsRepository;
import com.quiz.Repository.QuestionRepository;

@Service
public class OptionServiceImpl implements OptionService {
	
	@Autowired
	private OptionsRepository optionsRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Options addOption(Options option) {
		return optionsRepository.save(option);
	}


	@Override
	public Options updateOption(Options optionDto, Integer optionid) {
		Options option = this.optionsRepository.findById(optionid).orElseThrow(
				()->new ResourceNotFoundException("Option", "id", optionid));
		option.setContent(optionDto.getContent());
		option.setQuestion(optionDto.getQuestion());
		return optionsRepository.save(option);
	}

	@Override
	public void deleteOption(Integer optionid) {
		Options option = this.optionsRepository.findById(optionid).orElseThrow(
				()->new ResourceNotFoundException("Option", "id", optionid));
		optionsRepository.delete(option);
		
	}


	@Override
	public List<Options> getOptionsByQuestionId(Integer qid) {
		Question question = questionRepository.findById(qid).get();
		List<Options> options = question.getOptions();
		return options;
	}
	
	

}
