package com.pollistics;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PollisticsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void homeTest() {
		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("<!DOCTYPE HTML>\n" +
				"\n" +
				"<html lang=\"en\">\n" +
				"<head>\n" +
				"    <meta charset=\"UTF-8\" />\n" +
				"    <title>Pollistics</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"    <p>Hello World!</p>\n" +
				"</body>\n" +
				"</html>\n");
	}

	@Test
	public void pollNameTest() {
		String body = this.restTemplate.getForObject("/polls/some-poll", String.class);
		assertThat(body).contains("some-poll");
	}

}
