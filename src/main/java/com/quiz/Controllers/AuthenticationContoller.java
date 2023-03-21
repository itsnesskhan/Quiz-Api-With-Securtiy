package com.quiz.Controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.Services.StudentServices;
import com.quiz.config.MyUserDetailsService;
import com.quiz.dtos.TokenRefreshDto;
import com.quiz.jwt.JwtRequest;
import com.quiz.jwt.JwtResponse;
import com.quiz.jwt.JwtAuthService;
import com.quiz.jwt.JwtUtil;
import com.quiz.jwt.logoutRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthenticationContoller<T> {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

		
	
	@Autowired
	private JwtAuthService jwtAuthService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping(path = "/login")
	public T generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		return (T) jwtAuthService.generateToken(jwtRequest);
	}
	
	@PostMapping("/refreshToekn")
	public T refreshToken(@RequestBody TokenRefreshDto tokenRefreshDto) {
		System.out.println(tokenRefreshDto);
		return (T) jwtAuthService.refreshToken(tokenRefreshDto);
		
	}
	
	@PostMapping("/logout")
	public T logOut(@RequestBody logoutRequest logoutRequest ,HttpServletRequest request, HttpServletResponse response) {
		return (T) jwtAuthService.logout(logoutRequest,request, response);
		
	}
	
	

}
