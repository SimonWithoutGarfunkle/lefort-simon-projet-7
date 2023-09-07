package com.nnk.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserException;
import com.nnk.springboot.repositories.UserRepository;
/**
 * Implement the validation service for User
 * 
 * @author Simon
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	/**
	 * A valid password must containt at least 8 characters with at least : 1 uppercase letter, 1 digit and 1 special characters
	 * 
	 * @param user to test
	 */
	public void validatePasswordUser(User user) {
		logger.info("call validatePasswordUser");
		
		if (user.getPassword().length() < 8) {
	        throw new UserException("Password must contain at least 8 characters");
	    }
		
		if (!user.getPassword().matches(".*[A-Z].*")) {
	        throw new UserException("Password must contain at least 1 uppercase letter");
	    }

	    if (!user.getPassword().matches(".*\\d.*")) {
	        throw new UserException("Password must contain at least 1 digit");
	    }

	    if (!user.getPassword().matches(".*[!@#$%^&*()\\-_=+<>?,;].*")) {
	        throw new UserException("Password must contain at least 1 special character");
	    }
		
		
	}
	
	/**
	 * Verify that the username of a new username is not already registered
	 * 
	 * @param user to verify
	 */
	public void validateNewUserName(User user) {
		logger.info("call validateNewUserName");
		List<User> users = userRepository.findAll();
		for (User userInBase:users) {
			if (userInBase.getUsername().equals(user.getUsername())) {
				throw new UserException("Username already exists");
			}
		}
		
	}

}
