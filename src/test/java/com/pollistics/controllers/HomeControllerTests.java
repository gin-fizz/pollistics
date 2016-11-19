package com.pollistics.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;


@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTests {
	@MockBean
	private PollService pollService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getAllPollsTest() throws Exception {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			options.put("Rood", 12);
			Poll poll1 = new Poll("Mooi kleur", options);
			Poll poll2 = new Poll("Vies kleur", options);
			Poll poll3 = new Poll("Raar kleur", options);
			List<Poll> polls = Arrays.asList(poll1, poll2, poll3);
			when(pollService.getAllPolls()).thenReturn(polls);

			this.mockMvc.perform(get("/")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("name", equalTo("Mooi kleur")))))
			.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("name", equalTo("Vies kleur")))))
			.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("name", equalTo("Raar kleur")))))
			.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("options", Matchers.<String,Integer>hasEntry("Blauw", 1)))))
			.andExpect(model().attribute("polls", hasItem(Matchers.<Poll>hasProperty("options", Matchers.<String,Integer>hasEntry("Rood", 12)))));
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
			when(pollService.createPoll(title, options)).thenReturn("someId123");

			this.mockMvc.perform(post("/")
					.param("title", title)
					.param("option1",option1)
					.param("option2", option2)
					.param("option3", option3))					
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/polls/someId123"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
