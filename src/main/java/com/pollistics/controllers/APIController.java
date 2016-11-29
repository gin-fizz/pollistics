package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;

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
	public @ResponseBody ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
		String title = poll.getName();
		HashMap<String, Integer> options = poll.getOptions();
		String slug = poll.getSlug();
		String id = pollService.createPoll(title, options, slug);
		return new ResponseEntity<>(pollService.getPoll(id), HttpStatus.OK);
	}
}
