package com.ghenriqf.desafio_backend_picpay_simplificado.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private String mensagem;
    private int status;
    private LocalDateTime dataHora;
}
