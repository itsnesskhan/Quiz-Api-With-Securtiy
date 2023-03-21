package com.quiz.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.quiz.config.MyUserDetailsService;
import com.quiz.dtos.TokenBlaklist;
import com.quiz.dtos.TokenRefreshDto;
import com.quiz.dtos.TokenRefreshResponseDto;
import com.quiz.helper.ResponseHandler;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class JwtAuthService<T> {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private TokenBlaklist tokenBlaklist;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	public T generateToken(JwtRequest jwtRequest) {
		try {
			System.out.println(jwtRequest);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					jwtRequest.getUsername(), jwtRequest.getPassword());
			

			
			authenticationManager.authenticate(authenticationToken);
			
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException(String.format("User with %s doesn't exist", jwtRequest.getUsername()));
			
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new BadCredentialsException("Bad Credintials");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = jwtUtil.generateToken(userDetails);
		String refreshToken = jwtUtil.generateRefreshToken(userDetails);
		
		JwtResponse jwtResponse = JwtResponse.builder().accessToken(token).refreshToken(refreshToken).build();
		return (T) ResponseEntity.ok(jwtResponse);
	}
	
	public T refreshToken(TokenRefreshDto tokenRefreshDto) {

		String username = jwtUtil.extractUsername(tokenRefreshDto.getRefreshToken());
		if (username == null) {
			return (T) ResponseHandler.errorResponseBuilder("cannot generate refresh token", HttpStatus.BAD_GATEWAY);
		}

		UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
		
		if (userDetails == null) {
			return (T) ResponseHandler.errorResponseBuilder("user does not exist", HttpStatus.NOT_FOUND);
		}
		
		boolean isValidate = jwtUtil.validateToken(tokenRefreshDto.getRefreshToken(), userDetails);
		if (isValidate) {
			String accessToken = jwtUtil.generateToken(userDetails);
			
			TokenRefreshResponseDto refreshResponseDto = TokenRefreshResponseDto.builder()
					.token(accessToken)
					.refreshToken(jwtUtil.generateRefreshToken(userDetails))
					.tokenType("Bearer")
					.build();
			return (T) ResponseHandler.responseBuilder("token generated",HttpStatus.CREATED,refreshResponseDto );
		} else {
			return (T) ResponseHandler.errorResponseBuilder("refresh token expried", HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	public T logout(logoutRequest logoutRequest,HttpServletRequest request, HttpServletResponse response) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, response, authentication);
		
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);
		
		String token = request.getHeader("Authorization");
		tokenBlaklist.getTokens().add(token);
		tokenBlaklist.getTokens().add("Bearer "+ logoutRequest.getRefreshToken());
		return (T) ResponseHandler.responseBuilder("User log out successfully!",HttpStatus.OK,null);
	}
}
