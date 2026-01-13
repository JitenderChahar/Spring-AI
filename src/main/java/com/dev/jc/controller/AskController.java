package com.dev.jc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.jc.dto.Answer;
import com.dev.jc.dto.Question;
import com.dev.jc.service.SpringAiBoardGameService;

import jakarta.validation.Valid;

@RestController
public class AskController {
	
	private final SpringAiBoardGameService askService;
	
	public AskController(SpringAiBoardGameService askService) {
		this.askService = askService;
	}
	
	@PostMapping(value = "/ask", produces = "application/json")
	public Answer askQuestion(@RequestBody @Valid Question question) {
		return askService.askQuestion(question);
	}

}
