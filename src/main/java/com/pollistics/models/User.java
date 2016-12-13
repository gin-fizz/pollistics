package com.pollistics.models;

import java.util.Collection;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection="users")
public class User implements UserDetails {
	@Id
	private ObjectId id;

	@Length(max=100, message="Name can't be more than 100 characters long")
	private String name;
	private String username;
	@Email(message="Email is invalid")
	private String email;
	private String password;

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		id = ObjectId.get();
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		id = ObjectId.get();
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
