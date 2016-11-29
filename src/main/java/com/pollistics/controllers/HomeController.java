package com.pollistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pollistics.services.PollService;

@Controller
public class HomeController {
	@Autowired
	private PollService pollService;

	@GetMapping(value = "/")
	public String greeting(Model model) {
		return "index";
	}
}
