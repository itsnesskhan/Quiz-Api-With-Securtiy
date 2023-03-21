package com.quiz.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class JwtRequest {
	
	String username;
	String password;
}
