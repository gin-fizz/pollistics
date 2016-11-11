package com.pollistics.models;

import java.util.HashMap;

import org.springframework.data.annotation.Id;

public class User {
	@Id
    private String id;

	private String name;
    private String password;
    private HashMap<Integer,String> polls;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
