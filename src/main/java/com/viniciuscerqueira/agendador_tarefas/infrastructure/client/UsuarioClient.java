package com.viniciuscerqueira.agendador_tarefas.infrastructure.client;

import com.viniciuscerqueira.agendador_tarefas.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping("/usuario")
   UsuarioDTO buscaUsuarioPorEmail(@RequestParam("email") String email,
                                   @RequestHeader("Authorization") String token);

}
