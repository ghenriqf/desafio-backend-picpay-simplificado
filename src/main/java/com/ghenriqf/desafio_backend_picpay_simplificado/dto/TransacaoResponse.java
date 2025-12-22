package com.ghenriqf.desafio_backend_picpay_simplificado.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse (
         Long id,
         BigDecimal valor,
         UsuarioResponse remetente,
         UsuarioResponse destinatario,
         LocalDateTime dataHora
){
}
