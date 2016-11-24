package com.pollistics;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.pollistics.models.Poll;
import com.pollistics.services.PollService;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PollisticsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private PollService pollService;

	/**
	 * Test that the poll "Amerikaanse verkiezingen" is available on the homepage
	 */
	@Test
	public void homeTest() {
		ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
		assertEquals(response.getStatusCodeValue(), 200);
		assertThat(response.getBody(), containsString("Amerikaanse verkiezingen"));
	}

	/**
	 * Test that /polls/{id} fetches the right poll
	 */
	@Test
	public void pollByIdTest() {
		Poll poll = pollService.getAllPolls().get(0);

		ResponseEntity<String> response = restTemplate.getForEntity("/polls/" + poll.getId(), String.class);
		assertEquals(response.getStatusCodeValue(), 200);
		assertThat(response.getBody(), containsString(poll.getName()));
	}
	
	/**
	 * Test that /polls/{notavalidid} returns an error page
	 */
	@Test
	public void errorPollByIdTest() {
		String uri = "/polls/notavalidid123";
		String uri2 = "/notavalidid123";
		
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		ResponseEntity<String> responseRoot = restTemplate.getForEntity(uri2, String.class);
		
		assertEquals(response.getStatusCodeValue(), 404);
		assertThat(response.getBody(), containsString("404"));
		assertEquals(responseRoot.getStatusCodeValue(), 404);
		assertThat(responseRoot.getBody(), containsString("404"));
	}
}
