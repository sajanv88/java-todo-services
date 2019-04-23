package com.sajan.todo.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserLoginInfoModel {
	
	@NotBlank
	@Email(message = "Email is not valid",
			    regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String emailAddress;
	
	@NotBlank
    @Size(min = 3, max = 100,
    message = "Minium length should be 3 characters and Maximum length should be 100 characters")
	private String password;

}
