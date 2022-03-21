package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.repositories.UserRepository;

//service
@Service
public class UserService {
	
	//autowire userrepo
	@Autowired
	private UserRepository userRepository;

	//getAllUsers method
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
		
	}
	
	//create user method
	public User createUser(User user) {
		
		return userRepository.save(user);
	}
	
	//getUserById
	public Optional<User> getUserById(Long id) {
		
		Optional<User> user= userRepository.findById(id);
		
		return user;
		
	}
	
	//update userby id
	public User updateUserById(Long id, User user) {
		
		user.setId(id);
		return userRepository.save(user);
		
	}
	
	//deleteuserby id
	public void deleteUserById(Long id) {
		
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		}
		
	}
	
	//find by username
	public User getUserByUsername(String username) {
		
		return userRepository.findByUsername(username);
		
	}
	
	
}
