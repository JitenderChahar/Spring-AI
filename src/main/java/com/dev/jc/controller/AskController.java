package com.dev.jc.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.jc.dto.Answer;
import com.dev.jc.dto.Question;
import com.dev.jc.service.SpringAiBoardGameService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

@RestController
public class AskController {
	
	private final SpringAiBoardGameService askService;
	
	public AskController(SpringAiBoardGameService askService) {
		this.askService = askService;
	}
	
	@PostMapping(value = "/ask", produces = MediaType.APPLICATION_JSON_VALUE)
	public Answer askQuestion(@RequestBody @Valid Question question) {
		return askService.askQuestion(question);
	}
	
	@PostMapping(value = "/ask-streaming", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> askQuestionStreaming(@RequestBody @Valid Question question) {
		return askService.askQuestionStreaming(question);
	}

}
