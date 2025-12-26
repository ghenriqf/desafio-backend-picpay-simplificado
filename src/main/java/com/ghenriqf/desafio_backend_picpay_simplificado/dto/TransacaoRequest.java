package com.ghenriqf.desafio_backend_picpay_simplificado.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransacaoRequest(
        @NotNull @Positive BigDecimal valor,
        @NotNull @Positive Long remetenteId,
        @NotNull @Positive Long destinatarioId
) {
}
