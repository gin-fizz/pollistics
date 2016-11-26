package com.pollistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pollistics.models.SecUserDetails;
import com.pollistics.models.User;
import com.pollistics.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder;

	public boolean userExists(String username) {
		return userRepository.findByUsername(username) != null;
	}

	public void createUser(User user) {
		passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		userRepository.insert(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException(username);
		} else{
			UserDetails details = new SecUserDetails(user);
			return details;
		}
	}
}
