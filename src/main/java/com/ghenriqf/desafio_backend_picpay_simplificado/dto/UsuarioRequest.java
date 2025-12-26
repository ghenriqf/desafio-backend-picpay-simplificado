package com.ghenriqf.desafio_backend_picpay_simplificado.dto;

import com.ghenriqf.desafio_backend_picpay_simplificado.domain.usuario.TipoDoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UsuarioRequest(
        @NotBlank String nomeCompleto,
        @NotBlank String cpf,
        @NotBlank String email,
        @NotBlank String senha,
        @NotNull TipoDoUsuario tipoDoUsuario,
        @NotNull @PositiveOrZero BigDecimal saldo
) {
}
