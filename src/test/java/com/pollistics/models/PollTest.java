package com.pollistics.models;

//import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.fail;

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
		HashMap<String, Integer> newOptions = new HashMap<>();
		newOptions.put("Rood", 5);
		p.setOptions(newOptions);
		assertThat(p.getOptions().keySet().contains("Rood"));
		assertThat(p.getOptions().entrySet().contains(4));
		assertThat(p.getOptions().equals(newOptions));
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

	@Test
	public void voteTest() {
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		Poll p = new Poll("Kleuren", options);
		boolean worked = p.vote("Blauw");
		assert worked;
		assertThat(p.getOptions().get("Blauw").equals(2));
	}

	@Test
	public void equalsTest() {
		// not equal to meme
		Object o = new Object();
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		Poll p = new Poll("Kleuren", options);
		assertNotEquals(p, o);
	}

	/*
	@Test
	public void equalsContract() {
		try {
			EqualsVerifier.forClass(Poll.class)
				.withPrefabValues(User.class, new User("user", "pass"), new User("bla", "bla"))
				.usingGetClass()
				.verify();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}*/
}
