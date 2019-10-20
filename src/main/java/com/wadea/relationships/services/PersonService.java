package com.wadea.relationships.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wadea.relationships.models.Person;
import com.wadea.relationships.repositories.PersonRepository;

@Service
public class PersonService {
	private final PersonRepository personRepo;

	public PersonService(PersonRepository personRepo) {
		this.personRepo = personRepo;
	}
	
	public Person createPerson(Person person) {
		return personRepo.save(person);
	}
	
	public Person findPerson(Long id) {
		Optional<Person> optionalPerson = personRepo.findById(id);
        if(optionalPerson.isPresent()) {
            return optionalPerson.get();
        } else {
            return null;
        }
	}
	
	public List<Person> findAll(){
		return personRepo.findAll();
	}
}
