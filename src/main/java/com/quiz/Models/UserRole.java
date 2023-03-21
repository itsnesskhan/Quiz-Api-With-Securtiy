package com.quiz.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.ToString;

@ToString
@Entity
@Table(name = "roles")
public class UserRole {

	@Id
	private Integer id;
	private String name;
	
	public UserRole(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	

	public UserRole() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
