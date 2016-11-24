package com.pollistics.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class User {
	@Id
	private ObjectId id;

	private String name;
	private String password;
	private List<Poll> polls;

	// todo: hash password 🍃
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.polls = new ArrayList<>();
	}

	public User() {
	}

	public String getId() {
		return id.toHexString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Poll> getPolls() {
		return polls;
	}

	public void addPoll(Poll e) {
		this.polls.add(e);
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;

		User user = (User) obj;
		return this.getId().equals(user.getId());
	}
}
