package com.pedrinho.todolist.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @PostMapping("")
    public ResponseEntity postUsuario(@RequestBody UsuarioModel usuarioModel) {
        // String username = usuarioModel.getUsername();
        // String password = usuarioModel.getPassword();

        // return "Username " + username + " password " + password;
        var userFromDb = this.usuarioRepository.findByUsername(usuarioModel.getUsername());

        if (userFromDb != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe");
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, usuarioModel.getPassword().toCharArray());

        usuarioModel.setPassword(passwordHashed);

        this.usuarioRepository.save(usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com sucesso");
    }

    // @PostMapping("/login")
    // public ResponseEntity loginUsuario(@RequestBody UsuarioModel usuarioModel) {
    // var userFromDb =
    // this.usuarioRepository.findByUsername(usuarioModel.getUsername());

    // if (userFromDb == null) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao
    // existe");
    // }

    // BCrypt.Result passwordDecrypt =
    // BCrypt.verifyer().verify(usuarioModel.getPassword().toCharArray(),
    // userFromDb.getPassword().toCharArray());

    // if (!passwordDecrypt.verified) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ou senha
    // invalidos");
    // }

    // return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario logado com
    // sucesso!");
    // }
}
