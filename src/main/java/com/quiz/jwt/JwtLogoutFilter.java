package com.quiz.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.quiz.Exceptions.JwtAuthenticationException;
import com.quiz.dtos.TokenBlaklist;

@Component
public class JwtLogoutFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private TokenBlaklist tokenBlaklist;
	
	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("inside logout filter");
		String token = request.getHeader("Authorization");
		System.out.println(request.getRequestURI()+"URI");
		System.out.println("request url"+ request.getRequestURI().getClass());
		
		String reqUri = request.getRequestURI();
			  try {
				  System.out.println(tokenBlaklist);
				if (tokenBlaklist.getTokens().contains(token)) {
					System.out.println("token is blacklist");
					throw new JwtAuthenticationException("Token has expired");
				}		
			} catch (JwtAuthenticationException e) {
				logger.error("token authentication failed "+e.getMessage());
				jwtAuthEntryPoint.commence(request, response, e);
				return;

			}
		System.out.println("outside logout filter");
		filterChain.doFilter(request, response);
		
		
	}

}
