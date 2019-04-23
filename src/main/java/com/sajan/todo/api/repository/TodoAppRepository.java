package com.sajan.todo.api.repository;

import org.springframework.stereotype.Repository;

import com.sajan.todo.api.model.TodoAppModel;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TodoAppRepository extends JpaRepository<TodoAppModel, Long> {
	
}
