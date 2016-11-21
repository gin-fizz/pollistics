package com.pollistics.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pollistics.PollisticsApplication;
import com.pollistics.models.Poll;
import com.pollistics.repositories.PollRepository;

import java.util.HashMap;

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
		assertThat(pollService.getAllPolls() != null);
		assertThat(pollService.getAllPolls().get(0) != null);
		assertThat(pollService.getAllPolls().equals(pollRepo.findAll()));
	}

	@Test
	public void createPollTest() {
		Poll poll = pollRepo.findAll().get(0);
		String id = pollService.createPoll(poll.getName(), poll.getOptions());
		assertThat(id instanceof String);

		Poll pollById = pollService.getPoll(id);
		assertThat(id.equals(pollById.getId()));
	}

	@Test
	public void voteOptionTest() {
		Poll poll = pollRepo.findAll().get(0);
		HashMap<String, Integer> opts = poll.getOptions();
		String[] keys = opts.keySet().toArray(new String[0]);
		String first = keys[0];

		int firstVal = opts.get(first);

		boolean result = pollService.voteOption(poll, first);
		assert result;

		assertThat(opts.get(first).equals(firstVal + 1));

		boolean res = pollService.voteOption(poll, "somerandomstring");
		assert !res;
	}
}
