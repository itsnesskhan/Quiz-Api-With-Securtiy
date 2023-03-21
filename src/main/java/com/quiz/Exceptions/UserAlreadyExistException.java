package com.quiz.Exceptions;

public class UserAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	String email;

	public UserAlreadyExistException(String email) {
		super("User with email "+email+" already exits!");
		this.email = email;
	}
	

	
}
