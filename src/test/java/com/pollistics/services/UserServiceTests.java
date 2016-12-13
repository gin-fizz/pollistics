package com.pollistics.services;

import com.pollistics.PollisticsApplication;
import com.pollistics.models.User;
import com.pollistics.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PollisticsApplication.class)
public class UserServiceTests {
	@MockBean
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
		User u = new User("username","password");
		when(userRepo.findByUsername(u.getUsername())).thenReturn(u);
		assert(userService.userExists(u.getUsername()));
		
		when(userRepo.findByUsername(anyString())).thenReturn(null);		
		assert(!userService.userExists("stupid fake username"));
	}

	@Test
	public void loadUserByUsernameTest() {
		User u = new User("username","password");
		when(userRepo.findByUsername(u.getUsername())).thenReturn(u);
		UserDetails u2 = userService.loadUserByUsername(u.getUsername());
		assertThat(u2.equals(u));
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsernameTestFailing() {
		when(userRepo.findByUsername(anyString())).thenReturn(null);
		userService.loadUserByUsername("stupid fake username that doesn't exist");
	}
}
