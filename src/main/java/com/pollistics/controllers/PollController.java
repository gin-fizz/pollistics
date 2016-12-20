package com.pollistics.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pollistics.models.Poll;
import com.pollistics.models.User;
import com.pollistics.models.validators.PollValidator;
import com.pollistics.services.PollService;
import com.pollistics.utils.CookieBuilder;

@Controller
public class PollController {

	@Autowired
	private PollService pollService;

	@GetMapping(value = "/polls")
	public String allPolls(Model model) {
		model.addAttribute("polls", pollService.getAllPolls());
		return "polls/overview";
	}

	@GetMapping(value = {"/polls/{slug}", "/{slug}"})
	public String pollDetail(@PathVariable String slug, Model model, HttpServletResponse response) {
		Poll poll = pollService.getPoll(slug);
		if(poll == null) {
			response.setStatus(404);
			return "error/404";
		}
		model.addAttribute("poll", pollService.getPoll(slug));
		return "polls/detail";
	}

	@GetMapping(value = {"/polls/{slug}/results", "/{slug}/results"})
	public String pollResults(@PathVariable String slug, Model model, HttpServletResponse response) {
		Poll poll = pollService.getPoll(slug);
		if(poll == null) {
			response.setStatus(404);
			return "error/404";
		}
		model.addAttribute("poll", pollService.getPoll(slug));
		return "polls/results";
	}

	@PostMapping(value = "/polls/create")
	public String createPoll(HttpServletRequest request, @Valid @ModelAttribute("poll") Poll poll, BindingResult result, Principal principal, Model model) throws UnsupportedEncodingException {
		HashMap<String, Integer> options =
			(HashMap<String, Integer>)request.getParameterMap().entrySet().stream()
				.filter((param) -> param.getKey().startsWith("option") && !param.getValue()[0].trim().isEmpty())
				.map((param) -> param.getValue()[0])
				.collect(Collectors.<String,String,Integer>toMap((option) -> option, (option) -> 0));

		poll.setOptions(options);

		if (request.getParameter("slug") != null) {
			poll.setSlug(request.getParameter("slug"));
		}

		PollValidator pollValidator = new PollValidator();
		pollValidator.validate(poll, result);

		if (pollService.getPoll(poll.getSlug()) != null) {
			result.addError(new FieldError("poll", "slug", "This url is already taken"));
		}

		if(result.hasErrors()) {
			model.addAttribute("errors", result);
			return "index";
		} else {
			String slug;
			// todo: figure out casting here
			if (request.getParameter("slug") != null) {
				if (principal != null) {
					slug = pollService.createPoll(poll.getTitle(), options, request.getParameter("slug"), (User) ((Authentication) principal).getPrincipal());
				} else {
					slug = pollService.createPoll(poll.getTitle(), options, request.getParameter("slug"));
				}
			} else {
				if (principal != null) {
					slug = pollService.createPoll(poll.getTitle(), options, (User) ((Authentication) principal).getPrincipal());
				} else {
					slug = pollService.createPoll(poll.getTitle(), options);
				}
			}
			String encodedSlug = URLEncoder.encode(slug, "UTF-8");
			return "redirect:/" + encodedSlug;
		}
	}

	@PostMapping(value = "/polls/delete/{slug}")
	public String deletePoll(@PathVariable String slug, HttpServletResponse response, Principal principal, RedirectAttributes redirectAttrs) {
		if(principal == null || !((User) ((Authentication) principal).getPrincipal()).equals(pollService.getPoll(slug).getUser())) {
			response.setStatus(401);
			return "error/401";
		}
		
		boolean result = pollService.deletePoll(slug);
		if (result) {
			redirectAttrs.addFlashAttribute("message", "The poll has deleted successfully!");
			redirectAttrs.addFlashAttribute("message_type", "success");
			return "redirect:/account/polls";
		} else {
			response.setStatus(404);
			return "error/404";
		}
	}

	@PostMapping(value = "/polls/vote/{slug}")
	public String voteOptions(@CookieValue(value = "pollistics-voted", defaultValue = "") String originalCookie, @PathVariable String slug, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs) throws UnsupportedEncodingException {
		String encodedSlug = URLEncoder.encode(slug, "UTF-8");
		Poll p = pollService.getPoll(slug);
		// already voted
		if (originalCookie.contains(slug)) {
			redirectAttrs.addFlashAttribute("message", "You already voted for this poll");
			redirectAttrs.addFlashAttribute("message_type", "error");
			response.setStatus(403);
			return "redirect:/" + encodedSlug + "/results";
		} else {			
			response.addCookie(CookieBuilder.getVotedCookie(originalCookie, slug));

			String option = request.getParameter("option");
			pollService.voteOption(p, option);
			
			return "redirect:/" + encodedSlug + "/results";
		}
	}
}
