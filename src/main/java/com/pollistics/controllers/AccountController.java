package com.pollistics.controllers;

import com.pollistics.models.User;
import com.pollistics.models.validators.UserValidator;
import com.pollistics.services.PollService;
import com.pollistics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class AccountController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PollService pollService;

	@GetMapping(value = "/account")
	public String account(Model model) {
		return "account/index";
	}

	@GetMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);
		return "account/login";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@GetMapping(value = "/register")
	public String register(Model model) {
		return "account/register";
	}

	@PostMapping(value = "/register")
	public String postRegister(HttpServletRequest request, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		if(!request.getParameter("password1").equals(request.getParameter("password"))) {
			result.addError(new FieldError("user", "password1", "Passwords don't match"));
		}
		if(userService.userExists(user.getUsername())) {
			result.addError(new FieldError("user", "username", "Username is already taken"));
		}

		UserValidator userValidator = new UserValidator();
		userValidator.validate(user, result);

		if(result.hasErrors()) {
			model.addAttribute("errors", result);
			return "account/register";
		}
		else {
			userService.createUser(user);
			return "redirect:/";
		}
	}
	
	@GetMapping(value = "/account/polls")
	public String polls(Model model) {
		model.addAttribute("polls", pollService.getPolls((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
		return "account/polls";
	}
	
	
}
