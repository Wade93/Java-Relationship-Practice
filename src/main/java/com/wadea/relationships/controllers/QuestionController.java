package com.wadea.relationships.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wadea.relationships.models.Answer;
import com.wadea.relationships.models.Question;
import com.wadea.relationships.services.AnswerService;
import com.wadea.relationships.services.QuestionService;
import com.wadea.relationships.services.TagService;

@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;

	public QuestionController(QuestionService questionService, AnswerService answerService) {
		this.questionService = questionService;
		this.answerService = answerService;
	}

	@RequestMapping("/questions")
	public String dashboard(Model model) {
		List<Question> questions = questionService.findAll();
		model.addAttribute("questions", questions);
		return "questions/questions.jsp";
	}
	
	@RequestMapping("/questions/new")
	public String addNewQuestion(@ModelAttribute("question") Question question) {
		return "questions/newQuestion.jsp";
	}
	
	@RequestMapping(value="/questions/new", method=RequestMethod.POST)
	public String createQuestion(@Valid @ModelAttribute("question") Question question, BindingResult result, @RequestParam("tagString") String tagString) {
		System.out.println("!!!!!!!!!!!!!!!ENTERING THE POST METHOD!!!!!!!!!!!!!!!!!!");
		if (result.hasErrors()) {
			System.out.println("!!!!!!!!!!!THE POST HAS ERRORS!!!!!!!!!!!!!");
            return "questions/newQuestion.jsp";
        } else {
            ArrayList<String> tagList = new ArrayList<>(Arrays.asList(tagString.split(",")));
            if (tagList.size() > 0) {
	            for(int i = 0; i < tagList.size(); i++) {
	            	if(tagList.get(i).length() == 0) {
	            		tagList.remove(i);
	            		i--;
	            	}
	            	tagList.set(i, tagList.get(i).trim().toLowerCase());
	            }
            }
            questionService.createQuestion(question, tagList);
            System.out.println(question.getTags().get(0).getSubject());
            System.out.println(question.getId());
            return "redirect:/questions";
        }
	}
	@RequestMapping("/questions/{q_id}")
	public String viewQuestion(@PathVariable("q_id") Long q_id, Model model, @ModelAttribute("answer") Answer answer) {
		Question questionToView = questionService.findQuestion(q_id);
		model.addAttribute("question", questionToView);
		return "questions/showQuestion.jsp";
	}
	
	@RequestMapping(value="/questions/{questionID}", method=RequestMethod.POST)
	public String addAnswer(@PathVariable("questionID") Long questionID,@Valid  @ModelAttribute("answer") Answer answer, 
			BindingResult result, Model model) {
		System.out.println(result);
		if (result.hasErrors()) {
			model.addAttribute("question", questionService.findQuestion(questionID));
			return "questions/showQuestion.jsp";
		}
		else {
			Question questionToUpdate = questionService.findQuestion(questionID);
			answer.setQuestion(questionToUpdate);
			answerService.createAnswer(answer);
			List<Answer> answerList = questionToUpdate.getAnswers();
			answerList.add(answer);
			questionToUpdate.setAnswers(answerList);
			questionService.updateQuestion(questionToUpdate);
			return "redirect:/questions/"+questionID;
		}
	}
}
