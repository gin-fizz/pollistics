package com.pollistics.models;

import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class PollTest {

	@Test
	public void membersTest() {
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		Poll p = new Poll("Kleuren", options);
		assertThat(p.getName().equals("Kleuren"));
		p.setName("andere naam");
		assertThat(p.getName().equals("andere naam"));
		assertThat(p.getOptions().keySet().contains("Blauw"));
		assertThat(p.getOptions().entrySet().contains(1));
	}

	@Test
	public void userTest() {
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		User user = new User("someone", "somepass");
		Poll p = new Poll("Kleuren", options, user);
		assertThat(p.getUser().equals(user));
		User newUser = new User("other person", "pass");
		p.setUser(newUser);
		assertThat(p.getUser().equals(newUser));
	}
}
