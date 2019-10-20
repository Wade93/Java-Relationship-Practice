package com.wadea.relationships.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wadea.relationships.models.License;
import com.wadea.relationships.models.Person;
import com.wadea.relationships.services.LicenseService;
import com.wadea.relationships.services.PersonService;

@Controller
public class LicenseController {
	private final LicenseService licenseService;
	private final PersonService personService;
	
	public LicenseController(LicenseService licenseService, PersonService personService) {
		this.licenseService = licenseService;
		this.personService = personService;
	}

	@RequestMapping("/licenses/new")
	public String addLicense(@ModelAttribute("license") License license, Model model) {
		List<Person> persons = personService.findAll();
		model.addAttribute("persons", persons);
		return "licenses/newLicense.jsp";
	}
	
	@RequestMapping(value="/licenses/new", method=RequestMethod.POST)
	public String createLicense(@Valid @ModelAttribute("license") License license, @RequestParam("person") Person person, BindingResult result) {
		if (result.hasErrors()) {
            return "licenses/newLicense.jsp";
        } else {
            licenseService.createLicense(person, license);
            return "redirect:/licenses/new";
        }
	}
}
