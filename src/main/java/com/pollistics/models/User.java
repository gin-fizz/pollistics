package com.pollistics.models;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection="users")
public class User {
	@Id
	private ObjectId id;

	@Length(max=100, message="Name can't be more than 100 characters long")
	private String name;
	private String username;
	@Email(message="Email is invalid")
	private String email;
	private String password;
	private List<Poll> polls;

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		id = ObjectId.get();
		this.polls = new ArrayList<>();
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		id = ObjectId.get();
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		if(this == obj) {
			return true;
		}
		if((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}

		User user = (User) obj;
		return this.getId().equals(user.getId());
	}
}
