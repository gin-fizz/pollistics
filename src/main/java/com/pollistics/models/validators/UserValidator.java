package com.pollistics.models.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.pollistics.models.*;

public class UserValidator implements Validator {	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isInstance(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if(!user.getUsername().matches("^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")) {
			errors.rejectValue("username", "invalid.username", "Username has to be 3 to 20 characters and contain only letters, number and dots or underscores.");
		}
		if(!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
			errors.rejectValue("password", "invalid.password", "Password has to be at least 8 characters long and needs to contain at least one uppercase letter, one lowercase leter and one number");
		}
	}
}
