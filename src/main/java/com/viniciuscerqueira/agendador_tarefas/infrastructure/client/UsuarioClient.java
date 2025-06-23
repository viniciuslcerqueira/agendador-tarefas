package com.viniciuscerqueira.agendador_tarefas.infrastructure.client;

import com.viniciuscerqueira.agendador_tarefas.business.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface UsuarioClient {

    @GetMapping
   UsuarioDTO buscaUsuarioPorEmail(@RequestParam("email") String email,
                                   @RequestHeader("Authorization") String token);

}
