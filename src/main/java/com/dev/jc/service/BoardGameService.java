package com.dev.jc.service;

import com.dev.jc.dto.Answer;
import com.dev.jc.dto.Question;

public interface BoardGameService {
	
	Answer askQuestion(Question question);

}
