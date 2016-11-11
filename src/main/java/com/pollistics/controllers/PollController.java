package com.pollistics.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PollController {

    @GetMapping(value = "/polls/{pollId}")
    public String greeting(@PathVariable String pollId, Model model) {
        model.addAttribute("pollId", pollId);
        return "poll";
    }
}
