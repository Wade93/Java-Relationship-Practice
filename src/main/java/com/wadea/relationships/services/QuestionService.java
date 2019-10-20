package com.wadea.relationships.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wadea.relationships.models.Question;
import com.wadea.relationships.models.Tag;
import com.wadea.relationships.repositories.AnswerRepository;
import com.wadea.relationships.repositories.QuestionRepository;
import com.wadea.relationships.repositories.TagRepository;

@Service
public class QuestionService {
	
	private final QuestionRepository questionRepo;
	private final TagRepository tagRepo;
	private final AnswerRepository answerRepo;
	
	public QuestionService(QuestionRepository questionRepo, TagRepository tagRepo, AnswerRepository answerRepo) {
		this.questionRepo = questionRepo;
		this.tagRepo = tagRepo;
		this.answerRepo = answerRepo;
	}
	public Question createQuestion(Question question, ArrayList<String> tagList) {
		ArrayList<Tag> newTags = new ArrayList<Tag>();
		for(int i = 0; i < tagList.size(); i++) {
			Optional<Tag> optionalTag = tagRepo.findDistinctBySubject(tagList.get(i));
			if(optionalTag.isPresent()) {
				Tag tag = optionalTag.get();
				newTags.add(tag);
			}
			else {
				Tag newTag = new Tag(tagList.get(i));
				tagRepo.save(newTag);
				newTags.add(newTag);
			}
		}
		question.setTags((List<Tag>) newTags);
		return questionRepo.save(question);
	}
	public Question updateQuestion(Question question) {
		return questionRepo.save(question);
	}
	public Question findQuestion(Long id) {
		Optional<Question> question = questionRepo.findById(id);
		if(question.isPresent()) {
			return question.get();
		}
		else {
			return null;
		}
	}
	
	public List<Question> findAll(){
		return questionRepo.findAll();
	}
}
