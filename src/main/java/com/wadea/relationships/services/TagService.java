package com.wadea.relationships.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wadea.relationships.models.Tag;
import com.wadea.relationships.repositories.QuestionRepository;
import com.wadea.relationships.repositories.TagRepository;

@Service
public class TagService {
	private final QuestionRepository questionRepo;
	private final TagRepository tagRepo;
	
	public TagService(QuestionRepository questionRepo, TagRepository tagRepo) {
		this.questionRepo = questionRepo;
		this.tagRepo = tagRepo;
	}
	public Tag createTag(Tag tag) {
		return tagRepo.save(tag);
	}
	public Tag updateTag(Tag tag) {
		return tagRepo.save(tag);
	}
	public Tag findTag(Long id) {
		Optional<Tag> tag = tagRepo.findById(id);
		if(tag.isPresent()) {
			return tag.get();
		}
		else {
			return null;
		}
	}
	public List<Tag> findAll(){
		return tagRepo.findAll();
	}
	public Tag findTagBySubject(String subject) {
		Optional<Tag> tag = tagRepo.findDistinctBySubject(subject);
		if(tag.isPresent()) {
			return tag.get();
		}
		else {
			return null;
		}
	}
}
