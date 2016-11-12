package com.pollistics.models;

import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="polls")
public class Poll {
	@Id
    public ObjectId id;

    public String name;
    public HashMap<String,Integer> options;
    public User user;

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
}
