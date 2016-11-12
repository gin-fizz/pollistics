package com.pollistics.repositories;

/**
 * Created by Maliek & Elias on 12/11/2016.
 */

import java.util.List;

import com.pollistics.models.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PollRepository extends MongoRepository<Poll,String> {
    //Poll findById(String id);
}
