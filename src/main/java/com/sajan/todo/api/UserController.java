package com.sajan.todo.api;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sajan.todo.api.model.TokenDataModel;
import com.sajan.todo.api.model.UserLoginInfoModel;
import com.sajan.todo.api.model.UserModel;
import com.sajan.todo.api.repository.UserRepository;
import com.sajan.todo.exception.UserException;
import com.sajan.todo.security.Jwt;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping(path = "/users")
public class UserController {
	
	@Autowired
	private UserRepository userModel;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody UserModel user) {
		String bcryptHashString = BCrypt.withDefaults()
				.hashToString(12, user.getPassword().toCharArray());
		user.setPassword(bcryptHashString);
		UserModel newUser = userModel.save(user);
		return new ResponseEntity<String>(
				String.format("Registered successfully %s",
						newUser.getFullName()), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody UserLoginInfoModel userAuthDetails) {
		Iterator<UserModel> users = userModel.findAll().iterator();
		UserModel validUser = null;
		while(users.hasNext()) {
			UserModel user = users.next();
			if (user.getEmailAddress().equals(userAuthDetails.getEmailAddress())) {
				BCrypt.Result result = BCrypt
						.verifyer()
						.verify(userAuthDetails.getPassword().toCharArray(),
								user.getPassword());
				if (result.verified) {
					validUser = user;
					break;
				}else {
					throw new UserException("Password is not valid");
				}

			}
		}
		if (validUser != null) {
			String token = Jwt.createJwt(
					validUser.getId().toString(),
					"TodoApplication",
					validUser.getFullName(),
					new Date().getTime());
			TokenDataModel td = new TokenDataModel(token, validUser.getFullName());
			return new ResponseEntity<Object>(td, HttpStatus.ACCEPTED);
		}
		throw new UserException("User not found");
	}
	
}
