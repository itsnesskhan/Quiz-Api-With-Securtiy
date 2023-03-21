package com.quiz.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
	private String content;
	private String type;
	private String css_class;
	
}
