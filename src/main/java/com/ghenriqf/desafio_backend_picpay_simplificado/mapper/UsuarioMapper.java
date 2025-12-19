package com.ghenriqf.desafio_backend_picpay_simplificado.mapper;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.Usuario;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.UsuarioRequest;
import com.ghenriqf.desafio_backend_picpay_simplificado.dto.UsuarioResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest dto) {
        Usuario entity = new Usuario();
        entity.setNomeCompleto(dto.nomeCompleto());
        entity.setCpf(dto.cpf());
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setSaldo(dto.saldo());
        entity.setTipo(dto.tipoDoUsuario());
        return entity;
    }

    public UsuarioResponse toDTO(Usuario entity) {
      return new UsuarioResponse(
        entity.getId(),
        entity.getNomeCompleto(),
        entity.getEmail(),
        entity.getTipo()
      );
    }
}
