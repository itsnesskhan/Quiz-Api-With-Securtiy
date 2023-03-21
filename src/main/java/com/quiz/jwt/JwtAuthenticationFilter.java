package com.quiz.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.Exceptions.ExpiredJwtException2;
import com.quiz.Exceptions.JwtAuthenticationException;
import com.quiz.config.MyUserDetailsService;
import com.quiz.helper.ResponseHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String username = null;
		String token = null;

		String requestToken = request.getHeader("Authorization");

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				username = jwtUtil.extractUsername(token);
			} catch (IllegalArgumentException e) {

				throw new IllegalArgumentException("unable to get jwt token");
			} catch (ExpiredJwtException e) {
				logger.error("token authentication failed "+e.getMessage());
				jwtAuthEntryPoint.commence(request, response, e);
				return;

			} catch (MalformedJwtException e) {

				throw new MalformedJwtException("Invalid Jwt " + e.getMessage());
			}

		} else {
			log.error("jwt token doesn't begin with Bearer");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

			boolean isValid = this.jwtUtil.validateToken(token, userDetails);
			if (isValid) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				log.error("Invalid Jwt");
			}
		} else {
			log.error("username is null or context is not null");
		}

		filterChain.doFilter(request, response);
	}

	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

}
