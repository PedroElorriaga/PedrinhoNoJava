package com.pedrinho.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pedrinho.todolist.usuarios.IUsuarioRepository;
import com.pedrinho.todolist.usuarios.UsuarioModel;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    // ISSO É COMO SE FOSSE UM MIDDLEWARE DO PYTHON OU JS
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String serverletPath = request.getServletPath();

        // MIDDLEWARE SETADO PARA TODAS ROTAS QUE CONTÉM /tasks
        if (serverletPath.contains("/tasks")) {
            String autorizacao = request.getHeader("Authorization"); // PEGAMOS AS INFORMAÇÕES DO HEADER SOBRE AUTH
            String autorizacaoHash = autorizacao.split(" ")[1]; // SEPARAMOS O RETORNO PARA NOS RETORNAR SOMENTE O BYTE
                                                                // ex:
                                                                // cGVkcm86MTIzNA==
            byte[] autorizacaoDecoded = Base64.getDecoder().decode(autorizacaoHash);

            /*
             * System.out.println(autorizacaoDecoded.getClass());
             * [Z = boolean
             * [B = byte
             * [S = short
             * [I = int
             * [J = long
             * [F = float
             * [D = double
             * [C = char
             * [L = any non-primitives(Object)
             */

            String autorizacaoString = new String(autorizacaoDecoded); // AQUI, ESTAMOS CRIANDO UMA STRING A PARTIR DE
                                                                       // UM
                                                                       // BYTE[]

            String[] autorizacaoList = autorizacaoString.split(":");

            String username = autorizacaoList[0];
            String password = autorizacaoList[1];

            UsuarioModel usuarioFromDb = this.usuarioRepository.findByUsername(username);

            if (usuarioFromDb == null) {
                response.sendError(401);
            } else {
                BCrypt.Result passwordDecrypt = BCrypt.verifyer().verify(password.toCharArray(),
                        usuarioFromDb.getPassword().toCharArray());

                if (!passwordDecrypt.verified) {
                    response.sendError(401);
                } else {
                    request.setAttribute("idUsuario", usuarioFromDb.getId()); // PASSO ESSA INFORMAÇÃO PARA O REQUEST
                    filterChain.doFilter(request, response);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
