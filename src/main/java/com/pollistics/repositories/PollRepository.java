package com.pollistics.repositories;

/**
 * Created by Maliek & Elias on 12/11/2016.
 */

import com.pollistics.models.Poll;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface PollRepository extends MongoRepository<Poll,String> {
		// Insert custom querying methods here
}
