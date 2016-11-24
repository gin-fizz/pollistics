package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
