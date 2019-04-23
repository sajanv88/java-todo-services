package com.sajan.todo.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "Users", indexes = { @Index(name = "userIndex", columnList = "id, emailAddress")})
public class UserModel extends AuditModel {
	@Id
	@GeneratedValue(generator = "user_generator")
	@SequenceGenerator(
			name = "user_generator",
			sequenceName = "user_sequence",
			initialValue = 1000
	)
	private Long id;

    @NotBlank
    @Size(min = 3, max = 100,
    message = "Minium length should be 3 characters and Maximum length should be 100 characters")
	private String fullName;
    
    @NotBlank
    @Column(unique=true)
    @Email(message = "Email is not valid",
    regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String emailAddress;
    
    @NotBlank
    @Size(min = 3, max = 100,
    message = "Minium length should be 3 characters and Maximum length should be 100 characters")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
