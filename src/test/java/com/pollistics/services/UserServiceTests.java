package com.pollistics.services;

import com.pollistics.PollisticsApplication;
import com.pollistics.models.User;
import com.pollistics.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PollisticsApplication.class)
public class UserServiceTests {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userService;

	@Test
	public void createUserTest() {
		try {
			User u = new User("gebruikersnaam", "Azerty123");
			userService.createUser(u);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void userExistsTest() {
		User u = userRepo.findAll().get(0);
		boolean result = userService.userExists(u.getUsername());
		assert(result);
		assert(!userService.userExists("stupid fake username"));
	}

	@Test
	public void loadUserByUsernameTest() {
		User u = userRepo.findAll().get(0);
		UserDetails det = userService.loadUserByUsername(u.getUsername());
		assertThat(det.equals(u));
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsernameTestFailing() {
		userService.loadUserByUsername("stupid fake username that doesn't exist");
	}
}
