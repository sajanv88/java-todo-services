package com.sajan.todo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sajan.todo.api.model.UserModel;
import com.sajan.todo.api.model.UserServiceModel;
import com.sajan.todo.api.repository.UserRepository;
import com.sajan.todo.security.Jwt;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping(path = "/me")
public class UserMeController {
	
	@Autowired
	private UserRepository user;
	
	@GetMapping("/info")
	public ResponseEntity<UserServiceModel> getUserById() {
		Claims authenticatedUser = Jwt.getAuthenticatedUser();
		Long id = Long.parseLong(authenticatedUser.getId());
		UserModel u = user.findById(id).get();
		UserServiceModel us = new UserServiceModel(u);
		return new ResponseEntity<UserServiceModel>(us, HttpStatus.ACCEPTED);
	}
}
