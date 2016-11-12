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
public class HomeController {
    @Autowired
    private PollRepository pollRepo;

    @GetMapping(value = "/")
    public String greeting(Model model) {
        model.addAttribute("greeting", "Hello World!");
        List<Poll> pollList = pollRepo.findAll();
        model.addAttribute("polls_List",pollList);
        return "index";
    }
}
