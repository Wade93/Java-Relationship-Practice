package com.wadea.relationships.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wadea.relationships.models.License;
import com.wadea.relationships.models.Person;
import com.wadea.relationships.services.PersonService;

@Controller
public class PersonController {
	private final PersonService personService;
	
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@RequestMapping("/persons/new")
	public String addPerson(@ModelAttribute("person") Person person) {
		return "persons/newPerson.jsp";
	}
	@RequestMapping(value="/persons/new", method=RequestMethod.POST)
	public String createPerson(@ModelAttribute("person") Person person, BindingResult result) {
		if (result.hasErrors()) {
            return "persons/newPerson.jsp";
        } else {
            personService.createPerson(person);
            return "redirect:/persons/new";
        }
	}
	
	@RequestMapping("/persons/{id}")
	public String viewPerson(@PathVariable("id") Long id, Model model) {
		Person person = personService.findPerson(id);
		License license = person.getLicense();
		model.addAttribute("person", person);
		model.addAttribute("license", license);
		return "persons/show.jsp";
	}
}
