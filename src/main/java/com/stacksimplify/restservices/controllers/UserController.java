package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNameNotFoundEcxeption;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

//controller -
@RestController
@Validated
@RequestMapping(value = "/users")
public class UserController {
	
	//auto wire user service
	@Autowired
	private UserService userService;
	
	//get all users method
	@GetMapping
	public List<User> getAllUsers() {
		
		return userService.getAllUsers();
	}
	
	//create user
	//requestbody annotatn
	//postmapping annotatn
	@PostMapping
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
		
		try {
			
			userService.createUser(user);
			HttpHeaders headers=new HttpHeaders();
			headers.setLocation(builder.path("/user/{id}").buildAndExpand(user.getUserId()).toUri());
			return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
		} catch (UserExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
		
	}
	
	//getuserby id
	
	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id ){
		
		try {
			return userService.getUserById(id);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
		}
		
	}

	//updateuserbyid
	@PutMapping("/{id}")
	public User  updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		
		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
		
	}
	
	//delete user by id
	
	@DeleteMapping("/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}
	
	//getuserbu username
	@GetMapping("/byusername/{username}")
	public User getUserByUserName(@PathVariable("username") String username) throws UserNameNotFoundEcxeption {
		
		
		
		User user= userService.getUserByUsername(username);
		if (user==null) {
			throw new UserNameNotFoundEcxeption("Username : '"+username+"' not found in user repository");
		}
		return user;
	}
}
