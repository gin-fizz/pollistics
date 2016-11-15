package com.pollistics.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
	public void equalsContract() {
		EqualsVerifier.forClass(User.class)
			.withPrefabValues(Poll.class, new Poll(), new Poll())
			.usingGetClass()
			.verify();
	}
}
