package com.pollistics.repositories;

/**
 * Created by Maliek & Elias on 12/11/2016.
 */

import com.pollistics.models.Poll;
import com.pollistics.models.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface PollRepository extends MongoRepository<Poll,String> {
	List<Poll> findByUser(User user);
}
