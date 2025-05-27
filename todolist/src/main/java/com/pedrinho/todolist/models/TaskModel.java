package com.pedrinho.todolist.models;

import java.time.LocalDate;
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
    private LocalDate dataInicio;

    @CreationTimestamp
    private LocalDate startAt;
    private String endAt;
}
