package com.wadea.relationships.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wadea.relationships.models.Dojo;
import com.wadea.relationships.models.Ninja;
import com.wadea.relationships.services.DojoService;
import com.wadea.relationships.services.NinjaService;

@Controller
public class NinjaController {
	private final NinjaService ninjaService;
	private final DojoService dojoService;
	
	public NinjaController(NinjaService ninjaService, DojoService dojoService) {
		this.ninjaService = ninjaService;
		this.dojoService = dojoService;
	}
	
	@RequestMapping("/ninjas/new")
	public String addNinja(@Valid @ModelAttribute("ninja") Ninja ninja, Model model) {
		List<Dojo> dojos = dojoService.findAll();
		model.addAttribute("dojos", dojos);
		return "ninjas/newNinja.jsp";
	}
	@RequestMapping(value="/ninjas/new", method=RequestMethod.POST)
	public String createNinja(@Valid @ModelAttribute("ninja") Ninja ninja, BindingResult result) {
		if (result.hasErrors()) {
            return "ninjas/newNinja.jsp";
        } else {
            ninjaService.createNinja(ninja);
            return "redirect:/ninjas/new";
        }
	}
}