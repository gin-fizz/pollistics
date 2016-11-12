package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PollController {

    @Autowired
    private PollRepository pollRepo;

    @GetMapping(value = "/polls/{pollId}")
    public String greeting(@PathVariable String pollId, Model model) {
        Poll polly = pollRepo.findOne(pollId);
        model.addAttribute("poll", polly);
        return "poll";
    }
}
