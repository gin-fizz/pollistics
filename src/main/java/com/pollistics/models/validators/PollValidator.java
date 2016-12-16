package com.pollistics.models.validators;

import java.util.regex.Pattern;
import com.pollistics.models.Poll;
import com.pollistics.models.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PollValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isInstance(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Poll poll = (Poll) target;

		if (Pattern.matches("\\?|\\.|\\s|#|\\/|:", poll.getSlug())) {
			errors.rejectValue("slug", "invalid.slug", "A slug can't contain ?, -, ' ', #, : or .");
		}

		if(poll.getOptions().size() < 2) {
			errors.rejectValue("options", "invalid.options", "A poll has at least 2 options");
		}

		int nVotes = poll.getOptions().entrySet()
			.stream()
			.map(option -> option.getValue())
			.reduce(0, (total,votes) -> total + votes);

		if(nVotes != 0) {
			errors.rejectValue("options", "invalid.options", "Poll options can't have votes upon creation");
		}
	}
}
