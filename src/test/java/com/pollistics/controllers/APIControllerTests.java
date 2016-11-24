package com.pollistics.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.fail;

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
@WebMvcTest(APIController.class)
public class APIControllerTests {
	@MockBean
	private PollService pollService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void findAll() throws Exception {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			options.put("Rood", 12);
			Poll poll1 = new Poll("Mooi kleur", options);
			Poll poll2 = new Poll("Vies kleur", options);
			List<Poll> polls = Arrays.asList(poll1, poll2);
			when(pollService.getAllPolls()).thenReturn(polls);

			this.mockMvc.perform(get("/api/1/polls")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"name\":\"Mooi kleur\",\"options\":{\"Rood\":12,\"Blauw\":1}},{\"name\":\"Vies kleur\",\"options\":{\"Rood\":12,\"Blauw\":1}}]"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void findById() throws Exception {
		try {
			HashMap<String, Integer> options = new HashMap<>();
			options.put("Blauw", 1);
			options.put("Rood", 12);
			Poll poll = new Poll("Mooi kleur", options);
			when(pollService.getPoll("someId")).thenReturn(poll);

			this.mockMvc.perform(get("/api/1/polls/someId"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json("{\"name\":\"Mooi kleur\",\"options\":{\"Rood\":12,\"Blauw\":1}}"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
