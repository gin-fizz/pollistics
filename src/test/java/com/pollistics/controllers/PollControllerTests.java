package com.pollistics.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@WebMvcTest(PollController.class)
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
			when(pollService.getPoll("someId123")).thenReturn(new Poll("Mooi kleur", options));

			this.mockMvc.perform(get("/polls/someId123")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attribute("poll", Matchers.<Poll>hasProperty("name", equalTo("Mooi kleur"))))
				.andExpect(model().attribute("poll", Matchers.<Poll>hasProperty("options", Matchers.<String,Integer>hasEntry("Blauw", 1))))
				.andExpect(model().attribute("poll", Matchers.<Poll>hasProperty("options", Matchers.<String,Integer>hasEntry("Rood", 12))));
			
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

			this.mockMvc.perform(post("/polls/create")
					.param("title", title)
					.param("option1",option1)
					.param("option2", option2)
					.param("option3", option3))					
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/someId123"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
