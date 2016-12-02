package com.pollistics.services;

import com.pollistics.PollisticsApplication;
import com.pollistics.models.Poll;
import com.pollistics.repositories.PollRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

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
		Poll pollById = pollService.getPoll(poll.getId());
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
		String id = pollService.createPoll(poll.getTitle(), poll.getOptions());
		assertThat(id instanceof String);

		Poll pollById = pollService.getPoll(id);
		assertThat(id.equals(pollById.getId()));
	}

	@Test
	public void deletePollTest() {
		Poll first = pollRepo.findAll().get(0);
		pollService.deletePoll(first.getId());
		Poll second = pollRepo.findAll().get(0);
		assertThat(!first.equals(second));
		boolean result = pollService.deletePoll("stomme id die niet bestaat");
		assertThat(!result);
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
