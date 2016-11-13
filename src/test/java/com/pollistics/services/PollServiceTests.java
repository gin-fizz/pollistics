package com.pollistics.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pollistics.PollisticsApplication;
import com.pollistics.models.Poll;
import com.pollistics.repositories.PollRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PollisticsApplication.class)
public class PollServiceTests {
	@Autowired
	private PollRepository pollRepo;
	
	@Autowired
	private PollService pollService;
	
	@Test
	public void getPollByIdTest() {
		Poll poll = pollRepo.findAll().get(0);
		Poll pollById = pollService.getPoll(poll.getId().toString());
		
		assertThat(poll.equals(pollById));
	}
	
	@Test
	public void getAllPollsTest() {
		assertThat(pollService.getAllPolls() instanceof List);
		assertThat(pollService.getAllPolls().get(0) instanceof Poll);
		assertThat(pollService.getAllPolls().equals(pollRepo.findAll()));
	}
}
