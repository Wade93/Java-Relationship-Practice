package com.wadea.relationships.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wadea.relationships.models.Dojo;
import com.wadea.relationships.services.DojoService;

@Controller
public class DojoController {
	private final DojoService dojoService;
	
	public DojoController(DojoService dojoService) {
		this.dojoService = dojoService;
	}
	
	@RequestMapping("/dojos/new")
	public String addDojo(@ModelAttribute("dojo") Dojo dojo) {
		return "dojos/newDojo.jsp";
	}
	@RequestMapping(value="/dojos/new", method=RequestMethod.POST)
	public String createDojo(@Valid @ModelAttribute("dojo") Dojo dojo, BindingResult result) {
		if (result.hasErrors()) {
            return "dojos/newDojo.jsp";
        } else {
            dojoService.createDojo(dojo);
            return "redirect:/dojos/new";
        }
	}
	
	@RequestMapping("/dojos/{id}")
	public String viewDojo(@PathVariable("id") Long id, Model model) {
		Dojo dojo = dojoService.findDojo(id);
		model.addAttribute("dojo", dojo);
		return "dojos/show.jsp";
	}
}