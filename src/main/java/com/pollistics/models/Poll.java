package com.pollistics.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Objects;

@Document(collection="polls")
public class Poll {
	@Id
	private ObjectId id;

	@Length(min=3, max=500, message="Poll title must be be between 3 and 500 characters")
	private String title;
	private HashMap<String,Integer> options;
	private User user;

	@Indexed(unique = true)
	private String slug;

	// todo: orden these constructors with `this(...arg)`

	public Poll(String title, HashMap<String,Integer> options) {
		this.title = title;
		this.options = options;
		this.slug = createSlug();
	}

	private String createSlug() {
		// random three-word combo
		return "meme-meme-meme";
	}

	public Poll(ObjectId id, String title, HashMap<String, Integer> options) {
		this.id = id;
		this.title = title;
		this.options = options;
		this.slug = createSlug();
	}

	public Poll(String title, HashMap<String,Integer> options, String slug) {
		this.title = title;
		this.options = options;
		this.slug = slug;
	}

	public Poll(String title, HashMap<String,Integer> options, User user, String slug) {
		this.title = title;
		this.options = options;
		this.user = user;
		this.slug = slug;
	}

	public Poll(String title, HashMap<String,Integer> options, User user) {
		this.title = title;
		this.options = options;
		this.user = user;
		this.slug = createSlug();
	}

	public Poll() {
	}

	public String getId() {
		return id.toHexString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
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
