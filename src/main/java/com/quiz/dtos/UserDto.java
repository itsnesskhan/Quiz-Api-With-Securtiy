package com.quiz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Embedded;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quiz.Models.Name;
import com.quiz.Models.UserRole;

import antlr.collections.List;
import lombok.NoArgsConstructor;

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

