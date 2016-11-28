package com.pollistics.models;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

	@Test
	public void membersTest() {
		User u = new User("testuser", "testpass");
		assertThat(u.getUsername().equals("testuser"));
		assertThat(u.getPassword().equals("testpass"));
		u.setName("memer");
		assertThat(u.getName().equals("memer"));
		u.setPassword("dumbpass");
		assertThat(u.getPassword().equals("dumbpass"));
		u.setUsername("testusername");
		assertThat(u.getUsername().equals("testusername"));
		u.setEmail("e@a.com");
		assertThat(u.getEmail().equals("e@a.com"));
		
		Poll p = new Poll();
		u.addPoll(p);
		assertThat(u.getPolls().contains(p));
	}

	@Test
	public void testEquals() {
		User u1 = new User("username","e@a.com","password");
		User u2 = new User("username","e@a.com","password");
		assertFalse(u1.equals(new Poll()));
		assertFalse(u1.equals(u2));
		assertFalse(u1.hashCode() == u2.hashCode());
	}
}
