package com.quiz.dtos;

import java.util.Set;

import javax.persistence.Embedded;

import com.quiz.Models.Name;
import com.quiz.Models.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
	
	private Integer id;
	@Embedded
	private Name name;
	
	private String email;

	private String password;
	
	private String profile;
	
	private Set<UserRole> roles;
	
	private String profileUrl;
	
	
	
	

}

