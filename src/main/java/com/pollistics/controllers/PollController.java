package com.pollistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pollistics.services.PollService;

@Controller
public class PollController {
	@Autowired
	private PollService pollService;

    @GetMapping(value = "/polls/{pollId}")
    public String greeting(@PathVariable String pollId, Model model) {
        model.addAttribute("poll", pollService.getPoll(pollId));
        return "polls/detail";
    }
}
