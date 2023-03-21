package com.quiz.ExceptionHandler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.quiz.Exceptions.ExpiredJwtException2;
import com.quiz.Exceptions.JwtAuthenticationException;
import com.quiz.Exceptions.ResourceNotFoundException;
import com.quiz.Exceptions.UserAlreadyExistException;
import com.quiz.helper.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
		ApiResponse apiResponse = new ApiResponse(exception.getMessage(), false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({UserAlreadyExistException.class,ConstraintViolationException.class})
	public ResponseEntity<ApiResponse> userAlreadyExistExceptionHandler(UserAlreadyExistException exception){
		ApiResponse apiResponse = new ApiResponse(exception.getMessage(), false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(ExpiredJwtException2.class)
	public ResponseEntity<ApiResponse> expriedJwtExceptionHandler(ExpiredJwtException2 exception){
		System.out.println("handler got called");
		ApiResponse apiResponse = new ApiResponse(exception.getMessage(), false);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {Exception.class, IllegalArgumentException.class, MalformedJwtException.class})
	public ResponseEntity<ApiResponse> globalExceptionHandler(Exception exception){
		ApiResponse apiResponse = new ApiResponse(exception.getMessage(), false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = JwtAuthenticationException.class)
	public ResponseEntity<ApiResponse> globalExcedptionHandler(JwtAuthenticationException exception){
		ApiResponse apiResponse = new ApiResponse(exception.getMessage(), false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
	}
}
