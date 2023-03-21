package com.quiz.Models;

import java.util.List;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer question_id;
	private String content;
	
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Options> options;
	
	private String answer;
	
	@Transient
	private String given_answer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name= "quiz_id")
	private Quiz quiz;
	
	
}
