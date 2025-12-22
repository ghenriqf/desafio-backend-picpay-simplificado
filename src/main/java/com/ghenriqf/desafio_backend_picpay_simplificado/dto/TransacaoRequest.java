package com.ghenriqf.desafio_backend_picpay_simplificado.dto;

import java.math.BigDecimal;

public record TransacaoRequest(
        BigDecimal valor,
        Long remetenteId,
        Long destinatarioId
) {
}
