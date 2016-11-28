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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class PollController {
	@Autowired
	private PollService pollService;
	private Cookie myCookie = new Cookie("id", "");

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
	public String voteOptions(@CookieValue(value = "id", defaultValue = "") String cookieIdValue,@PathVariable String pollId, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (myCookie.getValue().contains(pollId)) {
			Poll p = pollService.getPoll(pollId);
			model.addAttribute("msg","You already voted for poll: " + p.getName());
			model.addAttribute("pollId", pollId);
			response.setStatus(400);
			return "error/400";
		} else {
			if (myCookie.getValue() == "") {
				cookieIdValue = pollId;
			} else {
				cookieIdValue = pollId + "-" + cookieIdValue;
			}
			final int expiryTimeCookie = 60 * 60 * 24;
			final String cookiePath = "/";
			myCookie.setValue(cookieIdValue);
			myCookie.setMaxAge(expiryTimeCookie);
			myCookie.setPath(cookiePath);
			response.addCookie(myCookie);
			String option = request.getParameter("option");
			Poll p = pollService.getPoll(pollId);
			pollService.voteOption(p, option);
			return "redirect:/" + pollId;
		}
	}
}
