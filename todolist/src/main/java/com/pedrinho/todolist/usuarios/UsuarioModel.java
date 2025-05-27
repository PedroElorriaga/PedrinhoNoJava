package com.pedrinho.todolist.usuarios;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data // USAMOS O LOMBOK PARA UTILIZAÇÃO DO GETTER E SETTER
@Entity(name = "usuarios") // DEFININDO NOME DA TABELA
public class UsuarioModel {

    // CONFIGURANDO ID
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String username;
    private String password;

    @CreationTimestamp
    private LocalDateTime createAt;

    // // GETTER E SETTERS
    // public String getUsername() {
    // return username;
    // }

    // public void setUsername(String username) {
    // this.username = username;
    // }

    // public String getPassword() {
    // return password;
    // }

    // public void setPassword(String password) {
    // this.password = password;
    // }

}
