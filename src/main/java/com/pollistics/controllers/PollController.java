package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;

@Controller
public class PollController {
	@Autowired
	private PollService pollService;

	@GetMapping(value = {"/polls/{pollId}", "/{pollId}"})
	public String pollDetail(@PathVariable String pollId, Model model, HttpServletResponse response) {
		Poll poll = pollService.getPoll(pollId);
		if(poll == null) {
			response.setStatus(404);
			return "error/404";
		}
		model.addAttribute("poll", pollService.getPoll(pollId));
		return "polls/detail";
	}

	@PostMapping(value = "/polls/create")
	public String createPoll(HttpServletRequest request, Model model) {
		String title = request.getParameter("title");
		String option1 = request.getParameter("option1");
		String option2 = request.getParameter("option2");
		String option3 = request.getParameter("option3");
		HashMap<String, Integer> options = new HashMap<>();
		options.put(option1, 0);
		options.put(option2, 0);
		options.put(option3, 0);
		String id = pollService.createPoll(title, options);
		return "redirect:/" + id;
	}

	@PostMapping(value = "/polls/vote/{pollId}")
	public String voteOptions(@PathVariable String pollId, HttpServletRequest request, Model model) {
		String option = request.getParameter("option");
		Poll p = pollService.getPoll(pollId);
		pollService.voteOption(p, option);
		return "redirect:/" + pollId;
	}
}
