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
		assertThat(response.getBody(), containsString("Pollistics"));
	}

	/**
	 * Test that the following fetches the right poll
	 * /polls/{id}
	 * /{id}
	 * /{slug}
	 */
	@Test
	public void pollByIdTest() {
		Poll poll = pollService.getAllPolls().get(0);

		ResponseEntity<String> response = restTemplate.getForEntity("/polls/" + poll.getId(), String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertThat(response.getBody(), containsString(poll.getTitle()));

		ResponseEntity<String> resp = restTemplate.getForEntity("/" + poll.getId(), String.class);
		assertEquals(200, resp.getStatusCodeValue());
		assertThat(resp.getBody(), containsString(poll.getTitle()));

		//ResponseEntity<String> res = restTemplate.getForEntity("/" + poll.getSlug(), String.class);
		//assertEquals(200, res.getStatusCodeValue());
		//assertThat(res.getBody(), containsString(poll.getName()));
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
