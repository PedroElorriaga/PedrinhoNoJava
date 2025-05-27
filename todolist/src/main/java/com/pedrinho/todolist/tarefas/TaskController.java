package com.pedrinho.todolist.tarefas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity postMethodName(@RequestBody TaskModel taskModel) {

        this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa criada com sucesso");
    }

}
