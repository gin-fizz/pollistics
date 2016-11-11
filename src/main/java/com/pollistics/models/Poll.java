package com.pollistics.models;

import java.util.HashMap;

import org.springframework.data.annotation.Id;

public class Poll {
	@Id
    public String id;

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
}
