package com.quiz.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenRefreshResponseDto {
	private String refreshToken;
	private String token;
	private String tokenType;
}