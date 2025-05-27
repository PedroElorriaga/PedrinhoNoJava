package com.pedrinho.todolist.usuarios;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
    UsuarioModel findByUsername(String username); // INTERFACE PARA BUSCAR NOME

    /*
     * O Spring analisa esse nome e entende:
     * 
     * "findBy" = é uma consulta
     * 
     * "Username" = é o campo da entidade UsuarioModel
     * 
     * Então ele monta a consulta:
     * SELECT * FROM usuario WHERE username = ? LIMIT 1
     */
}