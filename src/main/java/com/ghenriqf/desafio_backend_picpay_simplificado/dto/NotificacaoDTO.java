package com.ghenriqf.desafio_backend_picpay_simplificado.dto;

import jakarta.validation.constraints.NotBlank;

public record NotificacaoDTO(
        @NotBlank String email,
        @NotBlank String mensagem
) {
}
