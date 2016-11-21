package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class PollController {
	@Autowired
	private PollService pollService;

	@GetMapping(value = {"/polls/{pollId}", "/{pollId}"})
	public String greeting(@PathVariable String pollId, Model model) {
		model.addAttribute("poll", pollService.getPoll(pollId));
		return "polls/detail";
	}

	@PostMapping(value = "/polls/create")
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
		return new ModelAndView("redirect:/" + id);
	}

	@PostMapping(value = "/polls/vote/{pollId}")
	public ModelAndView voteOptions(@PathVariable String pollId, HttpServletRequest request, Model model) {
		String option = request.getParameter("option");
		Poll p = pollService.getPoll(pollId);
		pollService.voteOption(p, option);
		return new ModelAndView("redirect:/" + pollId);
	}
}
