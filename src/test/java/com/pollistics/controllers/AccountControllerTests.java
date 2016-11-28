package com.pollistics.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.pollistics.models.SecUserDetails;
import com.pollistics.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {
	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getAccountTest() {
		try {
			this.mockMvc.perform(get("/account"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@WithMockUser
	public void getAccountAuthTest() {
		try {
			this.mockMvc.perform(get("/account"))
				.andDo(print())
				.andExpect(status().isOk());
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getRegisterTest() {
		try {
			MvcResult result = this.mockMvc.perform(get("/register"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
			
			assertThat(result.getResponse().getContentAsString().contains("Register"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void postValidRegisterTest() {
		try {
			MvcResult result = this.mockMvc.perform(post("/register")
					.with(csrf())
					.param("username", "testUsername")
					.param("password", "testPass123")
					.param("password1", "testPass123")
					.param("email", "test@test.com"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andReturn();
			assertThat(result.getResponse().getRedirectedUrl().equals("/"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void postNonMatchingPasswordsRegisterTest() {
		try {
			MvcResult result = this.mockMvc.perform(post("/register")
					.with(csrf())
					.param("username", "testUsername")
					.param("password", "testPass123")
					.param("password1", "testPass1234")
					.param("email", "test@test.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
			assertThat(result.getResponse().getContentAsString().contains("Passwords don't match"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void postTakenUsernameRegisterTest() {
		try {
			when(userService.userExists("myUsername")).thenReturn(true);
			MvcResult result = this.mockMvc.perform(post("/register")
					.with(csrf())
					.param("username", "myUsername")
					.param("password", "testPass123")
					.param("password1", "testPass123")
					.param("email", "test@test.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
			assertThat(result.getResponse().getContentAsString().contains("Username is already taken"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
}

