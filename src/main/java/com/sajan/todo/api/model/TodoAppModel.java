package com.sajan.todo.api.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="todoapps")
public class TodoAppModel extends AuditModel {
	@Id
	@GeneratedValue(generator = "todo_apps_generator")
	@SequenceGenerator(
			name = "todo_apps_generator",
			sequenceName = "todo_apps_sequence",
			initialValue = 1000
	)
	private Long id;
	
    @NotBlank
    @Size(min = 3, max = 100)
	private String title;
    
    private Long userRefId;
    
	public Long getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(Long userRefId) {
		this.userRefId = userRefId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
