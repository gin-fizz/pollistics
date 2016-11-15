package com.pollistics.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

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
			.andExpect(content().string(containsString("Mooi kleur")))
			.andExpect(content().string(containsString("Blauw")))
			.andExpect(content().string(containsString("Rood")))
			.andExpect(content().string(containsString("Vies kleur")))
			.andExpect(content().string(containsString("Raar kleur")));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
