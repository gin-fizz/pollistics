package com.pollistics.models.validators;

import static org.junit.Assert.*;

import org.junit.Test;

import com.pollistics.models.User;

import org.springframework.validation.DirectFieldBindingResult;

public class UserValidatorTest {
	@Test
	public void validateUsernameTest() {		
		User user = new User();
		DirectFieldBindingResult errors = new DirectFieldBindingResult(user, "user");
		UserValidator userValidator = new UserValidator();
		user.setUsername("valid_user"); // valid
		user.setPassword("ValidPass123");
		
		userValidator.validate(user, errors);
		assertFalse(errors.hasErrors());
		
		user.setUsername("el"); // too short	
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("username"));
		
		errors =  new DirectFieldBindingResult(user, "user");
		user.setUsername("qwertyuiopasdfghjklzxcvnbm"); // too long
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("username"));
		
		errors =  new DirectFieldBindingResult(user, "user");
		user.setUsername("user*name");	// only _. special chars	
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("username"));  
		
		errors =  new DirectFieldBindingResult(user, "user");
		user.setUsername("username_");	// no ._ at the start or end
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("username"));
		
		errors =  new DirectFieldBindingResult(user, "user");
		user.setUsername("user..name"); // no 2 signs in sequence
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("username"));
	}
}
