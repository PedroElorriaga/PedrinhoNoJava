package com.pedrinho.todolist.tarefas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID idUsuario;

    private String descricao;
    private String titulo;

    private LocalDate startAt;
    private LocalDate endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
