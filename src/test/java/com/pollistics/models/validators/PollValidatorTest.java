package com.pollistics.models.validators;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import com.pollistics.models.Poll;

import org.springframework.validation.DirectFieldBindingResult;

public class PollValidatorTest {
	@Test
	public void validateOptionsTest() {		
		Poll poll = new Poll();
		DirectFieldBindingResult errors = new DirectFieldBindingResult(poll, "poll");
		PollValidator pollValidator = new PollValidator();
		HashMap<String, Integer> options = new HashMap<String,Integer>();
		poll.setTitle("Question?");
		options.put("option1", 0);
		options.put("option2", 0);
		
		poll.setOptions(options);
		pollValidator.validate(poll, errors);
		assertFalse(errors.hasErrors());
		
		options.put("option3", 10);
		poll.setOptions(options); // more than 0 votes on option	
		pollValidator.validate(poll, errors);
		assertTrue(errors.hasFieldErrors("options"));
		
		errors =  new DirectFieldBindingResult(poll, "poll");
		options.clear();
		options.put("option1", 1); // only 1 option
		pollValidator.validate(poll, errors);
		assertTrue(errors.hasFieldErrors("options"));
	}
}
