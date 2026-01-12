package com.dev.jc.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.dev.jc.dto.Answer;
import com.dev.jc.dto.Question;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpringAiBoardGameService implements BoardGameService {
	
	private final ChatClient chatClient;
	
	public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}
	
	@Override
	public Answer askQuestion(Question question) {
		log.info("Received question: {}", question.question());
		long startTime = System.currentTimeMillis();
		String response = chatClient
				.prompt()
				.user(question.question())
				.call()
				.content();
		long endTime = System.currentTimeMillis();
		log.info("Response time: {} ms", (endTime - startTime));
		log.info("Generated answer: {}", response);
		
		return new Answer(response);
	}

}
