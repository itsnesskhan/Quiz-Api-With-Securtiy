package com.quiz.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.quiz.Models.User;
import com.quiz.Models.UserRole;
import com.quiz.jwt.JwtAuthEntryPoint;
import com.quiz.jwt.JwtAuthenticationFilter;
import com.quiz.jwt.JwtLogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;
	
	@Autowired
	private JwtLogoutFilter jwtLogoutFilter;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		auth.authenticationProvider(daoAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			csrf().disable()
			.cors().
			and()
			.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET,"/student/**").permitAll()
		.antMatchers(HttpMethod.POST,"/student/**").permitAll()
		.antMatchers(HttpMethod.PUT,"/student/**").hasAnyAuthority("ADMIN","STUDENT")
		.antMatchers(HttpMethod.DELETE,"/student/**").hasAuthority("ADMIN")
//		.antMatchers(HttpMethod.GET,"/student/**").hasAnyAuthority("TEACHER","ADMIN","STUDENT")
		.antMatchers("index").permitAll()
		.antMatchers("/auth/refreshToekn").permitAll()
		.antMatchers("/auth/login").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(STATELESS);
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwtLogoutFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	

}
