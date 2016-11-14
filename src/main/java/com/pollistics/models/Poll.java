package com.pollistics.models;

import java.util.HashMap;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="polls")
public class Poll {
	@Id
    private ObjectId id;

	private String name;
    private HashMap<String,Integer> options;
    private User user;

    public Poll(String name, HashMap<String,Integer> options) {
        this.name = name;
        this.options = options;
    }

    public Poll(String name, HashMap<String,Integer> options, User user) {
        this.name = name;
        this.options = options;
        this.user = user;
    }

    public Poll() {
    }

    public ObjectId getId() {
		return id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

		Poll poll = (Poll) obj;
		return this.getId().equals(poll.getId());
    }
}
