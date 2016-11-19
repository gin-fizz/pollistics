package com.pollistics.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pollistics.services.PollService;

@Controller
@RequestMapping("/polls")
public class PollController {
	@Autowired
	private PollService pollService;

	@GetMapping(value = "/{pollId}")
	public String greeting(@PathVariable String pollId, Model model) {
		model.addAttribute("poll", pollService.getPoll(pollId));
		return "polls/detail";
	}
	
	@PostMapping(value = "/create")
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
