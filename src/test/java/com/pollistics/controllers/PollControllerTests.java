package com.pollistics.controllers;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PollControllerTests {
	@MockBean
	private PollService pollService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getPollByIdTest() throws Exception {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			options.put("Rood", 12);
			Poll poll = new Poll(new ObjectId("5830364e1c27ea512eea301c"), "Mooi kleur", options);
			when(pollService.getPoll("5830364e1c27ea512eea301c")).thenReturn(poll);
			when(pollService.getPoll("NotARealId")).thenReturn(null);

			this.mockMvc.perform(get("/polls/5830364e1c27ea512eea301c"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attribute("poll", Matchers.<Poll>hasProperty("name", equalTo("Mooi kleur"))))
				.andExpect(model().attribute("poll", Matchers.<Poll>hasProperty("options", Matchers.hasEntry("Blauw", 1))))
				.andExpect(model().attribute("poll", Matchers.<Poll>hasProperty("options", Matchers.hasEntry("Rood", 12))));
			this.mockMvc.perform(get("/polls/someImpossibleId"))
				.andExpect(status().isNotFound());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void createPollTest() {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			String title = "Poll title";
			String option1 = "option1";
			String option2 = "option2";
			String option3 = "option3";
			options.put(option1, 0);
			options.put(option2, 0);
			options.put(option3, 0);
			when(pollService.createPoll(title, options)).thenReturn("5830364e1c27ea512eea301a");

			this.mockMvc.perform(post("/polls/create")
				.with(csrf())
				.param("title", title)
				.param("option1",option1)
				.param("option2", option2)
				.param("option3", option3))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/5830364e1c27ea512eea301a"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void deletePollTest() {
		try {
			when(pollService.deletePoll("someId123")).thenReturn(true);
			when(pollService.deletePoll("someFakeId")).thenReturn(false);

			this.mockMvc.perform(post("/polls/delete/someId123").with(csrf()))
				.andExpect(flash().attribute("message", "The poll has deleted successfully!"))
				.andExpect(redirectedUrl("/"));

			this.mockMvc.perform(post("/polls/delete/someFakeId").with(csrf()))
				.andExpect(status().is4xxClientError());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void voteTest() {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			options.put("Rood", 12);
			Poll p = new Poll(new ObjectId("5830364e1c27ea512eea301a"), "Welk kleur?", options);
			when(pollService.voteOption(p, "Rood")).thenReturn(true);

			this.mockMvc.perform(post("/polls/vote/5830364e1c27ea512eea301a").with(csrf())
				.param("option", "Rood"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/5830364e1c27ea512eea301a"))
				.andExpect(cookie().exists("id"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getAllPollsTest() throws Exception {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			options.put("Rood", 12);
			Poll poll1 = new Poll(new ObjectId("5830364e1c27ea512eea301a"), "Mooi kleur", options);
			Poll poll2 = new Poll(new ObjectId("5830364e1c27ea512eea301b"), "Vies kleur", options);
			Poll poll3 = new Poll(new ObjectId("5830364e1c27ea512eea301c"), "Raar kleur", options);
			List<Poll> polls = Arrays.asList(poll1, poll2, poll3);
			when(pollService.getAllPolls()).thenReturn(polls);

			this.mockMvc.perform(get("/polls/")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("name", equalTo("Mooi kleur")))))
				.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("name", equalTo("Vies kleur")))))
				.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("name", equalTo("Raar kleur")))))
				.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("options", Matchers.hasEntry("Blauw", 1)))))
				.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("options", Matchers.hasEntry("Rood", 12)))));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/*
	@Test
	public void voteAgainTestOther() {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			options.put("Rood", 12);
			Poll p = new Poll("Welk kleur?", options);
			when(pollService.voteOption(p, "Rood")).thenReturn(true);

			Cookie c = new Cookie("id", "someOtherId");
			this.mockMvc.perform(post("/polls/vote/someId123")
				.cookie(c)
				.with(csrf())
				.param("option", "Rood"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/someId123"))
				.andExpect(cookie().exists("id"))
				.andExpect(cookie().value("id", "someId123-someOtherId"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void voteAgainTest() {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			options.put("Rood", 12);
			Poll p = new Poll("Welk kleur?", options);
			when(pollService.voteOption(p, "Rood")).thenReturn(true);

			Cookie c = new Cookie("id", "someId123");
			this.mockMvc.perform(post("/polls/vote/someId123")
				.cookie(c)
				.with(csrf())
				.param("option", "Rood"))
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(cookie().exists("id"))
				.andExpect(cookie().value("id", "someId123"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	*/
}
