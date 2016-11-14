package com.pollistics.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

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
				.andExpect(content().string(containsString("Mooi kleur")))
				.andExpect(content().string(containsString("Blauw")))
				.andExpect(content().string(containsString("Rood")));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
}
