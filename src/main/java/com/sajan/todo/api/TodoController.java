package com.sajan.todo.api;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sajan.todo.api.model.TodoAppModel;
import com.sajan.todo.api.model.TodoModel;
import com.sajan.todo.api.repository.TodoAppRepository;
import com.sajan.todo.security.Jwt;
import com.sajan.todo.service.TodoService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping(path = "/todo")
public class TodoController {

	@Autowired
	private TodoAppRepository todoAppRepo;
	
	@Autowired
	private TodoService todoService;

	@GetMapping("/todos/all-todos/{userId}")
	public ResponseEntity<List<TodoModel>> getTodo(@PathVariable Long userId) {
		return new ResponseEntity<List<TodoModel>>(
				todoService.fetchTodos(userId),
				HttpStatus.ACCEPTED);
	}

	@PostMapping("/create-todo-app")
	public ResponseEntity<TodoAppModel> createTodoApp(@Valid @RequestBody TodoAppModel todo) {
		Claims user = Jwt.getAuthenticatedUser();
		Long id = Long.parseLong(user.getId());
		todo.setUserRefId(id);
		TodoAppModel newTodo = todoAppRepo.save(todo);
		return new ResponseEntity<TodoAppModel>(newTodo, HttpStatus.CREATED);
	}
	
	@PostMapping("/todos/create")
	public ResponseEntity<TodoModel> createTodo(@Valid @RequestBody TodoModel todo) {
		return new ResponseEntity<TodoModel>(
				todoService.save(todo),
				HttpStatus.CREATED);
	}
	
	@PutMapping("/todos/update")
	public ResponseEntity<TodoModel> updateTodo(@Valid @RequestBody TodoModel todo) {
		return new ResponseEntity<TodoModel>(
				todoService.update(todo),
				HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/todos/delete/{id}")
	public ResponseEntity<Boolean> deleteTodo(@PathVariable Long id) {
		return new ResponseEntity<Boolean>(
				todoService.deleteTodo(id),
				HttpStatus.ACCEPTED);
	}
}
