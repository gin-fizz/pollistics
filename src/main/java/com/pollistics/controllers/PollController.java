package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.HashMap;

@Controller
public class PollController {

	@Autowired
	private PollService pollService;
	private Cookie cookie = new Cookie("id", "");

	@GetMapping(value = "/polls")
	public String allPolls(Model model) {
		model.addAttribute("polls", pollService.getAllPolls());
		return "polls/overview";
	}

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

	//Multiple options
	@PostMapping(value = "/polls/create")
	public String createPoll(HttpServletRequest request) {
		String title = request.getParameter("title");
		HashMap<String, Integer> options = new HashMap<>();
		int i = 0;
		while (request.getParameter("option" + i) != "") {
			String option = request.getParameter("option" + i);
			options.put(option, 0);
			i++;
		}
		String id = pollService.createPoll(title, options);
		return "redirect:/" + id;
	}

	@PostMapping(value = "/polls/vote/{pollId}")
	public String voteOptions(@CookieValue(value = "id", defaultValue = "") String cookieIdValue, @PathVariable String pollId, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (cookie.getValue().contains(pollId)) {
			Poll p = pollService.getPoll(pollId);
			model.addAttribute("msg", MessageFormat.format("You already voted for poll: {0}", p.getName()));
			model.addAttribute("previous", MessageFormat.format("/{0}/", pollId));
			response.setStatus(403);
			return "error/403";
		} else {
			String value;
			if (cookie.getValue().equals("")) {
				value = pollId;
			} else {
				value = MessageFormat.format("{0}-{1}", pollId, cookieIdValue);
			}
			final int expiryTimeCookie = 2147483647; // maximum of int
			final String cookiePath = "/";
			cookie.setValue(value);
			cookie.setMaxAge(expiryTimeCookie);
			cookie.setPath(cookiePath);
			response.addCookie(cookie);
			String option = request.getParameter("option");
			Poll p = pollService.getPoll(pollId);
			pollService.voteOption(p, option);
			return "redirect:/" + pollId;
		}
	}
}
