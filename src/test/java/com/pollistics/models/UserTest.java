package com.pollistics.models;

//import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

//import java.util.HashMap;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.fail;

public class UserTest {

	@Test
	public void membersTest() {
		User u = new User("testuser", "testpass");
		assertThat(u.getName().equals("testuser"));
		assertThat(u.getPassword().equals("testpass"));
		// todo: this should be hashieshd
		u.setName("memer");
		assertThat(u.getName().equals("memer"));
		u.setPassword("dumbpass");
		assertThat(u.getPassword().equals("dumbpass"));
		// todo: this should be hashieshd
		Poll p = new Poll();
		u.addPoll(p);
		assertThat(u.getPolls().contains(p));
	}

	@Test
	public void equalsTest() {
		// not equal to empty object
		Object o = new Object();
		User u = new User("testuser", "testpass");
		assertNotEquals(u, o);

		// not equal to null
		assertNotEquals(u, null);
	}

	/*
	@Test
	public void equalsContract() {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			Poll p = new Poll("Kleuren", options);
			HashMap<String, Integer> optionsTwo = new HashMap<>();
			options.put("Obama", 1);
			Poll q = new Poll("Verkiezingen", options);
			EqualsVerifier.forClass(User.class)
				.withPrefabValues(Poll.class, p, q)
				.usingGetClass()
				.verify();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	*/
}
