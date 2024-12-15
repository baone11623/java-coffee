package com.coffee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffee.model.User;
import com.coffee.repository.UserRepository;

@Service
public class UserService {
	 	@Autowired
	    private UserRepository userRepository;

	    public User findByUsername(String username) {
	        return userRepository.findAll().stream()
	                .filter(user -> user.getUsername().equals(username))
	                .findFirst()
	                .orElse(null);
	    }

	    public boolean authenticate(String username, String password) {
	    	String usernameTrim = username.trim();
	    	String passwordTrim = password.trim();
	        User user = findByUsername(usernameTrim);
	        return user != null && user.getPassword().equals(passwordTrim);
	    }

	    public User save(User user) {
	        return userRepository.save(user);
	    }
}
