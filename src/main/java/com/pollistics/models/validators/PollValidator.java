package com.pollistics.models.validators;

import com.pollistics.models.Poll;
import com.pollistics.models.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public static boolean containsItemFromArray(String inputString, String[] items) {
    // Convert the array of String items as a Stream
    // For each element of the Stream call inputString.contains(element)
    // If you have any match returns true, false otherwise
    return Arrays.stream(items).anyMatch(inputString::contains);
}

public class PollValidator implements Validator {
	public final static string[] FORBIDDEN_CHARS = ["?", ".", " ", "#", "/", ",", ":"];

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isInstance(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Poll poll = (Poll) target;

		if (containsItemFromArray(poll.getSlug(), FORBIDDEN_CHARS)) {
			errors.rejectValue("slug", "invalid.slug", "A slug can't contain '?', '-', ' ', '#', ':', ',' or '.'");
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
