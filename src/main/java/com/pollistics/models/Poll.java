package com.pollistics.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Objects;

@Document(collection="polls")
public class Poll {
	@Id
	private ObjectId id;

	private String name;
	private HashMap<String,Integer> options;
	private User user;
	private String slug;

	// todo: orden these constructors with `this(...arg)`

	public Poll(String name, HashMap<String,Integer> options) {
		this.name = name;
		this.options = options;
		this.slug = createSlug();
	}

	private String createSlug() {
		// random three-word combo
		return "meme-meme-meme";
	}

	public Poll(ObjectId id, String name, HashMap<String, Integer> options) {
		this.id = id;
		this.name = name;
		this.options = options;
		this.slug = createSlug();
	}

	public Poll(String name, HashMap<String,Integer> options, String slug) {
		this.name = name;
		this.options = options;
		this.slug = slug;
	}

	public Poll(String name, HashMap<String,Integer> options, User user, String slug) {
		this.name = name;
		this.options = options;
		this.user = user;
		this.slug = slug;
	}

	public Poll(String name, HashMap<String,Integer> options, User user) {
		this.name = name;
		this.options = options;
		this.user = user;
		this.slug = createSlug();
	}

	public Poll() {
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

	public HashMap<String, Integer> getOptions() {
		return options;
	}

	public void setOptions(HashMap<String, Integer> options) {
		this.options = options;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSlug() {
		return slug;
	}

	public boolean vote(String option) {
		if(!options.containsKey(option)) {
			return false;
		} else {
			int val = options.get(option);
			val++;
			options.put(option,val);
			return true;
		}
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

		Poll poll = (Poll) obj;
		return this.getId().equals(poll.getId());
	}
}
