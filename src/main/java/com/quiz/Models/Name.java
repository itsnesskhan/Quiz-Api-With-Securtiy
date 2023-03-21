package com.quiz.Models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class Name {

	@Column(name = "first_name",nullable = false)
	private String fname;

	@Column(name = "last_name",nullable = false)
	private String lname;

}
