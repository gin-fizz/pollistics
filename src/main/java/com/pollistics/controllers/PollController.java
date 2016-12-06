package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.models.User;
import com.pollistics.models.validators.PollValidator;
import com.pollistics.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
public class PollController {

	@Autowired
	private PollService pollService;
	private Cookie cookie = new Cookie("id", "");

	@GetMapping(value = "/polls")
	public String allPolls(Model model) {
		model.addAttribute("polls", pollService.getAllPolls());
		return "polls/overview";
	}

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

	@PostMapping(value = "/polls/delete/{pollId}")
	public String deletePoll(@PathVariable String pollId, HttpServletResponse response, RedirectAttributes redirectAttrs) {
		boolean result = pollService.deletePoll(pollId);
		if (result) {
			redirectAttrs.addFlashAttribute("message", "The poll has deleted successfully!");
			redirectAttrs.addFlashAttribute("message_type", "success");
			return "redirect:/";
		} else {
			response.setStatus(404);
			return "error/404";
		}
	}

	@PostMapping(value = "/polls/vote/{pollId}")
	public String voteOptions(@CookieValue(value = "id", defaultValue = "") String cookieIdValue, @PathVariable String pollId, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		if (cookie.getValue().contains(pollId)) {
			Poll p = pollService.getPoll(pollId);
			model.addAttribute("msg", MessageFormat.format("You already voted for poll: {0}", p.getTitle()));
			model.addAttribute("previous", MessageFormat.format("/{0}/", pollId));
			response.setStatus(403);
			return "error/403";
		} else {
			String value;
			if (cookie.getValue().equals("")) {
				value = pollId;
			} else {
				value = MessageFormat.format("{0}-{1}", pollId, cookieIdValue);
			}
			final int expiryTimeCookie = 2147483647; // maximum of int
			final String cookiePath = "/";
			cookie.setValue(value);
			cookie.setMaxAge(expiryTimeCookie);
			cookie.setPath(cookiePath);
			response.addCookie(cookie);
			String option = request.getParameter("option");
			Poll p = pollService.getPoll(pollId);
			pollService.voteOption(p, option);
			String encodedSlug = URLEncoder.encode(pollId, "UTF-8");
			return "redirect:/" + encodedSlug;
		}
	}
}
