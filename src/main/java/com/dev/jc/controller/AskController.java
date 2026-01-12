package com.dev.jc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.jc.dto.Answer;
import com.dev.jc.dto.Question;
import com.dev.jc.service.SpringAiBoardGameService;

@RestController
public class AskController {
	
	private final SpringAiBoardGameService askService;
	
	public AskController(SpringAiBoardGameService askService) {
		this.askService = askService;
	}
	
	@PostMapping("/ask")
	public Answer askQuestion(@RequestBody Question question) {
		return askService.askQuestion(question);
	}

}
