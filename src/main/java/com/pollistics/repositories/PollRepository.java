package com.pollistics.repositories;

import com.pollistics.models.Poll;
import com.pollistics.models.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface PollRepository extends MongoRepository<Poll,String> {
	Poll findBySlug(String slug);
	List<Poll> findByUser(User user);
	void deleteBySlug(String slug);
}
