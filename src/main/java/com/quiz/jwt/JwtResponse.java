package com.quiz.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Builder
@AllArgsConstructor
@Data
public class JwtResponse {

	String accessToken;
	
	String refreshToken;
	}
