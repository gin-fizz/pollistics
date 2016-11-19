package com.pollistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pollistics.services.PollService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class HomeController {
	@Autowired
	private PollService pollService;

	@GetMapping(value = "/")
	public String greeting(Model model) {
		model.addAttribute("greeting", "Hello World!");
		model.addAttribute("polls", pollService.getAllPolls());
		return "index";
	}

	@PostMapping(value = "/")
	public ModelAndView createPoll(HttpServletRequest request, Model model) {
		String title = request.getParameter("title");
		String option1 = request.getParameter("option1");
		String option2 = request.getParameter("option2");
		String option3 = request.getParameter("option3");
		HashMap<String, Integer> options = new HashMap<>();
		options.put(option1, 0);
		options.put(option2, 0);
		options.put(option3, 0);
		String id = pollService.createPoll(title, options);
		return new ModelAndView("redirect:/polls/" + id);
	}


}
