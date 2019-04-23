package com.sajan.todo.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sajan.todo.api.model.TodoModel;

@Repository
public interface TodosRepository extends JpaRepository<TodoModel, Long>  {
}
