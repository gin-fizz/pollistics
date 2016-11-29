package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.HashMap;

/**
 * Created by Maliek on 24/11/2016.
 */

@RestController
@RequestMapping("/api/1/polls") // todo: this should have a subclass or something for all poll-things
public class APIController {
	@Autowired
	private PollService pollService;

	@GetMapping
	public List<Poll> findAll() {
		return pollService.getAllPolls();
	}

	@GetMapping(value = "{id}")
	public Poll findById(@PathVariable("id") String id) {
		return pollService.getPoll(id);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Poll createPoll(HttpServletRequest request) {
		String title = request.getParameter("title");
		String option1 = request.getParameter("option1");
		String option2 = request.getParameter("option2");
		HashMap<String, Integer> options = new HashMap<>();
		options.put(option1, 0);
		options.put(option2, 0);
		String id = pollService.createPoll(title, options);
		return pollService.getPoll(id);
	}
}
