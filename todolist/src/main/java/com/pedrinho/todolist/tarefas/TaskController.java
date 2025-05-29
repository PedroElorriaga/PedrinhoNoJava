package com.pedrinho.todolist.tarefas;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity postTaskCreate(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        LocalDate dataAtual = LocalDate.now();
        if (dataAtual.isAfter(taskModel.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio deve ser maior ou igual que a data atual");
        } else if (dataAtual.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de termino deve ser maior data atual");
        }

        Object idUsuario = request.getAttribute("idUsuario"); // COLETO A INFORMAÇÃO QUE PASSEI NO REQUEST DO
                                                              // FILTER(MIDDLEWARE)
        taskModel.setIdUsuario((UUID) idUsuario);
        this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa criada com sucesso");
    }

    @GetMapping("")
    public ResponseEntity getTaskList(TaskModel taskModel, HttpServletRequest request) {
        Object idUsuario = request.getAttribute("idUsuario");
        List<TaskModel> usuariosFromDb = this.taskRepository.findByIdUsuario((UUID) idUsuario);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuariosFromDb);
    }
}
