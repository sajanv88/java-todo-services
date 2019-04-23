package com.sajan.todo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sajan.todo.api.model.TodoModel;
import com.sajan.todo.api.repository.TodosRepository;
import com.sajan.todo.exception.TodoAccessNotValidException;
import com.sajan.todo.security.Jwt;

import io.jsonwebtoken.Claims;

@Component
public class TodoService {
	
	@Autowired
	private TodosRepository todosRepo;

	public List<TodoModel> fetchTodos(Long userId) {
		Iterator<TodoModel> allTodos =  todosRepo.findAll().iterator();
		List<TodoModel> todos = new ArrayList<TodoModel>();
		while(allTodos.hasNext()) {
			TodoModel todo = allTodos.next();
			if (todo.getUserRefId().equals(userId)) {
				todos.add(todo);
			}
		}
		return todos;
	}
	
	public TodoModel save(TodoModel todo) {
		return todosRepo.save(todo);
	}
	
	public TodoModel update(TodoModel newTodo) {
		Claims user = Jwt.getAuthenticatedUser();
		if(Long.parseLong(user.getId()) != newTodo.getUserRefId()) {
			throw new TodoAccessNotValidException("Bad request received!");
		}
		TodoModel todo = todosRepo.getOne(newTodo.getId());
		todo.setTodoTitle(newTodo.getTodoTitle());
		todo.setDescription(newTodo.getDescription());
		todo.setStatus(newTodo.getStatus());
		todo.setProgress(newTodo.getProgress());
		todo.setCreated(newTodo.getCreated());
		todo.setCompleted(newTodo.getCompleted());
		todo.setUpdatedAt(new Date());
		return todosRepo.save(todo);
	}
	
	public Boolean deleteTodo(Long id) {
		Claims user = Jwt.getAuthenticatedUser();
		TodoModel todo = todosRepo.getOne(id);
		if(Long.parseLong(user.getId()) != todo.getUserRefId()) {
			throw new TodoAccessNotValidException("Bad request received!");
		}
		todosRepo.deleteById(id);
		return true;
	}
}
