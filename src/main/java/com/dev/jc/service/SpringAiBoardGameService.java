package com.dev.jc.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.dev.jc.dto.Answer;
import com.dev.jc.dto.Question;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class SpringAiBoardGameService implements BoardGameService {

	private final ChatClient chatClient;
	private final GameRulesService gameRulesService;
	
	public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder, GameRulesService gameRulesService) {
		this.chatClient = chatClientBuilder.build();
		this.gameRulesService = gameRulesService;
	}
	
	@Value("classpath:/promptTemplates/systemPromptTemplate.st")
	Resource promptTemplate;
	
	@Override
	public Answer askQuestion(Question question) {
		log.info("Received question: {}", question.question());
		long startTime = System.currentTimeMillis();
		
		String gameRules = gameRulesService.getRulesFor(question.gameTitle());

		Answer answer = chatClient.prompt()
				.system(systemSpec -> systemSpec
						.text(promptTemplate)
						.param("gameTitle", question.gameTitle())
			            .param("question", question.question())
						.param("rules", gameRules))
				.user(question.question())
				.call()
				.entity(Answer.class);

		long endTime = System.currentTimeMillis();
		log.info("Response time: {} ms", (endTime - startTime));
		log.info("Generated answer: {}", answer.answer());
		return answer;
	}

	public Flux<String> askQuestionStreaming(@Valid Question question) {
		String gameRules = gameRulesService.getRulesFor(question.gameTitle());

		return chatClient.prompt()
				.system(systemSpec -> systemSpec
						.text(promptTemplate)
						.param("gameTitle", question.gameTitle())
			            .param("question", question.question())
						.param("rules", gameRules))
				.user(question.question())
				.stream()
				.content();
	}

}
