package com.pollistics.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pollistics.models.User;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
	User findByUsername(String username);
}
