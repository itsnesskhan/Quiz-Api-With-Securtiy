package com.quiz.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table
public class Quiz_Result {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "result_id")
	private Integer rid;
	
	@Column(name = "marks_got")
	private Integer marksGot;
	
	@Column(name = "attempted")
	private Integer attempted;
	
	@Column(name = "correct_ans")
	private Integer correctAns;
	
	@ManyToOne()
	@JoinColumn(name = "uid")
	private User user;
	
		
	@ManyToOne()
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;


	public Quiz_Result(Integer marksGot, Integer attempted, Integer correctAns, User user, Quiz quiz) {
		super();
		this.marksGot = marksGot;
		this.attempted = attempted;
		this.correctAns = correctAns;
		this.user = user;
		this.quiz = quiz;
	}
	
	
	

}
