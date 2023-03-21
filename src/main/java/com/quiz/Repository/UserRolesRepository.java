package com.quiz.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.Models.User;
import com.quiz.Models.UserRole;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, Integer> {

	
}
