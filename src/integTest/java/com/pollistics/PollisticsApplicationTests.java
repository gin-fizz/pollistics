package com.pollistics;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PollisticsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	PollService pollService;

    /**
     * Test that the poll "Amerikaanse verkiezingen" is available on the homepage
     */
	@Test
	public void homeTest() {
		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body, containsString("Amerikaanse verkiezingen"));
	}

    /**
     * This tests that /polls/ isn't reached
     */
	@Test
	public void pollsTest() {
		String body = this.restTemplate.getForObject("/polls/", String.class);
		assertThat(body, containsString("404"));
	}
	
	/**
     * Test that /polls/{id} fetches the right poll
     */
	@Test
	public void pollByIdTest() {
		Poll poll = pollService.getAllPolls().get(0);
		
		String body = this.restTemplate.getForObject("/polls/" + poll.getId(), String.class);
		assertThat(body, containsString(poll.getName()));
	}

}
