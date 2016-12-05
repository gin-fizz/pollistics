package com.pollistics.services;

import com.pollistics.models.Poll;
import com.pollistics.models.User;
import com.pollistics.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PollService {
	@Autowired
	private PollRepository pollRepo;

	public Poll getPoll(String id) {
		return pollRepo.findOne(id);
	}

	public List<Poll> getAllPolls() {
		return pollRepo.findAll();
	}

	public String createPoll(String title, HashMap<String, Integer> options) {
		Poll poll = pollRepo.insert(new Poll(title, options));
		return poll.getId();
	}
	
	public String createPoll(String title, HashMap<String, Integer> options, User user) {
		Poll poll = pollRepo.insert(new Poll(title, options, user));
		return poll.getId();
	}

	public boolean deletePoll(String id) {
		try {
			pollRepo.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean voteOption(Poll p, String option) {
		boolean result = p.vote(option);
		if (!result) {
			return false;
		}
		pollRepo.save(p); // todo false when not works
		return true;
	}
	
	public List<Poll> getPolls(User user) {
		return pollRepo.findByUser(user);
	}
}
