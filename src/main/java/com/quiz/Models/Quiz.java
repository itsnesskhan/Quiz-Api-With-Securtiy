package com.quiz.Models;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer qid;
	
	private String title;
	
	private String maxMarks;
	
	private String numOfQuestion;
	
	private boolean active;	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cid")
	private Categories category;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
	@JsonIgnore
	private Set<Quiz_Result> quiz_Results;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "quiz")
	@JsonIgnore
	private Set<Question> questions = new LinkedHashSet<Question>();
}

