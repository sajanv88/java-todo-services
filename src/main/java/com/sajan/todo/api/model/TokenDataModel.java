package com.sajan.todo.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDataModel {
	private String token;
	private String fullName;
}
