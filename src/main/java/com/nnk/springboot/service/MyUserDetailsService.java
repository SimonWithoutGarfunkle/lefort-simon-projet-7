package com.nnk.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.repositories.UserRepository;

/**
 * Custom implementation of the Spring Security UserDetailsService.
 * This service is responsible for loading user details from the UserRepository.
 * 
 * @author Simon
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	/**
     * Load user details by username.
     *
     * @param username the username of the user to load
     * @return UserDetails object containing the user details
     * @throws UsernameNotFoundException if the user is not found
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (userRepository.findByUsername(username) == null) {
			throw new UsernameNotFoundException("User not found: " + username); 
		}
		return userRepository.findByUsername(username);
	}

}
