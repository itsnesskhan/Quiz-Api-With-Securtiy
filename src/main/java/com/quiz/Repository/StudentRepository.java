package com.quiz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.Models.User;

@Repository
public interface StudentRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

}
