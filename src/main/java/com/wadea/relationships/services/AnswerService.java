package com.wadea.relationships.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wadea.relationships.models.Answer;
import com.wadea.relationships.repositories.AnswerRepository;
import com.wadea.relationships.repositories.QuestionRepository;

@Service
public class AnswerService {
	private final QuestionRepository questionRepo;
	private final AnswerRepository answerRepo;
	
	public AnswerService(QuestionRepository questionRepo, AnswerRepository answerRepo) {
		this.questionRepo = questionRepo;
		this.answerRepo = answerRepo;
	}	
	public Answer createAnswer(Answer answer) {
		return answerRepo.save(answer);
	}
	public Answer updateAnswer(Answer answer) {
		return answerRepo.save(answer);
	}
	public Answer findTag(Long id) {
		Optional<Answer> answer = answerRepo.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		}
		else {
			return null;
		}
	}
	public List<Answer> findAll(){
		return answerRepo.findAll();
	}
	
	public Answer findLast() {
		Optional<Answer> answer = answerRepo.findFirstByOrderByIdDesc();
		if(answer.isPresent()) {
			return answer.get();
		}
		else {
			return null;
		}
	}
}
