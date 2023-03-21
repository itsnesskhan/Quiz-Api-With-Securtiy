package com.quiz.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ExpiredJwtException2 extends RuntimeException{


	public ExpiredJwtException2(String message) {
		super(message);
	}
	
	
	
}
