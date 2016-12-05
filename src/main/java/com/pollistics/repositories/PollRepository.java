package com.pollistics.repositories;

import com.pollistics.models.Poll;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface PollRepository extends MongoRepository<Poll,String> {
	Poll findBySlug(String slug);
}
