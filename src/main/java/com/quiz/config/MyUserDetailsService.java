package com.quiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quiz.Models.User;
import com.quiz.Repository.StudentRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = studentRepository.findByEmail(username);
		if (user==null) {
			throw new UsernameNotFoundException(username);
		}
		
		return user;
	}

}
