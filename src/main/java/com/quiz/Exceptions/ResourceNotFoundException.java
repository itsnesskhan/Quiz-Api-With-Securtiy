package com.quiz.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	String sourceName;
	String fieldName;
	int fieldValue;

	public ResourceNotFoundException(String sourceName, String fieldName, int fieldValue) {
		super(String.format("%s not found with %s : %s", sourceName, fieldName, fieldValue));
		this.sourceName = sourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
