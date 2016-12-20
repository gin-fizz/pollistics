package com.pollistics.services;

import com.pollistics.PollisticsApplication;
import com.pollistics.models.Poll;
import com.pollistics.models.User;
import com.pollistics.repositories.PollRepository;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PollisticsApplication.class)
public class PollServiceTests {
	@MockBean
	private PollRepository pollRepo;

	@Autowired
	private PollService pollService;

	@Test
	public void getPollByIdTest() {
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		ObjectId id = ObjectId.get();
		Poll p = new Poll(id, "Mooi kleur", options);
		when(pollRepo.findOne(id.toString())).thenReturn(p);

		assertThat(pollService.getPoll(id.toString()).equals(p));
	}

	@Test
	public void getPollBySlugTest() {
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		Poll p = new Poll("Mooi kleur", options, "nice-meme-poll");
		when(pollRepo.findBySlug("nice-meme-poll")).thenReturn(p);

		assertThat(pollService.getPoll("nice-meme-poll").equals(p));
	}

	@Test
	public void getAllPollsTest() {
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		Poll p = new Poll("Mooi kleur", options);
		Poll p2 = new Poll("Vies kleur", options);
		List<Poll> polls = new ArrayList<Poll>();
		polls.add(p);
		polls.add(p2);
		when(pollRepo.findAll()).thenReturn(polls);

		assertThat(pollService.getAllPolls().equals(polls));
	}

	@Test
	public void getPollsByUserTest() {
		User u = new User("name","password");
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		Poll p = new Poll("Mooi kleur", options, u);
		List<Poll> polls = new ArrayList<Poll>();
		polls.add(p);
		when(pollRepo.findByUser(u)).thenReturn(polls);

		assertThat(pollService.getPolls(u).equals(polls));
	}

	@Test
	public void createPollTest() {
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		Poll p = new Poll(ObjectId.get(), "my Poll Title", options);
		when(pollRepo.insert(any(Poll.class))).thenReturn(p);
		Object id = pollService.createPoll("my Poll Title", options);

		assertThat(id instanceof String);
		assertThat(id.equals(p.getId()));
	}

	@Test
	public void createPollWithSlugTest() {
		String slug = "nice-meme-poll";
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		options.put("Grun", 5);
		options.put("Oronje", 8);
		Poll p = new Poll(ObjectId.get(), "welk kleur?", options);
		p.setSlug(slug);
		when(pollRepo.insert(any(Poll.class))).thenReturn(p);

		Object answer = pollService.createPoll("welk kleur?", options, slug);

		assertThat(answer instanceof String);
		assertThat(answer.equals(slug));
	}

	@Test
	public void createPollWithUserTest() {
		String slug = "leuke-funny-poll";
		User u = new User("name", "password");
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		Poll p = new Poll(ObjectId.get(), "my Poll Title", options);
		p.setUser(u);
		p.setSlug(slug);
		when(pollRepo.insert(any(Poll.class))).thenReturn(p);

		Object id = pollService.createPoll("my Poll Title", options, slug, u);

		assertThat(id instanceof String);
		assertThat(id.equals(slug));
	}

	@Test
	public void createPollWithUserAndSlugTest() {
		User u = new User("name", "password");
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		Poll p = new Poll(ObjectId.get(), "my Poll Title", options);
		p.setUser(u);
		when(pollRepo.insert(any(Poll.class))).thenReturn(p);

		Object id = pollService.createPoll("my Poll Title", options, u);

		assertThat(id instanceof String);
		assertThat(id.equals(p.getId()));
	}

	@Test
	public void deletePollTest() {
		String slug = "myslug";
		doNothing().when(pollRepo).deleteBySlug(anyString());
		assertThat(pollService.deletePoll(slug));
		doThrow(new IllegalArgumentException()).when(pollRepo).deleteBySlug(anyString());
		assertFalse(pollService.deletePoll(slug));
	}

	@Test
	public void voteOptionTest() {
		HashMap<String, Integer> options = new HashMap<>();
		options.put("Blauw", 1);
		options.put("Rood", 12);
		Poll p = new Poll(ObjectId.get(), "my Poll Title", options);

		assertThat(pollService.voteOption(p,"Blauw"));
		assertThat(p.getOptions().get("Blauw").equals(2));
		assertFalse(pollService.voteOption(p, "somerandomstring"));
	}
}
