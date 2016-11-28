package com.pollistics.models;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SecUserDetailsTest {
	@Test
	public void membersTest() {
		User u = new User("testuser", "testpass");
		User u2 = new User("testuser2", "testpass2");
		SecUserDetails uSec = new SecUserDetails(u);
		assertThat(uSec.getUser().equals(u));
		uSec.setUser(u2);
		assertThat(uSec.getUser().equals(u2));
		assertThat(uSec.getPassword().equals(u2.getPassword()));
		assertThat(uSec.getUsername().equals(u2.getUsername()));
		assertThat(uSec.getAuthorities() == null);
		assertThat(uSec.isAccountNonExpired());
		assertThat(uSec.isAccountNonLocked());
		assertThat(uSec.isCredentialsNonExpired());
		assertThat(uSec.isEnabled());
	}
}
