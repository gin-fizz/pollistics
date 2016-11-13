package com.pollistics.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pollistics.models.Poll;
import com.pollistics.repositories.PollRepository;

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
}
