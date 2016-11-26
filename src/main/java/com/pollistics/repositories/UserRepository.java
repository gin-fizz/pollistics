package com.pollistics.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pollistics.models.User;

public interface UserRepository extends MongoRepository<User,String> {
	User findByUsername(String username);
}
