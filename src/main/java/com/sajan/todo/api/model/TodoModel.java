package com.sajan.todo.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "todos")
@Data
@EqualsAndHashCode(callSuper = false)
public class TodoModel extends AuditModel {
	@Id
	@GeneratedValue(generator = "todos_generator")
	@SequenceGenerator(
			name = "todos_generator",
			sequenceName = "todos_sequence",
			initialValue = 1000
	)
	private Long id;
	
	@NotBlank
	@Size(min = 5, max = 25,
	message = "minimum 5 characters and maximum 25 characters")
	private String todoTitle;
	
    @NotNull(message = "Please enter userRefId")
	private Long userRefId;
	
    @NotNull(message = "Please enter appId")
	private Long appId;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@NotBlank
	@Size(min = 5, max = 10, 
	message = "minimum 5 characters and maximum 10 characters")
	private String status;
	
	private Boolean progress = false;
	
	private Boolean completed = false;
	
	private Boolean closed = false;
	
	private Boolean created = true;
	
}
