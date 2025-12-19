package com.ghenriqf.desafio_backend_picpay_simplificado.controller;

import com.ghenriqf.desafio_backend_picpay_simplificado.dto.UsuarioRequest;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.UsuarioResponse;
import com.ghenriqf.desafio_backend_picpay_simplificado.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest usuarioDTO) {
        UsuarioResponse usuario = usuarioService.criarUsuario(usuarioDTO);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UsuarioResponse> listarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios();
        return usuarios;
    }
}
