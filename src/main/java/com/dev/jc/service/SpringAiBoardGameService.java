package com.dev.jc.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.dev.jc.dto.Answer;
import com.dev.jc.dto.Question;

import lombok.extern.slf4j.Slf4j;

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

		String answerText = chatClient.prompt()
				.system(systemSpec -> systemSpec
						.text(promptTemplate)
						.param("gameTitle", question.gameTitle())
			            .param("question", question.question())
						.param("rules", gameRules))
				.user(question.question())
				.call()
				.content();

		long endTime = System.currentTimeMillis();
		log.info("Response time: {} ms", (endTime - startTime));
		log.info("Generated answer: {}", answerText);
		return new Answer(question.gameTitle(), answerText);

	}

}
