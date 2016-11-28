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
	
	@Test
	public void validatePasswordTest() {		
		User user = new User();
		DirectFieldBindingResult errors = new DirectFieldBindingResult(user, "user");
		UserValidator userValidator = new UserValidator();
		user.setUsername("valid_user"); 
		
		user.setPassword("Testje1"); // too short	
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("password"));
		
		errors =  new DirectFieldBindingResult(user, "user");
		user.setPassword("TestTestTest"); // no number
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("password"));
		
		errors =  new DirectFieldBindingResult(user, "user");
		user.setPassword("testtest123"); // no uppercase letter
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("password"));  
		
		errors =  new DirectFieldBindingResult(user, "user");
		user.setPassword("TESTTEST123"); // no lowercase character
		userValidator.validate(user, errors);
		assertTrue(errors.hasFieldErrors("password"));
	}
}
