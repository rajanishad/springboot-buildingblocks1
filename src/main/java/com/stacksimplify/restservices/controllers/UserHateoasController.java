package com.stacksimplify.restservices.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

import net.bytebuddy.asm.Advice.This;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	// get all users method
	@GetMapping
	public CollectionModel<User> getAllUsers() throws UserNotFoundException {

		List<User> allUsers= userService.getAllUsers();
		for (User user : allUsers) {
			//self link
			Long userid=user.getUserId();
			Link selfLink= WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selfLink);
			//relationshif link with get all order
			CollectionModel<Order> orders=WebMvcLinkBuilder.methodOn(OrderHateoasController.class)
					.getAllOrders(userid);
			Link ordersLink=WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
			user.add(ordersLink);
			
		}
		//self link for get all users
		Link sleflinkgetallusers=WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
		
		CollectionModel<User> finalResurce= CollectionModel.of(allUsers, sleflinkgetallusers);
		return finalResurce;
	}

	// getuserby id

	@GetMapping("/{id}")
	public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {

		try {
			Optional<User> userOptional= userService.getUserById(id);
			User user=userOptional.get();
			Long userid= user.getUserId();
			Link selfLink= WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selfLink);
			EntityModel<User> entityModel=EntityModel.of(user);
			return entityModel;
			
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}

	}
}
