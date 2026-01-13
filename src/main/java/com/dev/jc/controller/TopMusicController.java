package com.dev.jc.controller;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopMusicController {
	
	private final ChatClient chatClient;
	private final ChatOptions chatOptions = ChatOptions.builder()
		    .temperature(0.7)
		    .build();
	
	@Value("classpath:/promptTemplates/top-songs-prompt.st")
	private Resource topSongPromptTemplate;
	
	public TopMusicController(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}
	
	@GetMapping(path = "/topSongs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> topSongs(@RequestParam String year) {
		return chatClient.prompt()
				.options(chatOptions)
				.user(userSpec -> userSpec
			            .text(topSongPromptTemplate)
			            .param("year", year))
				.call()
				.entity(new ParameterizedTypeReference<List<String>>() {});
	}

}
