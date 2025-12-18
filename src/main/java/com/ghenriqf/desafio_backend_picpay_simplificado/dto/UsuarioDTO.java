package com.ghenriqf.desafio_backend_picpay_simplificado.dto;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.TipoDoUsuario;

import java.math.BigDecimal;

public record UsuarioDTO(
        String nomeCompleto,
        String cpf,
        String email,
        String senha,
        TipoDoUsuario tipoDoUsuario,
        BigDecimal saldo
) {
}
